package webproxy.url;

public class WebsiteInfo 
{
	public static String getHostRequest(String websiteRequest)
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
	
	public static String getWebsiteRequest(String allData)
	{
		char [] allDataArray = allData.toCharArray();
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
	
	public static String httpRequest(String websiteRequest)
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
}
