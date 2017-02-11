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
		  int testForEnd;
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
			 
			 
			 //The end of the stream is reached if \r\n\r\n is reached
			 //Or if the stream ends
			 do
			 {
				 testForEnd = stream.read(data);
				 s = new String(data);
				 fullLengthData += new String(data);
				 if(s.equals("\r"))
				 {
					 testForEnd = stream.read(data);
					 s = new String(data);
					 fullLengthData += new String(data);
					 if(s.equals("\n"))
					 {
						 testForEnd = stream.read(data);
						 s = new String(data);
						 fullLengthData += new String(data);
						 if(s.equals("\r"))
						 {
							 testForEnd = stream.read(data);
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
			 }while(testForEnd != -1);
			 

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
		
		//Split the website information up
		String getRequest = WebsiteInfo.getWebsiteRequest(this.allData);
		String url = WebsiteInfo.getHostRequest(getRequest);
		String fullUrl = WebsiteInfo.httpRequest(getRequest);

		
		ServerSocket serverSocket = null;
		Socket socket = null;
		BufferedReader reader;
		String line;
		String checkData;
		
		InputStream stream = null;
		PrintWriter outputStream = null;
		String output = "";
		byte [] data = new byte[1];
		int testForEnd;
		
		try {
			
			
			if(!isCached(fullUrl)) 
			{
				socket = new Socket(url,PORT_NUMBER);
				outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
				
				outputStream.println(getRequest);
				outputStream.flush();
				stream = socket.getInputStream();
				
				
				//wait for the stream to respond
				while(stream.available() == 0)
					;
				
				//keep reading until you hit \r\n\r\n, as that is the end of header information
				do
				{
					testForEnd = stream.read(data);
					checkData = new String(data);

					if(checkData.equals("\r"))
					{
						testForEnd = stream.read(data);
						checkData = new String(data);
						if(checkData.equals("\n"))
						{
							testForEnd = stream.read(data);
							checkData = new String(data);
							if(checkData.equals("\r"))
							{
								testForEnd = stream.read(data);
								checkData = new String(data);
								if(checkData.equals("\n"))
								{
									testForEnd = stream.read(data);
									break;
								}
							}
						}
					}
				}while(testForEnd != -1);
				//If the test reaches -1, the end of the stream has been reached
				
				while(testForEnd != -1)
				{
					checkData = new String(data);
					
					output += new String(data);
					testForEnd = stream.read(data);					
					
				}
				socket.close();
				outputStream.close();

				HelperFunctions.createFiles(output,this.pathName,this.hostName);
				stream.close();				
				
				
			}
			else //If the website was cached
			{
				reader = new BufferedReader(new FileReader("http" + File.separator + this.hostName + File.separator + this.pathName));
				
				while((line = reader.readLine()) != null)
				{
					output += line + "\r";
				}
				System.out.println("used cache");
			}
			
			//display the output that was given
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


	/**
	 * 
	 * Checks whether a website has been cached or not
	 * 
	 * @param website - The website to be checked
	 * @return whether or not the website was cached
	 */
	
	
	boolean isCached(String website)
	{
		char [] websiteArray = website.toCharArray();
		
		String hostName = "";
		String pathName = "";
		int index = 7; //Where the website name starts

		
		
		//separate the host name and path name
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
		this.pathName = pathName;
		this.hostName = hostName;


		//Check if the paths exist
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
