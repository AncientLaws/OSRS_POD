package main_pod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class GET extends Connect {
	private URL GET_1;
	private Object grab;
	private int[] grabno;

	GET() {
	}

	protected GET(String url) {
		GETTER(url);
	}

	protected GET(String url, Object o) {
		this.grab = o;
		GETTER(url, o);

	}

	protected GET(String url, int arr[]) {
		this.grabno = arr;
		GETTER(url, grabno);

	}// edit

	/** Overloaded GETTER Methods to get API Response */
	private void GETTER(String url) {
		try {
			HttpURLConnection http = httpStringURL(url); // Setting http variable to equal predefined values returned by
															// httpStringURL method
			if (HttpURLConnection.HTTP_OK == responseCode) { // Success: Status = 200
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
				StringBuffer response = new StringBuffer();
				String READ_INPUT_LINE_FROM_SITE;
				// Reading input from BufferedReader into StringBuffer
				while ((READ_INPUT_LINE_FROM_SITE = in.readLine()) != null) {
					response.append(READ_INPUT_LINE_FROM_SITE);
				}
				in.close();
				System.out.println(response);

			}
		}

		catch (Exception e) {
			System.out.println(" " + e.getMessage());
		}

	}

	private void GETTER(String url, Object o) {
		try {
			/*************************************
			 * Setting up connection & Reading Input Stream
			 ***********************************/
			HttpURLConnection http = httpStringURL(url); // Setting http variable to equal predefined values returned by
															// httpStringURL method
			if (HttpURLConnection.HTTP_OK == responseCode) { // Success: Status = 200
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
				StringBuffer response = new StringBuffer();
				String READ_INPUT_LINE_FROM_SITE;
				// Reading input from BufferedReader into StringBuffer
				while ((READ_INPUT_LINE_FROM_SITE = in.readLine()) != null) {
					response.append(READ_INPUT_LINE_FROM_SITE);
				}
				in.close();
				// Assign output to JSONObject
				String jsonString = response.toString();
				JSONObject obj = new JSONObject(jsonString);
				Object target = obj.get(o.toString());
				System.out.println(target);
			}
		}

		catch (Exception e) {
			System.out.println(" " + e.getMessage());
		}

	}

	private void GETTER(String url, int[] i) {
		try {
			HttpURLConnection http = httpStringURL(url); // Setting http variable to equal predefined values returned by
															// httpStringURL method
			if (HttpURLConnection.HTTP_OK == responseCode) { // Success: Status = 200
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
				StringBuffer response = new StringBuffer();
				String READ_INPUT_LINE_FROM_SITE;
				// Reading input from BufferedReader into StringBuffer
				while ((READ_INPUT_LINE_FROM_SITE = in.readLine()) != null) {
					response.append(READ_INPUT_LINE_FROM_SITE);
				}
				in.close();
				// Assign output to JSONObject

				String jsonString = response.toString();
				JSONObject obj = new JSONObject(jsonString);
				Object JSON_KEYS = null;

				for (int k = 0; k < i.length; k++) {
					int KEY_INDEX;
					String KEY_STRING;
					String KEY_STRING_VALUE;
					if (i[k] < obj.length()) {
						KEY_INDEX = i[k];
						JSON_KEYS = obj.keySet();
						String JSON_KEYS_STRING = JSON_KEYS.toString();
						JSON_KEYS_STRING = JSON_KEYS_STRING.substring(1, JSON_KEYS_STRING.length() - 1);
						// System.out.println(JSON_KEYS_STRING);
						String JKS_Arr[];
						JKS_Arr = JSON_KEYS_STRING.split(",");
						KEY_STRING = JKS_Arr[KEY_INDEX].trim();
						KEY_STRING_VALUE = obj.get(KEY_STRING).toString();

						System.out.println(KEY_STRING_VALUE);

					} else {
						System.out.println("Index out of bounds");
					}
				}
			}
		} catch (Exception e) {
			System.out.println(" " + e.getMessage());
		}
	}

}
