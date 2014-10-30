package com.example.firstapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessageActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		listView = (ListView) findViewById(R.id.listView1);

		String text = getIntent().getStringExtra("text");
		boolean isChecked = getIntent().getBooleanExtra("checkBox", false);

		Log.d("debug", "extra:" + text + "," + isChecked);
		writeFile(text);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, readFile());
		listView.setAdapter(adapter);
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
}
