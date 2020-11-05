import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class PalindromeServer {
    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(59898)) {
            System.out.println("The Palindrome server is running...");
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Palindrome(listener.accept()));
            }
        }
    }
    private static class Palindrome implements Runnable {
        private Socket socket;
        Palindrome(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
	String original, reverse = "";
            System.out.println("Connected: " + socket);
            try {
                var in = new Scanner(socket.getInputStream());
                var out = new PrintWriter(socket.getOutputStream(), true);
                while (in.hasNextLine()) {
                    
		    original = in.nextLine();
		     int length = original.length();   
      for ( int i = length - 1; i >= 0; i-- )  
         reverse = reverse + original.charAt(i);  
      if (original.equals(reverse))  
         out.println("Entered string/number is a palindrome.");  
      else  
         out.println("Entered string/number isn't a palindrome.");

                }
            } catch (Exception e) {
                System.out.println("Error:" + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                System.out.println("Closed: " + socket);
            }
        }
    }
}