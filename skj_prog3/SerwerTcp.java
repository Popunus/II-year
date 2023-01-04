import java.io.*;
import java.net.*;
//S23184
public class SerwerTcp {
    private static final int PORT = 8005;
    private final static int SERVER_UDP_PORT = 8000;
    private final static String SERVER_UDP_NAME = "localhost";
    private final static String SERVER_TCP_NAME = "localhost";
    public static void main(String[]args) throws IOException {
        String connect="siema";

        //socket do serwera udp
        DatagramSocket socket = new DatagramSocket();

        //buffery do danych
        byte[] inData = new byte[1024];
        byte[] outData = new byte[1024];

        //ip serwera udp
        InetAddress IP = InetAddress.getByName(SERVER_UDP_NAME);

        //wysylamy komunikat by sie polaczyc z serwerem UDP
        outData = connect.getBytes();


        //wysylamy pakiet
        DatagramPacket sendPkt = new DatagramPacket(outData, outData.length, IP, SERVER_UDP_PORT);
        socket.send(sendPkt);

        //otrzymujemy dane serwera UDP
        DatagramPacket recievePkt = new DatagramPacket(inData, inData.length);
        socket.receive(recievePkt);
        String msg = new String(recievePkt.getData(), recievePkt.getOffset(), recievePkt.getLength());
        //wysylamy dane serwera UDP do klienta
        System.out.println("Replay from Server: "+msg);
        log("Starting");
        log("Server socket opening");
        ServerSocket welcomeSocket = new ServerSocket(PORT);
        log("Server socket opened");
        log("Server socket listing");
        Socket clientSocket = welcomeSocket.accept();
        log("Client connected from: " +
                clientSocket.getInetAddress().toString() +
                ":" + clientSocket.getPort());
        log("Streams collecting phase");
        OutputStream os = clientSocket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write(msg);
        bw.newLine();
        bw.flush();



    }
    public static void log(String message) {
        System.out.println("[S]: " + message);
        System.out.flush();
    }
}
