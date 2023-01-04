import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
public class Client {
    static String address = "";   // ip adress
    static int port = 19799; // nr portu
    static int flag = 125218;  //  flaga

    public static String deletenumber(String h) {
        String x_str = h;
        String str = x_str;
        String strNew = str.replace("9", "");
        return strNew;
    }

    ;

    public static int findK(int x) {
        int k = 0;
        while (Math.pow(k, 5) < x) k++;
        return k - 1;
    }

    public static long suma(long arr[]) {
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }


    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(address, port)) {
            try (
                    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String flagString = Integer.toString(flag);
                outToServer.println(flagString);
                //outToServer.println(clientSocket.getLocalPort());
                int x = Integer.parseInt(inFromServer.readLine());
                System.out.println("Otrzymana liczba: " + x);
                System.out.println("Szukana liczba: " + findK(x));
                String pierwszaOdpdoserwera = Integer.toString(findK(x));
                outToServer.println(pierwszaOdpdoserwera);
                long a, b, c, d, e;
                a = Long.parseLong(inFromServer.readLine());
                b = Long.parseLong(inFromServer.readLine());
                c = Long.parseLong(inFromServer.readLine());
                d = Long.parseLong(inFromServer.readLine());
                e = Long.parseLong(inFromServer.readLine());
                long tab[] = {a, b, c, d, e};
                System.out.println("Dla liczb " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", suma wynosi " + suma(tab));
                String drugaodpdoserwera = Long.toString(suma(tab));
                outToServer.println(drugaodpdoserwera);
                String h = inFromServer.readLine();
                System.out.println("Otrzymana liczba: " + h);
                System.out.println("Szukana liczba: " + deletenumber(h));
                outToServer.println(deletenumber(h));
                System.out.println(inFromServer.readLine());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}