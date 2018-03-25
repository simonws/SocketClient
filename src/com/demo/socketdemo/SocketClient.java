package com.demo.socketdemo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketClient {

	public SocketClient() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new SocketClient().sendTcpData();
		new SocketClient().sendUdpData();
	}
	
	
    private Socket socket;
    private String ipString = "127.0.0.1";

    /**
     * 建立服务端连接
     */
    public void sendTcpData() {
        new Thread() {

            @Override
            public void run() {

                try {
                    socket = new Socket(ipString,9999);
                    System.out.println("JAVA建立连接：" + socket.isConnected());
                    
                    Thread.sleep(1000);
                    send();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }.start();
    }

    /**
     * 发送消息
     */
    public void send() {
        new Thread() {
            @Override
            public void run() {

                try {
                    // socket.getInputStream()
                    DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
                    writer.writeUTF("嘿嘿，你好啊，服务器.."); // 写一个UTF-8的信息

                    System.out.println("发送消息");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    
    private String sendStr = "SendString";
    private int PORT_NUM = 5066;
   
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
   
    public void sendUdpData(){
        try {
            /*** 发送数据***/
            // 初始化datagramSocket,注意与前面Server端实现的差别
            datagramSocket = new DatagramSocket();
            // 使用DatagramPacket(byte buf[], int length, InetAddress address, int port)函数组装发送UDP数据报
            byte[] buf = sendStr.getBytes();
            InetAddress address = InetAddress.getByName(ipString);
            datagramPacket = new DatagramPacket(buf, buf.length, address, PORT_NUM);
            // 发送数据
            datagramSocket.send(datagramPacket);
           
            /*** 接收数据***/
            byte[] receBuf = new byte[1024];
            DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
            datagramSocket.receive(recePacket);
           
            String receStr = new String(recePacket.getData(), 0 , recePacket.getLength());
            System.out.println("Client Rece Ack:" + receStr);
            System.out.println(recePacket.getPort());
           
           
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭socket
            if(datagramSocket != null){
                datagramSocket.close();
            }
        }
    } 

}
