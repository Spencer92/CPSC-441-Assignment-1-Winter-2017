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
}
