package com.example.firstapp;

import com.example.firstapp.fragment.InputFragment;
import com.parse.Parse;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Parse.initialize(this, "j0mdZulYSEQYwHcqA0JwRwzIRfY4U0TNtV6Gw2Uz",
				"z7vFTTlQjLRMkVzNaCjIDMD5R1KqzQkoRzqBZBoS");

		InputFragment inputFragment = new InputFragment();

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.container, inputFragment);
		ft.commit();
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
