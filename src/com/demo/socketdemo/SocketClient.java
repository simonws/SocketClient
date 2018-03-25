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
     * �������������
     */
    public void sendTcpData() {
        new Thread() {

            @Override
            public void run() {

                try {
                    socket = new Socket(ipString,9999);
                    System.out.println("JAVA�������ӣ�" + socket.isConnected());
                    
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
     * ������Ϣ
     */
    public void send() {
        new Thread() {
            @Override
            public void run() {

                try {
                    // socket.getInputStream()
                    DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
                    writer.writeUTF("�ٺ٣���ð���������.."); // дһ��UTF-8����Ϣ

                    System.out.println("������Ϣ");
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
            /*** ��������***/
            // ��ʼ��datagramSocket,ע����ǰ��Server��ʵ�ֵĲ��
            datagramSocket = new DatagramSocket();
            // ʹ��DatagramPacket(byte buf[], int length, InetAddress address, int port)������װ����UDP���ݱ�
            byte[] buf = sendStr.getBytes();
            InetAddress address = InetAddress.getByName(ipString);
            datagramPacket = new DatagramPacket(buf, buf.length, address, PORT_NUM);
            // ��������
            datagramSocket.send(datagramPacket);
           
            /*** ��������***/
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
            // �ر�socket
            if(datagramSocket != null){
                datagramSocket.close();
            }
        }
    } 

}
