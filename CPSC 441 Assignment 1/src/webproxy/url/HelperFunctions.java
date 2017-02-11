package webproxy.url;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HelperFunctions
{
	
	/**
	 * 
	 * Creates the path and file for caching
	 * and writes to the newly created file
	 * 
	 * @param output - The output that will be sent to a file
	 * @param pathName - the path name that the output file will need
	 * @param hostName - the host name that the output file will need
	 */
	
	public static void createFiles(String output, String pathName, String hostName)
	{
		File protocolidentifierfile;
		File hostNameFile;
		File pathNameFile;
		BufferedWriter writer;
		File parentFile;
		char [] outputArray = output.toCharArray();
		
		
		//Create the "http" folder
		protocolidentifierfile = new File("http");
		if(!protocolidentifierfile.exists() || !protocolidentifierfile.isDirectory())
		{
			protocolidentifierfile.mkdir();
		}
		
		
		//Create the host name folder
		hostNameFile = new File("http" + File.separator + hostName);
		if(!hostNameFile.exists() || !hostNameFile.isDirectory())
		{
			hostNameFile.mkdir();
		}
		
		
		parentFile = new File("http" + File.separator + hostName);
		parentFile.mkdirs();
		
		
		//Create the path name
		pathNameFile = new File(parentFile,pathName);
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
						writer.write("\r\n"); //since windows systems need the \r
					}
				}
				
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
