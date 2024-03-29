package webproxy.proxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		  this.port = port;
		  
		  
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
			 
//			 outputStream.flush();
			 
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

		//asdfaskl;dfjal;sdkfj

	}

     /**
     * The webproxy logic goes here 
     */
	public void start()
	{
		String getRequest = getWebsiteRequest();
		String url = getHostRequest(getRequest);
		String fullUrl = httpRequest(getRequest);
		ServerSocket serverSocket = null;
		Socket socket = null;
		BufferedReader reader;
		BufferedWriter writer;
		String line;

//		ArrayList<Byte> outputBytes = new ArrayList<Byte>();
//		byte [] byteStream;
		
		InputStream stream = null;
		PrintWriter outputStream = null;
		PrintWriter displayText = null;
		String output = "";
		byte [] data = new byte[1];
		int test;
		
		try {
			
			System.out.println("request is " + getRequest);
			
			if(!isCached(fullUrl))
			{
				socket = new Socket(url,PORT_NUMBER);
				System.out.println(socket.getLocalSocketAddress());
				System.out.println(socket.getInetAddress());
				System.out.println("url is " + url);
	//			stream = socket.getInputStream();
				outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
				
				outputStream.println(getRequest);
	//			outputStream.println(fullUrl);
				outputStream.flush();
	
				
	//			outputStream.println("Wsadrfa");
	//			outputStream.flush();
				
				stream = socket.getInputStream();
				
				while(stream.available() == 0)
					;
				
				System.out.println(stream.available());
				System.out.println(stream.toString());
				
				
	//			displayText = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
				
	
				String s;
				
				do
				{
					test = stream.read(data);
					s = new String(data);
	//				outputStream.print(s);
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
				
	//			outputStream.flush();
				
				
				
				while(test != -1)
				{
					s = new String(data);
	//				outputBytes.add(data[0]);
					
					
					output += new String(data);
					test = stream.read(data);
					
	//				outputStream.append(s.charAt(0));
	//				displayText.write(s);
					
					if(!s.equals("\n") && !s.equals("\r"))
					{
						System.out.print(s);
					}
					else if(s.equals("\r"))
					{
						System.out.print("\\r");
					}
					else if(s.equals("\n"))
					{
						System.out.println("\\n");
					}
					
			
					
					
				}
				socket.close();
				outputStream.close();
				

				
				
				createFiles(output);
				stream.close();				
				
				
			}
			else
			{
				reader = new BufferedReader(new FileReader("http" + File.separator + this.hostName + File.separator + this.pathName));
				
				while((line = reader.readLine()) != null)
				{
					output += line + "\r";
				}
				
			}
			serverSocket = new ServerSocket(this.port);
			socket = serverSocket.accept();
//			outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
			outputStream = new PrintWriter(new DataOutputStream(socket.getOutputStream()));

			outputStream.print(output);
//			displayText.print(output);
//			displayText.flush();
			outputStream.flush();
			outputStream.close();
//			displayText.close();

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createFiles(String output)
	{
		File protocolidentifierfile;
		File hostNameFile;
		File pathNameFile;
		BufferedWriter writer;
		File parentFile;
		char [] outputArray = output.toCharArray();
		
		
		protocolidentifierfile = new File("http");
		if(!protocolidentifierfile.exists() || !protocolidentifierfile.isDirectory())
		{
			protocolidentifierfile.mkdir();
		}
		
		
		hostNameFile = new File("http" + File.separator + this.hostName);
		
		if(!hostNameFile.exists() || !hostNameFile.isDirectory())
		{
			hostNameFile.mkdir();
		}
		
		
		parentFile = new File("http" + File.separator + this.hostName);
		parentFile.mkdirs();
		
		pathNameFile = new File(parentFile,this.pathName);
		pathNameFile.getParentFile().mkdirs();
		
		
		if(!pathNameFile.exists() || pathNameFile.isDirectory())
		{
			try {
				
				pathNameFile.createNewFile();				
				writer = new BufferedWriter(new FileWriter(pathNameFile));
				
				for(int i = 0; i < outputArray.length; i++)
				{
					if(outputArray[i] != '\n')
					{
						writer.write(outputArray[i]);
					}
					else
					{
						writer.write("\r\n");
					}
				}
				
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getHostRequest(String websiteRequest)
	{
		char [] allDataArray = websiteRequest.toCharArray();
		String url = "";
		int index = 0;
		
		while(allDataArray[index] != '\n')
		{
			index++;
		}
		
		while(allDataArray[index] != ' ')
		{
			index++;
		}
		index++;
		
		while(allDataArray[index] != '\r')
		{
			url += Character.toString(allDataArray[index]);
			index++;
		}
		
		return url;
	}

	public String getWebsiteRequest()
	{
		char [] allDataArray = this.allData.toCharArray();
		String getRequest = "";
		int index = 0;
		
		
		
		do
		{
			getRequest += Character.toString(allDataArray[index]);
			index++;
		}while(allDataArray[index] != '\n');
		
		do
		{
			getRequest += Character.toString(allDataArray[index]);
			index++;
		}while(allDataArray[index] != '\n');
		
		getRequest += "\n";
		

		
		return getRequest;
	}
	
	public String httpRequest(String websiteRequest)
	{
		char[] websiteRequestArray = websiteRequest.toCharArray();
		String fullUrl = "";
		int index = 0;
		
		while(websiteRequestArray[index] != 'h')
		{
			index++;
		}
		
		while(websiteRequestArray[index] != ' ')
		{
			fullUrl += Character.toString(websiteRequestArray[index]);
			index++;
		}
		
		return fullUrl;
		
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
		
//		path = Paths.get("http" + hostName);
		
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
