package com.example.takephoto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private static final int REQUEST_CODE_TAKE_PHOTO = 0;
	private static final int REQUEST_CODE_GALLERY = 1;

	private Uri extraOutput;
	private ImageView imageView;
	private LinearLayout linearLayout;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Parse.initialize(this, "6GIweBfY6S45aUHHhzAkw4cgo6Cb7PlvUyYYwJFs",
				"nEFIK6PmEiidO3qnyvPa04WCi9rJCECOvN8qg5vf");

		imageView = (ImageView) findViewById(R.id.imageView1);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		progressDialog = new ProgressDialog(this);

		loadPhotoFromParse();
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
		} else if (id == R.id.action_take_photo) {
			extraOutput = getOutputUri();

			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, extraOutput);
			startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
			return true;
		} else if (id == R.id.action_gallery) {

			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, REQUEST_CODE_GALLERY);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
			if (resultCode == RESULT_OK) {
				// Bitmap bitmap = data.getParcelableExtra("data");
				// imageView.setImageBitmap(bitmap);
				imageView.setImageURI(extraOutput);
				saveToParse(extraOutput);
			}
		} else if (requestCode == REQUEST_CODE_GALLERY) {
			if (resultCode == RESULT_OK) {
				Uri selectedImageUri = data.getData();
				imageView.setImageURI(selectedImageUri);
				saveToParse(selectedImageUri);
			}
		}
	}

	private Uri getOutputUri() {
		File dcimDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		if (dcimDir.exists() == false) {
			dcimDir.mkdirs();
		}
		File file = new File(dcimDir, "photo.png");
		return Uri.fromFile(file);
	}

	private byte[] uriToBytes(Uri uri) {
		try {
			InputStream is = getContentResolver().openInputStream(uri);
			ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				byteBuffer.write(buffer, 0, len);
			}
			return byteBuffer.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void saveToParse(Uri uri) {
		byte[] bytes = uriToBytes(uri);

		ParseObject object = new ParseObject("Photo");
		final ParseFile file = new ParseFile("photo.png", bytes);

		object.put("file", file);
		object.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				Log.d("debug", file.getUrl());
				loadPhotoFromParse();
			}
		});
	}

	private void loadPhotoFromParse() {
		progressDialog.setTitle("Loading...");
		progressDialog.show();
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Photo");
		query.orderByDescending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				linearLayout.removeAllViews();
				
				for (ParseObject object : objects) {
					ParseFile file = object.getParseFile("file");

					try {
						byte[] data = file.getData();
						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);

						ImageView imageView = new ImageView(MainActivity.this);
						imageView.setImageBitmap(bitmap);

						linearLayout.addView(imageView);
						
						Log.d("debug", file.getName());

					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				progressDialog.dismiss();
			}
		});
	}
}
