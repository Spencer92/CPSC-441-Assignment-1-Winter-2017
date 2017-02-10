package webproxy.proxy;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * WebProxy Class
 * 
 * @author      XYZ
 * @version     1.0, 20 Jan 2017
 *
 */


public class WebProxy {

        
	private String allData;
	static final int PORT_NUMBER = 80;
	
     /**
     *  Constructor that initalizes the server listenig port
         *
     * @param port      Proxy server listening port
     */

	public WebProxy(int port) {

	/* Intialize server listening port */	
		//Modified from Server1.java
		ArrayList<Byte> bt = new ArrayList<Byte>();
		  String s = "";
		  String fullLengthData = "";
//		  Scanner inputStream = null;
//		  PrintWriter outputStream = null;
		  ByteArrayInputStream bis = null;
		  InputStream stream;
		  ServerSocket serverSocket = null;
		  int test;
		  byte [] data = new byte[1];
		  char [] ch = new char["http://pages.cpsc.ucalgary.ca/~cyriac.james/sample.txt".length()];
		  
		  ch = "http://pages.cpsc.ucalgary.ca/~cyriac.james/sample.txt".toCharArray();
		  
//		  for(int i = 0; i < ch.length; i++)
//		  {
//			  data[i] = Character.toString(ch[i]).getBytes()[0];
//		  }
		  
		  try
		  {
			 // Wait for connection on port 6789
			 System.out.println("Waiting for a connection.");
			 serverSocket = new ServerSocket(port);
			 String str = serverSocket.toString();
			 System.out.println(str);
			 Socket socket = serverSocket.accept();
//			 System.out.println(socket.getOutputStream());
			 stream = socket.getInputStream();
			 
//			 outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
			 
			 do
			 {
				 test = stream.read(data);
				 s = new String(data);
				 fullLengthData += new String(data);
				 if(s.equals("\r"))
				 {
					 test = stream.read(data);
					 s = new String(data);
					 fullLengthData += new String(data);
					 System.out.print("\\r");
					 if(s.equals("\n"))
					 {
						 test = stream.read(data);
						 s = new String(data);
						 fullLengthData += new String(data);
						 System.out.println("\\n");
						 if(s.equals("\r"))
						 {
							 test = stream.read(data);
							 s = new String(data);
							 fullLengthData += new String(data);
							 System.out.print("\\r");
							 if(s.equals("\n"))
							 {
								 fullLengthData += new String(data);
								 break;
							 }
						 }
					 }
				 }
				 System.out.print(s);
//				 outputStream.print(s);
			 }while(test != 0);
			 
//			 inputStream = new Scanner(new InputStreamReader(socket.getInputStream()/*new ByteArrayInputStream(byt)*/));
//			 bis = new ByteArrayInputStream(socket.getInputStream());
			 
			 
/*			 for(int i = 0; i < byt.length; i++)
			 {
				 System.out.println(Byte.toString(byt[i]));
			 }*/
			 // Connection made, set up streams
//			 inputStream = new Scanner(new InputStreamReader(socket.getInputStream()));
//		 	 outputStream = new PrintWriter(new
//		 			 DataOutputStream(socket.getOutputStream()));
		 	 
		 	// Read a line from the client

//		 	 while(inputStream.hasNext())		 	 {
//		 		 s = inputStream.nextLine();
//		 		 outputStream.println(s);
//		 		}

			 // Output text to the client
		 	 
//		 	 while(inputStream.hasNextLine())//for(int i = 0; i < 10; i++)
//		 	 {
//		 		 s = inputStream.nextLine();
//		 		 if(s.equals(""))
//		 		 outputStream.println(s);
//		 	 }	
//		 		 outputStream.println("Where the hell is this going");
		     
//			 outputStream.flush();
		     System.out.println("Closing connection");
		     stream.close();
//		     outputStream.close();
		     
		     this.allData = fullLengthData;
		  }
		  catch(UnsupportedEncodingException e)
		  {
			  e.printStackTrace();
		  }
		  catch (Exception e)
		  {
		     // If any exception occurs, display it
		     System.out.println("Error " + e);
		  }

		//asdfaskl;dfjal;sdkfj

	}

     /**
     * The webproxy logic goes here 
     */
	public void start()
	{
		String url = getWebsite();
		InputStream stream = null;
		PrintWriter outputStream = null;
		String output = "";
		byte [] data = new byte[1];
		int test;
		
		try {
			Socket socket = new Socket(url,PORT_NUMBER);
			stream = socket.getInputStream();
			outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
			test = stream.read(data);
			
			while(test != -1)
			{
				output += new String(data);
				test = stream.read(data);
			}
			outputStream.print(output);
			outputStream.flush();
			outputStream.close();
			stream.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String getWebsite()
	{
		char [] allDataArray = this.allData.toCharArray();
		String url = "";
		int index = 0;
		
		while(allDataArray[index] != 'h')
		{
			index++;
		}
		
		while(allDataArray[index] != ' ')
		{
			url += Character.toString(allDataArray[index]);
			index++;
		}
		
		System.out.println(url);
		
		return url;
	}
	

/**
 * A simple test driver
*/
	public static void main(String[] args) {
//   http://pages.cpsc.ucalgary.ca/~cyriac.james/sample.txt
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
