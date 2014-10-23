package com.example.simplelinearlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private LinearLayout linearLayout;
	private EditText editText1;
	private EditText editText2;
	private EditText editText3;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setPadding(15, 15, 15, 15);

		editText1 = new EditText(this);
		editText1.setHint("To");

		editText2 = new EditText(this);
		editText2.setHint("Subject");

		LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 0);
		messageParams.weight = 1;

		editText3 = new EditText(this);
		editText3.setHint("Message");
		editText3.setGravity(Gravity.TOP);
		editText3.setLayoutParams(messageParams);

		LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		buttonParams.gravity = Gravity.RIGHT;

		button = new Button(this);
		button.setText("Send");
		button.setLayoutParams(buttonParams);

		linearLayout.addView(editText1);
		linearLayout.addView(editText2);
		linearLayout.addView(editText3);
		linearLayout.addView(button);

		setContentView(linearLayout);
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
