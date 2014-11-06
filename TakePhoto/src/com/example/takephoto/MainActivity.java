package com.example.takephoto;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private static final int REQUEST_CODE_TAKE_PHOTO = 0;
	private static final int REQUEST_CODE_GALLERY = 1;

	private Uri extraOutput;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageView = (ImageView) findViewById(R.id.imageView1);
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
			Log.d("debug", "take photo");

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
			}
		} else if (requestCode == REQUEST_CODE_GALLERY) {
			if (resultCode == RESULT_OK) {
				Uri selectedImageUri = data.getData();
				imageView.setImageURI(selectedImageUri);

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
}
