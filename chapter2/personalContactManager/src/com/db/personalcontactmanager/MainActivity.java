package com.db.personalcontactmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
	final static String TAG = "MainActivity";
	final static int CON_REQ_CODE = 100;
	ListView listReminder;

	CustomListAdapter cAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button addNewButton = (Button) findViewById(R.id.addNew);
		listReminder = (ListView) findViewById(R.id.list);
		cAdapter = new CustomListAdapter(this);
		listReminder.setAdapter(cAdapter);

		addNewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MainActivity.this,
						AddNewContact.class);
				startActivityForResult(intent, CON_REQ_CODE);
			}
		});

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 100:
			if (resultCode == Activity.RESULT_OK) {
				cAdapter.notifyDataSetChanged();
			}
		}
	}
}
