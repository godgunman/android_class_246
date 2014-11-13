package com.example.firstapp.fragment;

import com.example.firstapp.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class InputFragment extends Fragment {

	private TextView textView;
	private EditText editText;
	private Button button;
	private CheckBox checkBox;

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public InputFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		sp = getActivity().getSharedPreferences("settings",
				Context.MODE_PRIVATE);
		editor = sp.edit();

		View rootView = inflater.inflate(R.layout.fragment_input, container, false);

		textView = (TextView) rootView.findViewById(R.id.textView1);
		editText = (EditText) rootView.findViewById(R.id.editText1);
		button = (Button) rootView.findViewById(R.id.button1);
		checkBox = (CheckBox) rootView.findViewById(R.id.checkBox1);

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
		return rootView;
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

		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
		editText.setText("");

		editor.putString("text", "");
		editor.commit();

		Bundle args = new Bundle();
		args.putString("text", text);
		args.putBoolean("checkBox", checkBox.isChecked());

		MessageFragment messageFragment = new MessageFragment();
		messageFragment.setArguments(args);
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.container, messageFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void clickButton(View view) {

		String newText = editText.getText().toString();
		textView.setText(newText);
	}

}
