package beispielAusVL;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ThreadAPI {

	public static void main(String[] args) throws InterruptedException, IOException {
		syncRequest();
		
		Thread t1 = new Thread(()->{
			try {
				syncRequest();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//do stuff
		});
		t1.start();
		
		asyncRequest();

		Thread.sleep(1000);
	}
	
	private static void syncRequest() throws IOException, InterruptedException {

		var client = HttpClient.newHttpClient();

		var request = HttpRequest.newBuilder(
		       URI.create("https://www.tradegate.de/refresh.php?isin=US36467W1099"))
		   .header("accept", "application/json")
		   .build();

		var response = client.send(request, BodyHandlers.ofString());

		System.out.println(response.body());

		ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		var jsonBody = om.readValue(response.body(), TradeTick.class);

		double value = Double.parseDouble( jsonBody.last.replace(",",".") );
		System.out.println(value);
	}
	
	private static void asyncRequest() {
		var client = HttpClient.newHttpClient();

		var request = HttpRequest.newBuilder(
			       URI.create("https://www.tradegate.de/refresh.php?isin=US36467W1099"))
				   .header("accept", "application/json")
				   .build();
		
		ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		client.sendAsync(request, BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenApply(x -> {
			try {
				return om.readValue(x, TradeTick.class);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		})
        .thenAccept(x-> {
        	double value = Double.parseDouble( x.last.replace(",",".") );
        	System.out.println(value);
        });
	}
}

class TradeTick{
    public String last;
}
