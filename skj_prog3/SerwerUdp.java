import java.io.*;
import java.net.*;
//S23184
public class SerwerUdp {

    private final static int SERVER_UDP_PORT = 8000;
    private final static String SERVER_UDP_NAME = "localhost";
    private final static int FLAG_TO_SERVER=1234;
    private final static int FINAL_FLAG=4321;

//funkcje obliczeniowe
    public static int naturalnum(int num){
        int index=0;
        while(Math.pow(index,7)<num){
            index++;
        }
        return index;
    }
    public static int arraysum(int [] arr){
        int sum=0;
        for(int i=0;i<arr.length;i++){
            sum+=arr[i];
        }
        return sum;
    }
    public static String deletenumber(String h){
        String str = h;
        String strNew = str.replace("2", "");
        return strNew;
    }
    public static String[] dataEncoder(String h){
        String[] splited = h.split(" ");
        return splited;
    }

    public static void main(String[] args) throws IOException {



        //tworzenie socketa
        DatagramSocket socket = new DatagramSocket(SERVER_UDP_PORT, InetAddress.getByName(SERVER_UDP_NAME));
        //program wykonuje sie caly czas
        while(true) {

            //tworzenie bufforow
            byte[] inServer = new byte[1024];
            byte[] outServer = new byte[1024];
            byte[] inServer2 = new byte[1024];
            byte[] outServer2 = new byte[1024];
            byte[] inServer3 = new byte[1024];
            byte[] outServer3 = new byte[1024];
            byte[] inServer4 = new byte[1024];
            byte[] outServer4 = new byte[1024];
            byte[] outServer5 = new byte[1024];


            DatagramPacket rcvPkt = new DatagramPacket(inServer, inServer.length);
            socket.receive(rcvPkt);
            //dostajemy informacje kiedy przychodzi pakiet
            System.out.println("Packet Received!");
            String msg = new String(rcvPkt.getData(), rcvPkt.getOffset(), rcvPkt.getLength());
            String flag = String.valueOf(FLAG_TO_SERVER);


            //odsylamy informacje do obiektu z ktorego ja dostalismy
            InetAddress IP = rcvPkt.getAddress();
            int port = rcvPkt.getPort();

            //jezeli obiekt podal flage zaczynamy wysylac mu dane od zadania
            if (flag.equals(msg)) {
                String data = "2002";

                outServer2 = data.getBytes();
                DatagramPacket senddata = new DatagramPacket(outServer2, outServer2.length, IP, port);
                socket.send(senddata);
                DatagramPacket rcvPkt2 = new DatagramPacket(inServer2, inServer2.length);
                socket.receive(rcvPkt2);
                System.out.println("Packet Received!");
                String msg2 = new String(rcvPkt2.getData(), rcvPkt2.getOffset(), rcvPkt2.getLength());
                //sprawdzamy czy dane sie zgadzaja
                if (Integer.parseInt(msg2) == naturalnum(Integer.parseInt(data))) {
                    String data2 = "5 4 3 2";
                    int tab[] = {5, 4, 3, 2};
                    outServer3 = data2.getBytes();
                    DatagramPacket senddata2 = new DatagramPacket(outServer3, outServer3.length, IP, port);
                    socket.send(senddata2);
                    DatagramPacket rcvPkt3 = new DatagramPacket(inServer3, inServer3.length);
                    socket.receive(rcvPkt3);
                    System.out.println("Packet Received!");
                    String msg3 = new String(rcvPkt3.getData(), rcvPkt3.getOffset(), rcvPkt3.getLength());
                    if (arraysum(tab) == Integer.parseInt(msg3)) {
                        String data3 = "2napis2";
                        outServer4 = data3.getBytes();
                        DatagramPacket senddata3 = new DatagramPacket(outServer4, outServer4.length, IP, port);
                        socket.send(senddata3);
                        DatagramPacket rcvPkt4 = new DatagramPacket(inServer4, inServer4.length);
                        socket.receive(rcvPkt4);
                        System.out.println("Packet Received!");
                        String msg4 = new String(rcvPkt4.getData(), rcvPkt4.getOffset(), rcvPkt4.getLength());
                        if (deletenumber(data3).equals(msg4)) {
                            String flag2 = String.valueOf(FLAG_TO_SERVER);
                            outServer4 = flag2.getBytes();
                            DatagramPacket senddata4 = new DatagramPacket(outServer5, outServer5.length, IP, port);
                            socket.send(senddata4);

                        }
                    }

                }


            } else {

                //jezeli nie dostalismy flagi wysylamy dane serwera UDP
                String temp = new String(SERVER_UDP_PORT + " " + SERVER_UDP_NAME);
                outServer = temp.getBytes();
                DatagramPacket sndPkt = new DatagramPacket(outServer, outServer.length, IP, port);
                socket.send(sndPkt);
            }
        }

    }
}