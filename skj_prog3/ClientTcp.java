import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
//S23184
/*1. Utwórz gniazdo UDP. Wyślij do serwera TCP jedną linię w formacie adres:port, gdzie adres jest adresem IP Twojego gniazda UDP, a port jego numerem portu. Wykonaj poniższe polecenia używając protokołu UDP i gniazda, które właśnie utworzyłeś.

        2. Wyślij numer portu z którego się komunikujesz. (ten podpunkt mial nie byc wykonany)

        3. Odbierz liczbę naturalną x. Oblicz największą liczbę naturalną k, taką, że k podniesione do potęgi 7 jest nie większe niż wartość x. Odeślij wartość k.

        4. W 4 kolejnych liniach odbierz 4 liczb(y) naturalnych(e). Policz sumę tych liczb i odeślij.

        5. Odbierz napis. Usuń z niego wszystkie wystąpienia 2 i odeślij wynik.*/

public class ClientTcp {

    private final static String SERVER_TCP_NAME = "localhost";
    private  static int SERVER_UDP_PORT;
    private final static int SERVER_TCP_PORT = 8005;
    private  static String SERVER_UDP_NAME;
    private final static int FLAG_TO_SERVER=1234;

//funkcje do realizacji programu
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


    public static void main(String[] args) throws IOException,SocketException{
        //laczymy sie z serwerem tcp by pobrac dane o serwerze UDP
        try (Socket clientSocket = new Socket(SERVER_TCP_NAME, SERVER_TCP_PORT)) {
            try (
                    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String udpdata=inFromServer.readLine();
                String tab[];
                tab=dataEncoder(udpdata);
                int port=Integer.parseInt(tab[0]);
                SERVER_UDP_PORT=port;
                SERVER_UDP_NAME=tab[1];


            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String flag=String.valueOf(FLAG_TO_SERVER);
        //polaczenie udp
        DatagramSocket socket = new DatagramSocket();

        //buffery do danych
        byte[] inData = new byte[1024];
        byte[] inData2 = new byte[1024];
        byte[] inData3= new byte[1024];
        byte[] inData4 = new byte[1024];
        byte[] zad1 = new byte[1024];
        byte[] zad3 = new byte[1024];
        byte[] zad4 = new byte[1024];
        byte[] zad5 = new byte[1024];

        //ip serwera udp
        InetAddress IP = InetAddress.getByName(SERVER_UDP_NAME);

        //wyslanie flagi by zaczac otrzymywac dane
        zad1 = flag.getBytes();
        DatagramPacket sendPkt = new DatagramPacket(zad1, zad1.length, IP, SERVER_UDP_PORT);
        socket.send(sendPkt);

        //zad3 otrzymujemy dane i wysylamy odpowiedz do serwera udp
        DatagramPacket recievePkt = new DatagramPacket(inData, inData.length);
        socket.receive(recievePkt);
        String msg = new String(recievePkt.getData(), recievePkt.getOffset(), recievePkt.getLength());
        int x = Integer.parseInt(msg);
        System.out.println("Otrzymana liczba: " + x);
        System.out.println("wynik: " + naturalnum(x));
        String msg2=String.valueOf(naturalnum(x));
        zad3 = msg2.getBytes();
        DatagramPacket sendPkt2 = new DatagramPacket(zad3, zad3.length, IP, SERVER_UDP_PORT);
        socket.send(sendPkt2);
        //zad4
        int a, b, c, d ;
        DatagramPacket recievePkt2 = new DatagramPacket(inData2, inData2.length);
        socket.receive(recievePkt2);
        String msg3 = new String(recievePkt2.getData(), recievePkt2.getOffset(), recievePkt2.getLength());
        String tab2[];
        tab2=dataEncoder(msg3);
        a = Integer.parseInt(tab2[0]);
        b = Integer.parseInt(tab2[1]);
        c = Integer.parseInt(tab2[2]);
        d = Integer.parseInt(tab2[3]);
        int arr[] = {a, b, c, d,};
        System.out.println("Otrzymane liczby: "+a+", "+b+", "+c+", "+d);
        System.out.println("suma liczb wynosi: "+arraysum(arr));
        String msg4=String.valueOf(arraysum(arr));
        zad4 = msg4.getBytes();
        DatagramPacket sendPkt3 = new DatagramPacket(zad4, zad4.length, IP, SERVER_UDP_PORT);
        socket.send(sendPkt3);
        //zad5
        DatagramPacket recievePkt3 = new DatagramPacket(inData3, inData3.length);
        socket.receive(recievePkt3);
        String msg5 = new String(recievePkt3.getData(), recievePkt3.getOffset(), recievePkt3.getLength());
        System.out.println("Otrzymany napis: " + msg5);
        System.out.println("Szukana liczba: " + deletenumber(msg5));
        String msg6=deletenumber(msg5);
        zad5 = msg6.getBytes();
        DatagramPacket sendPkt4 = new DatagramPacket(zad5, zad5.length, IP, SERVER_UDP_PORT);
        socket.send(sendPkt4);
        //final flag otrzymujemy finalna flage
        DatagramPacket recievePkt4 = new DatagramPacket(inData4, inData4.length);
        socket.receive(recievePkt4);
        String msg7 = new String(recievePkt.getData(), recievePkt.getOffset(), recievePkt.getLength());
        System.out.println("ostateczna flaga wynosi: "+msg7);

    }
}


