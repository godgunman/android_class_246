package com.example.firstapp.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.firstapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MessageFragment extends Fragment {
	private ListView listView;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_message, container,
				false);

		listView = (ListView) rootView.findViewById(R.id.listView1);

		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setTitle("Loading...");
		progressDialog.show();

		// progressDialog = new ProgressDialog(this);
		// progressDialog.setTitle("Loading... ");
		// progressDialog.show();

		if (getArguments() != null) {

			String text = getArguments().getString("text");
			boolean isChecked = getArguments().getBoolean("checkBox", false);

			Log.d("debug", "extra:" + text + "," + isChecked);
			saveToParse(text, isChecked);
		} else {
			loadMessageFromParse();
		}
		// TODO Auto-generated method stub
		return rootView;
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

	private void loadMessageFromParse() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException exception) {
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

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
				android.R.layout.simple_list_item_2, from, to);

		listView.setAdapter(adapter);
	}

}
