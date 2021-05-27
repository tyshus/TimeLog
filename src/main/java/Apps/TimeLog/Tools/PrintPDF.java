package Apps.TimeLog.Tools;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.cloudmersive.client.invoker.ApiClient;
import com.cloudmersive.client.invoker.ApiException;
import com.cloudmersive.client.invoker.Configuration;
import com.cloudmersive.client.invoker.auth.*;
import com.cloudmersive.client.ConvertDocumentApi;

public class PrintPDF {

	public PrintPDF(String docFile, String filename) throws IOException {

		ApiClient defaultClient = Configuration.getDefaultApiClient();

		// Configure API key authorization: Apikey
		ApiKeyAuth Apikey = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
		Apikey.setApiKey("5f628dfe-177a-4181-a6ab-2f4bc7fc61bb");
		// Uncomment the following line to set a prefix for the API key, e.g. "Token"
		// (defaults to null)
		// Apikey.setApiKeyPrefix("Token");

		ConvertDocumentApi apiInstance = new ConvertDocumentApi();
		File inputFile = new File(docFile); // File | Input file to perform the operation on.
		try {
			byte[] result = apiInstance.convertDocumentDocxToPdf(inputFile);
			FileUtils.writeByteArrayToFile(new File(filename), result);

		} catch (ApiException e) {
			System.err.println("Exception when calling ConvertDocumentApi#convertDocumentDocxToPdf");
			e.printStackTrace();
		}
	}
}
