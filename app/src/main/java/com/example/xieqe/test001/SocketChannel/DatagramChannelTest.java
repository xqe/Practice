package com.example.xieqe.test001.SocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

/**
 * Created by xieqe on 2018/3/14.
 */

public class DatagramChannelTest {
    private DatagramChannel datagramChannel;
    private Selector selector;
    private boolean bStop;

    private void initUDPConfig(int receivePort) throws IOException {
        //UDP只需指定数据包的接收端口，接收广播接收者的回复信息
        SocketAddress socketAddress = new InetSocketAddress(receivePort);
        datagramChannel = DatagramChannel.open();

        //绑定特定的地址和端口，在该端口上接收数据包
        datagramChannel.socket().bind(socketAddress);

        //设置监听的端口可以重用，针对一次可能接收到多个数据源发来的数据包，
        datagramChannel.socket().setReuseAddress(true);

        datagramChannel.configureBlocking(false);
        datagramChannel.socket().setBroadcast(true);//设置可发送广播

        selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);
    }

    private String sendUDPPackage(ByteBuffer pack,int sendPort) throws IOException {
        String result = "";
        //255.255.255.255，有限广播，可向该局域网内所有ip段的设备发送广播
        String ip = "255.255.255.255";
        datagramChannel.send(pack,new InetSocketAddress(ip, sendPort));
        while (!bStop) { //未收到回复之前就一直发
            if (selector.select(200) > 0) {
                for (SelectionKey sk : selector.selectedKeys()) {
                    selector.selectedKeys().remove(sk);// 删除正在处理的selectionkey
                    if (sk.isReadable())// 如果该selectionkey对应的channel中有可读的数据
                    {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        /*第一种：receive读取方式，若调用connect之后则不能用receive方法读取 */
					    DatagramChannel dc= (DatagramChannel)sk.channel();
					    SocketAddress socketAddress= dc.receive(buffer);

                        /*第二种：
                        read读取方式，只用于已连接的通道，
                        必须用datagramChannel.connect(new InetSocketAddress(ADDRASS, PORT))之后，
                        才可使用该方法读取数据但这种方式无法获得发送这个包的主机的地址与监听端口*/
                        /*DatagramChannel dc= (DatagramChannel)sk.channel();
                        SocketAddress socketAddress = dc.read(buffer);*/

                        buffer.flip();//翻转buffer，开始输出
                        CharBuffer charBuffer = Charset.forName("UTF-8").newDecoder().decode(buffer);
                        result = charBuffer.toString();

                        //回复
                        String responseStr = "datagramChannel已收到你的消息";
                        ByteBuffer response = ByteBuffer.wrap(responseStr.getBytes("UTF-8"));
                        datagramChannel.send(response, socketAddress);

                        //或者datagramChannel.write(response)--write与connect
                        response.clear();
                    }
                }
            }
        }
        return result;
    }

    public void test(int receivePort) throws IOException {
        initUDPConfig(receivePort);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(0, (byte) 2);
        String result = sendUDPPackage(byteBuffer,8001);
    }
}
