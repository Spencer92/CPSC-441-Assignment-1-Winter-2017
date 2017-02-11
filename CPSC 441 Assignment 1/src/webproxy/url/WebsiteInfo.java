package webproxy.url;

public class WebsiteInfo 
{
	
	/**
	 * Gets the website host e.g. pages.cpsc.ucalgary.ca
	 * 
	 * 
	 * @param websiteRequest - The entire request
	 * @return the website host
	 */
	public static String getHostRequest(String websiteRequest)
	{
		char [] allDataArray = websiteRequest.toCharArray();
		String url = "";
		int index = 0;
		
		while(allDataArray[index] != '\n')
		{
			index++;
		}
		
		while(allDataArray[index] != ' ')// in order to skip the space after "HOST:"
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
	
	
	/**
	 * Separates the website request from the rest of the header data
	 * 
	 * 
	 * @param allData - all the header data
	 * @return - the website request data
	 */
	
	public static String getWebsiteRequest(String allData)
	{
		char [] allDataArray = allData.toCharArray();
		String getRequest = "";
		int index = 0;
		
		
		
		
		//This needs to be done twice to make sure only the request is grabbed
		//without the rest of the header data
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
	
	/**
	 * Similar to hostRequest, but gets the entire http path
	 * e.g. http://pages.cpsc.ucalgary.ca/~cyriac.james/sample.txt
	 * 
	 * 
	 * @param websiteRequest - The string that requests the website
	 * @return the entire http path
	 */
	
	
	public static String httpRequest(String websiteRequest)
	{
		char[] websiteRequestArray = websiteRequest.toCharArray();
		String fullUrl = "";
		int index = 0;
		
		//get to the correct start
		while(websiteRequestArray[index] != 'h')
		{
			index++;
		}
		
		//add all the information
		while(websiteRequestArray[index] != ' ')
		{
			fullUrl += Character.toString(websiteRequestArray[index]);
			index++;
		}
		
		return fullUrl;
		
	}
}
