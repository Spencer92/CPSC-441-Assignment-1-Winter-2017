package webproxy.proxy;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * WebProxy Class
 * 
 * @author      XYZ
 * @version     1.0, 20 Jan 2017
 *
 */


public class WebProxy {

        

     /**
     *  Constructor that initalizes the server listenig port
         *
     * @param port      Proxy server listening port
     */

	public WebProxy(int port) {

	/* Intialize server listening port */	
		//Modified from Server1.java
		  String s;
		  Scanner inputStream = null;
		  PrintWriter outputStream = null;
		  ServerSocket serverSocket = null;
		  try
		  {
			 // Wait for connection on port 6789
			 System.out.println("Waiting for a connection.");
			 serverSocket = new ServerSocket(port);
			 String str = serverSocket.toString();
			 System.out.println(str);
			 Socket socket = serverSocket.accept();
			 System.out.println(socket.getOutputStream());
			 
			 inputStream = new Scanner(new InputStreamReader(new java.io.InputStream(), "http://pages.cpsc.ucalgary.ca/~cyriac.james/sample.txt"));
			 
			 
			 // Connection made, set up streams
/*			 inputStream = new Scanner(new InputStreamReader(socket.getInputStream()));
		 	 outputStream = new PrintWriter(new
		 			 DataOutputStream(socket.getOutputStream()));
		 	 
		 	// Read a line from the client
			 s = inputStream.nextLine();
			 System.out.println(s);


			 // Output text to the client
			 outputStream.println(s);
		     outputStream.flush();
		     System.out.println("Closing connection");
		     inputStream.close();
		     outputStream.close();*/
		  }
		  catch (Exception e)
		  {
		     // If any exception occurs, display it
		     System.out.println("Error " + e);
		  }

		

	}

     /**
     * The webproxy logic goes here 
     */
	public void start(){
		

	}



/**
 * A simple test driver
*/
	public static void main(String[] args) {

		String server = "localhost"; // webproxy and client runs in the same machine
//		String server = "http://pages.cpsc.ucalgary.ca/";
		int server_port = 0;
		try {
                // check for command line arguments
                	if (args.length == 1) {
                        	server_port = Integer.parseInt(args[0]);
                	}
                	else {
                        	System.out.println("wrong number of arguments, try again.");
                        	System.out.println("usage: java WebProxy port");
                        	System.exit(0);
                	}


                	WebProxy proxy = new WebProxy(server_port);

                	System.out.printf("Proxy server started...\n");
                	proxy.start();
        	} catch (Exception e)
		{
			System.out.println("Exception in main: " + e.getMessage());
                        e.printStackTrace();
	
		}
		
	}
}
