package com.example.firstapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MessageActivity extends Activity {

	private ListView listView;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		listView = (ListView) findViewById(R.id.listView1);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Loading...");
		progressDialog.show();

		// progressDialog = new ProgressDialog(this);
		// progressDialog.setTitle("Loading... ");
		// progressDialog.show();

		String text = getIntent().getStringExtra("text");
		boolean isChecked = getIntent().getBooleanExtra("checkBox", false);

		Log.d("debug", "extra:" + text + "," + isChecked);
		writeFile(text);
		saveToParse(text, isChecked);

//		loadMessageFromParse();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, readFile());
		listView.setAdapter(adapter);
	}

	private void saveToParse(String text, boolean checked) {
		ParseObject message = new ParseObject("Message");
		message.put("text", text);
		message.put("checked", checked);
		message.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException arg0) {
				// TODO Auto-generated method stub
				loadMessageFromParse();
			}
		});
	}

	private void writeFile(String text) {
		text += "\n";

		try {
			FileOutputStream fos = openFileOutput("message.txt",
					Context.MODE_APPEND);
			fos.write(text.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private List<String> readFile() {
		try {
			FileInputStream fis = openFileInput("message.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			List<String> history = new ArrayList<String>();
			String str = null;
			while ((str = br.readLine()) != null) {
				history.add(str);
			}

			return history;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void loadMessageFromParse() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException exception) {
				for (ParseObject object : objects) {
					Log.d("debug", object.getString("foo"));
				}
				setListView(objects);
				progressDialog.dismiss();
			}

		});

	}

	private void setListView(List<ParseObject> messages) {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (ParseObject message : messages) {
			Map<String, String> item = new HashMap<String, String>();
			item.put("text", message.getString("text"));
			item.put("checked", "" + message.getBoolean("checked"));

			data.add(item);
		}
		String[] from = new String[] { "text", "checked" };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, from, to);
		
		listView.setAdapter(adapter);
	}
	// private void setListView(List<ParseObject> messages){
	// List<Map<String, String>> data = new ArrayList<Map<String,String>>();
	// for(ParseObject message : messages){
	// Map<String, String> item = new HashMap<String, String>();
	// item.put("text", message.getString("text"));
	// item.put("checked", "" + message.getBoolean("checked"));
	//
	// data.add(item);
	// }
	// String[] from = new String[] { "text", "checked" };
	// int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
	//
	// SimpleAdapter adapter = new SimpleAdapter(this, data,
	// android.R.layout.simple_list_item_2, from, to);
	// listView.setAdapter(adapter);
	// }
}
