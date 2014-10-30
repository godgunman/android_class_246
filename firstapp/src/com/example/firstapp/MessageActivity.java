package com.example.firstapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MessageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		String text = getIntent().getStringExtra("text");
		boolean isChecked = getIntent().getBooleanExtra("checkBox", false);
		
		Log.d("debug", "extra:" + text + "," + isChecked);
	}
}
