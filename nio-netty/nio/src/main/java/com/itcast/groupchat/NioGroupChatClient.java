package com.itcast.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 功能描述：群聊客户端，及用户
 *
 * @author JIAQI
 * @date 2021/5/28 - 16:29
 */
public class NioGroupChatClient {

    private Selector selector;
    private SocketChannel socketChannel;
    private static final String IP = "127.0.0.1";
    private static final int PORT = 6666;
    private String username;

    public NioGroupChatClient() {
        try {
            // 创建一个socketChannel客户端并与服务端进行连接
            socketChannel = SocketChannel.open(new InetSocketAddress(IP, PORT));
            // 设置为非阻塞
            socketChannel.configureBlocking(false);
            // 定义一个selector
            selector = Selector.open();
            // 将socketChannel注册到chanel中,并关联一个buffer
            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
            //得到username
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + " is ok...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 客户端发送消息
    private void sendInfo(String msg) throws IOException {
        msg = username + " 说：" + msg;
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        // 将消息写入通道
        socketChannel.write(buffer);
    }

    // 客户端接收消息
    private void readInfo() {
        try {
            // 监听channel是否有事件发送
            int count = selector.select();
            if (count > 0) {
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey selectionKey = selectedKeys.next();
                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        channel.read(buffer);
                        System.out.println("[客户端接收消息]:" + new String(buffer.array()));
                    }
                }
                selectedKeys.remove();
            } else {
                System.out.println("[客户端接收消息]:当前暂无群消息...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        NioGroupChatClient chatClient = new NioGroupChatClient();
        //启动一个线程, 每个3秒，读取从服务器发送数据
        new Thread(() -> {
            while (true) {
                chatClient.readInfo();
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClient.sendInfo(msg);
        }
    }

}
