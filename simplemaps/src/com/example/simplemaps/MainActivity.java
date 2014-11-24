package com.example.simplemaps;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final LatLng NTU = new LatLng(25.019319, 121.541540);
	private GoogleMap map;

	private LocationListener locationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(Location location) {
			updateGoogleMap(location);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);

		map = mapFragment.getMap();

		MarkerOptions markOption = new MarkerOptions().position(NTU).title(
				"NTU");
		Marker marker = map.addMarker(markOption);

		map.setMyLocationEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(NTU, 15));
		map.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				Toast.makeText(
						MainActivity.this,
						marker.getTitle() + " "
								+ marker.getPosition().toString(),
						Toast.LENGTH_SHORT).show();

				return false;
			}
		});
		
		Location location = map.getMyLocation();
		updateGoogleMap(location);
		
		initLocationManager();
	}

	private void updateGoogleMap(Location location) {
		LatLng myLocation = new LatLng(location.getLatitude(),
				location.getLongitude());
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
	}

	private void initLocationManager() {

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, false);
		
		locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);
		
		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			updateGoogleMap(location);
		}

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
