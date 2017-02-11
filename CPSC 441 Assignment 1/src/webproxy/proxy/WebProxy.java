package webproxy.proxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import webproxy.url.HelperFunctions;
import webproxy.url.WebsiteInfo;

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
	private PrintWriter outputStream = null;
	private int port;
	String hostName;
	String pathName;
	
     /**
     *  Constructor that initalizes the server listenig port
         *
     * @param port      Proxy server listening port
     */

	public WebProxy(int port) {

	/* Intialize server listening port */	
		//Modified from Server1.java
		  String s = "";
		  String fullLengthData = "";
		  InputStream stream;
		  ServerSocket serverSocket = null;
		  int test;
		  byte [] data = new byte[1];
		  this.port = port;
		  
		  
		  
		  try
		  {
			 // Wait for connection on port 8888
			 System.out.println("Waiting for a connection.");
			 serverSocket = new ServerSocket(port);

			 Socket socket = serverSocket.accept();

			 stream = socket.getInputStream();
			 
			 outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
			 
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
					 if(s.equals("\n"))
					 {
						 test = stream.read(data);
						 s = new String(data);
						 fullLengthData += new String(data);
						 if(s.equals("\r"))
						 {
							 test = stream.read(data);
							 s = new String(data);
							 fullLengthData += new String(data);
							 if(s.equals("\n"))
							 {
								 fullLengthData += new String(data);
								 break;
							 }
						 }
					 }
				 }
			 }while(test != 0);
			 

		     stream.close();
		     
		     this.allData = fullLengthData;
		     serverSocket.close();
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

	}

     /**
     * The webproxy logic goes here 
     */
	public void start()
	{
		String getRequest = WebsiteInfo.getWebsiteRequest(this.allData);
		String url = WebsiteInfo.getHostRequest(getRequest);
		String fullUrl = WebsiteInfo.httpRequest(getRequest);
		ServerSocket serverSocket = null;
		Socket socket = null;
		BufferedReader reader;
		String line;

		
		InputStream stream = null;
		PrintWriter outputStream = null;
		String output = "";
		byte [] data = new byte[1];
		int test;
		
		try {
			
			
			if(!isCached(fullUrl)) 
			{
				socket = new Socket(url,PORT_NUMBER);
				outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
				
				outputStream.println(getRequest);
				outputStream.flush();
				stream = socket.getInputStream();
				
				while(stream.available() == 0)
					;
								
				
				String s;
				
				do
				{
					test = stream.read(data);
					s = new String(data);

					if(s.equals("\r"))
					{
						test = stream.read(data);
						s = new String(data);
						if(s.equals("\n"))
						{
							test = stream.read(data);
							s = new String(data);
							if(s.equals("\r"))
							{
								test = stream.read(data);
								s = new String(data);
								if(s.equals("\n"))
								{
									test = stream.read(data);
									break;
								}
							}
						}
					}
				}while(test != -1);
				
				
				while(test != -1)
				{
					s = new String(data);
					
					output += new String(data);
					test = stream.read(data);					
					
				}
				socket.close();
				outputStream.close();
				

				
				
				HelperFunctions.createFiles(output,this.pathName,this.hostName);
				stream.close();				
				
				
			}
			else
			{
				reader = new BufferedReader(new FileReader("http" + File.separator + this.hostName + File.separator + this.pathName));
				
				while((line = reader.readLine()) != null)
				{
					output += line + "\r";
				}
				System.out.println("used cache");
			}
			serverSocket = new ServerSocket(this.port);
			socket = serverSocket.accept();
			outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));

			outputStream.print(output);
			outputStream.flush();
			outputStream.close();

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	boolean isCached(String website)
	{
		char [] websiteArray = website.toCharArray();
		File protocolidentifierfile;
		File hostNameFile;
		File pathNameFile;
		Path path;
		
		String protocolidentifier = "http";
		String hostName = "";
		String pathName = "";
		int index = 7; //Where the website name starts

		
		
		protocolidentifierfile = new File(protocolidentifier);

		
		while(websiteArray[index] != '/')
		{
			hostName += Character.toString(websiteArray[index]);
			index++;
		}
		index++;

		while(index < websiteArray.length)
		{
			pathName += Character.toString(websiteArray[index]);
			index++;
		}
		
		pathNameFile = new File(pathName);
		this.pathName = pathName;
		
		hostNameFile = new File(hostName);
		this.hostName = hostName;

		
		
		
		
		if(!Files.exists(Paths.get("http")))
		{
			return false;
		}
		
		if(!Files.exists(Paths.get("http" + "/" + hostName)))
		{		
			return false;
		}
		
		if(!Files.exists(Paths.get("http" + "/" + hostName + "/" + pathName)))
		{
			return false;
		}
		
		return true;
	}
	
	
//http://pages.cpsc.ucalgary.ca/~cyriac.james/readlist.pdf	
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
