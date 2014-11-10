package com.example.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String API_CLIENT_ID = "S1LC42PP1ZRDU5VWZIIZBIVOVACP4DXX0R5SVSXBQHJS3UP1";
	private static final String API_CLIENT_SECRET = "FCB500VP5QGJEH3GYNHRICNMLZMWXLEOOM0FK30ET5RHTIUC";
	private static final String API_VERSION = "20140806";

	private TextView textView;
	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textView1);
		editText = (EditText) findViewById(R.id.editText1);

		// disableStrictMode();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void disableStrictMode() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	private String fetch(String urlStr) {

		try {
			URL url = new URL(urlStr);
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()));

			String line = null;
			StringBuilder builder = new StringBuilder();
			while ((line = bufferReader.readLine()) != null) {
				builder.append(line);
			}

			return builder.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private String fetch2(String urlStr) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(urlStr);

		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpClient.execute(get, responseHandler);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void click(View view) {
		try {
			String address = URLEncoder.encode(editText.getText().toString(),
					"utf-8");
			String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
					+ address;

			new NetworkRunner().execute(url, "geocoding");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};

	private void callFoursquare(double lat, double lng) {
		String ll = lat + "," + lng;
		String url = String
				.format("https://api.foursquare.com/v2/venues/search?ll=%s&client_id=%s&client_secret=%s&v=%s",
						ll, API_CLIENT_ID, API_CLIENT_SECRET, API_VERSION);
		new NetworkRunner().execute(url, "foursquare");
	}

	class NetworkRunner extends AsyncTask<String, Integer, String> {

		private String type;

		@Override
		protected String doInBackground(String... params) {
			type = params[1];
			return fetch(params[0]);
		}

		protected void onPostExecute(String result) {
			if (type.equals("geocoding")) {
				try {
					JSONObject object = new JSONObject(result);
					JSONObject result0 = object.getJSONArray("results")
							.getJSONObject(0);
					JSONObject location = result0.getJSONObject("geometry")
							.getJSONObject("location");

					String formattedAddress = result0
							.getString("formatted_address");
					double lat = location.getDouble("lat");
					double lng = location.getDouble("lng");

					textView.setText(formattedAddress + "," + lat + "," + lng);

					callFoursquare(lat, lng);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (type.equals("foursquare")) {
				try {
					JSONObject object = new JSONObject(result);
					JSONArray venues = object.getJSONObject("response")
							.getJSONArray("venues");
					for (int i = 0; i < venues.length(); i++) {
						String name = venues.getJSONObject(i).getString("name");
						Log.d("debug", "name: " + name);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

}
