package com.example.firstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView textView;
	private EditText editText;
	private Button button;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	private CheckBox checkBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
		editor = sp.edit();

		textView = (TextView) findViewById(R.id.textView1);
		editText = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button1);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);

		textView.setText("Hi world!");
		button.setText("submit");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submit();
			}
		});

		editText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				String text = editText.getText().toString();

				editor.putString("text", text);
				editor.commit();

				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					submit();
					return true;
				}

				return false;
			}
		});

		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				editor.putBoolean("checkBox", isChecked);
				editor.commit();
			}
		});

		loadSettings();
	}

	private void loadSettings() {
		String text = sp.getString("text", "");
		editText.setText(text);

		checkBox.setChecked(sp.getBoolean("checkBox", false));
	}

	private void submit() {
		String text = editText.getText().toString();
		if (checkBox.isChecked()) {
			text = "*********";
		}

		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		editText.setText("");

		editor.putString("text", "");
		editor.commit();

		Intent intent = new Intent();
		intent.setClass(this, MessageActivity.class);
		intent.putExtra("text", text);
		intent.putExtra("checkBox", checkBox.isChecked());
		startActivity(intent);
	}

	public void clickButton(View view) {
		Log.d("debug", "on click");

		String newText = editText.getText().toString();
		textView.setText(newText);
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
}
