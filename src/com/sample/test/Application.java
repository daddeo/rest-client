package com.sample.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Application {
	public static final String apiURL = "http://localhost:8080/demo/webapi/check";
	
	public static void main(String[] args) {
		final String[] addresses = {
				"test.email@gmail.com",
				"test.email+spam@gmail.com",
				"testemail@gmail.com"
		};
/* another data set
		final String[] addresses = {
				"email@gmail.com",
				"test.email@fetchrewards.com"
		};
*/

		try {
			URL url = new URL(apiURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "text/plain");

			String input = "{\"list\": [";
		    for (int idx = 0; idx < addresses.length; idx++) {
		    	if (idx > 0) {
		    		input += ",";
		    	}
		        input += "\"" + addresses[idx] + "\"";
		    }
			input += "]}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			// HTTP_CREATED
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server ....");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
