package webproxy.url;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HelperFunctions
{
	
	public static void createFiles(String output, String pathName, String hostName)
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
		
		
		hostNameFile = new File("http" + File.separator + hostName);
		
		if(!hostNameFile.exists() || !hostNameFile.isDirectory())
		{
			hostNameFile.mkdir();
		}
		
		
		parentFile = new File("http" + File.separator + hostName);
		parentFile.mkdirs();
		
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
	
}
