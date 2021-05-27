package com.itcast;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 功能描述：nio客户端
 *
 * @author JIAQI
 * @date 2021/5/27 - 14:44
 */
public class NioClient {

    public static void main(String[] args) throws Exception {

        // 创建一个客户端通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置channel为非阻塞
        socketChannel.configureBlocking(false);
        // 与服务端进行连接
        boolean ifConnect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 6666));
        // 如果客户端还未连接上服务端
        if (!ifConnect) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作");
            }
        }
        // 如果连接成功
        String str = "你好，这里是客户端";
        // 方式一：
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());

        // 方式二：
        /* // 创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        // 反转
        buffer.flip();*/

        // 将数据写入通道
        socketChannel.write(buffer);
        System.in.read();
    }
}
