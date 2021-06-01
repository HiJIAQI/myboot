package com.itcast.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 功能描述：群聊服务端
 *
 * @author JIAQI
 * @date 2021/5/28 - 14:20
 */
public class NioGroupChatService {

    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private static final int PORT = 6666;

    //构造器初始化工作
    public NioGroupChatService() {
        try {
            // 1. 创建serviceSocketChannel服务端进行监听连接
            serverSocketChannel = ServerSocketChannel.open();
            // 2. 绑定端口、设置非阻塞
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            // 3. 创建一个selector选择器
            selector = Selector.open();
            // 4. 将SocketChannel注册到selector选择器中,并定义关心事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 监听客户端连接
    private void listen() {
        try {
            // 5. 循环等待客户端进行连接
            while (true) {
                // 6.调用select方法监听通道返回事件发生个数
                int listenCount = selector.select(1000);
                // 若监听返回发生事件的通道个数大于0，则说明已经关注到了某个事件
                // 反之为没有事件发生
                if (listenCount > 0) {
                    // 遍历获取selectionKey集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> selectionKey = selectionKeys.iterator();
                    while (selectionKey.hasNext()) {
                        // 取出selectionKey
                        SelectionKey key = selectionKey.next();
                        // 监听发生的事件类型
                        // 6.1 当事件为连接事件---提示用户已上线
                        if (key.isAcceptable()) {
                            // 接受客户端与此通道所建立的连接
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            // 设置非阻塞
                            socketChannel.configureBlocking(false);
                            // 将该socketChannel注册到selector中,并创建buffer
                            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                            System.out.println("[用户上线通知]:" + socketChannel.getRemoteAddress() + "已上线");
                        }
                        // 6.2 当事件为读事件---转发给其他用户(自身除外)
                        if (key.isReadable()) {
                            readData(key);
                        }
                        // 手动从集合中移除当前的SelectionKey，防止重复操作
                        selectionKey.remove();
                    }
                } else {
                    System.out.println("[服务端消息]:当前无连接,服务端等待中...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读取客户端发生的消息
    private void readData(SelectionKey selectionKey) {
        SocketChannel channel = null;
        try {
            // 通过key 反向获取所关联的通道
            channel = (SocketChannel) selectionKey.channel();
            // 获取该channel所关联的buffer
            ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
            channel.read(buffer);
            //把缓存区的数据转成字符串
            String msg = new String(buffer.array());
            System.out.println("[服务端接收消息]:" + channel.getRemoteAddress() + ">>>" + msg);
            // 转发消息给其他客户端
            sendInfoToOtherClients(msg, channel);
//            //创建buffer
//            ByteBuffer buffer = ByteBuffer.allocate(1024);
//
//            int count = channel.read(buffer);
//            //根据count的值做处理
//            if(count > 0) {
//                //把缓存区的数据转成字符串
//                String msg = new String(buffer.array());
//                //输出该消息
//                System.out.println("form 客户端: " + msg);
//
//                //向其它的客户端转发消息(去掉自己), 专门写一个方法来处理
//                sendInfoToOtherClients(msg, channel);
//            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了..");
                //取消注册
                selectionKey.cancel();
                //关闭通道
                channel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    //转发消息给其它客户(通道)
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("[客户端转发消息]:服务器转发消息中...");
        Set<SelectionKey> keys = selector.keys();
        //遍历 所有注册到selector 上的 SocketChannel,并排除 self
        for (SelectionKey key : keys) {
            //通过 key  取出对应的 SocketChannel
            SelectableChannel channel = key.channel();
            // 进行对比类型 及是否为发送者本身
            if (channel instanceof SocketChannel && channel != self) {
                // 转型
                SocketChannel socketChannel = (SocketChannel) channel;
                // 将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // 将buffer 的数据写入 通道
                socketChannel.write(buffer);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new NioGroupChatService().listen();
    }

}
