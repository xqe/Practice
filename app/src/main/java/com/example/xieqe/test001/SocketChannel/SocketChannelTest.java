package com.example.xieqe.test001.SocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by xieqe on 2018/3/14.
 */

public class SocketChannelTest {
    private SocketChannel socketChannel;
    private Selector selector;

    /**
     * 初始化socketChannel，注册通道选择器
     */
    private void initTCPConfig(String host, int port) throws IOException {
        SocketAddress socketAddress = new InetSocketAddress(host, port);
        socketChannel = SocketChannel.open(socketAddress);
        socketChannel.configureBlocking(false);

        //初始化选择器，注册监听感兴趣的事件：READ事件
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private String request(ByteBuffer params) throws IOException {
        socketChannel.write(params);
        String result = "";
        //阻塞到至少有一个通道在你注册的事件上就绪或者超时300ms
        if (selector.select(300) > 0) {
            for (SelectionKey key : selector.selectedKeys()) {
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer);

                    buffer.flip();//翻转buffer，开始输出
                    CharBuffer charBuffer = Charset.forName("UTF-8").newDecoder().decode(buffer);
                    result = charBuffer.toString();

                    key.interestOps(SelectionKey.OP_READ);    //为下一次读取做好准备
                }
                selector.selectedKeys().remove(key);  //删除正在处理的SelectionKey
            }
        }
        return result;
    }

    public void test(String host, int port) {
        try {
            initTCPConfig(host,port);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(0, (byte) 2);
            String result = request(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
