package com.itcast.simple;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 功能描述：nio服务器端
 *
 * @author JIAQI
 * @date 2021/5/27 - 9:51
 */
public class NioService {

    // 1.当客户端连接时，会通过ServerSocketChannel得到一个SocketChannel
    // 2.Selector类中的select方法进行监听，返回通道发生事件的个数
    // 3.将得到的SocketChannel通过register方法注册到Selector选择器中
    // 4.注册后register方法会返回一个selectionKey与该Selector进行关联(set集合)
    // 5.若监听过程中有事件发生，通过Selector类中的selectedKeys方法进一步得到各个key
    // 6.通过selectionKey反向获取channel，并进行相应业务处理

    public static void main(String[] args) throws Exception {

        // 1.创建ServerSocketChannel -> SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2.绑定channel端口号,在服务端进行监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 3.设置socketChannel为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 4.创建selector选择器
        Selector selector = Selector.open();
        // 5.将serverSocketChannel注册到selector选择器中， 关心事件为  OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 6.循环等待客户端连接
        while (true) {
            // 7.通过selector中的select方法监听，通过返回的事件个数判断是否有事件发生
            // 7.1 若返回等于0,则说明没有事件发生
            if (selector.select(1000) == 0) {
                System.out.println("当前无连接,服务端等待中...");
                // 跳过本次循环
                continue;
            }
            // 7.2 若返回大于0,则说明已经关注到了某个事件
            // 通过Selector类中的selectedKeys方法进一步得到各个key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历各个key,并根据对应channel通道发生的事件做对应的业务处理
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                // 7.2.1 有新的客户端进行连接
                if (key.isAcceptable()) {
                    System.out.println("有新的客户端进行连接...");
                    // 在该服务端生成一个 socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端产生了一个channel:" + socketChannel.hashCode());
                    // 将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 将socketChannel 注册到selector 关注事件为OPEN_RED 并关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                // 7.2.2 有新的客户端进行读取
                if (key.isReadable()) {
                    // 通过key反向获取channel
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    // 获取该channel所关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    socketChannel.read(buffer);
                    System.out.println("来自客户端的消息：" + new String(buffer.array()));
                }
                // 手动从集合中移除当前的SelectionKey，防止重复操作
                keyIterator.remove();
            }
        }
    }

}
