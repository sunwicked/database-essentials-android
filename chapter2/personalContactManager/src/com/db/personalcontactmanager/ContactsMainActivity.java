package com.db.personalcontactmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;

public class ContactsMainActivity extends Activity implements OnClickListener {

	final static String TAG = "MainActivity";
	final static int CON_REQ_CODE = 100;
	private ListView listReminder;
	private Button addNewButton;

	private CustomListAdapter cAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		bindViews();
		setListeners();

		cAdapter = new CustomListAdapter(this);
		listReminder.setAdapter(cAdapter);
		registerForContextMenu(listReminder);

	}

	private void bindViews() {
		addNewButton = (Button) findViewById(R.id.addNew);
		listReminder = (ListView) findViewById(R.id.list);
	}

	private void setListeners() {
		addNewButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.addNew:
			Intent intent = new Intent(ContactsMainActivity.this,
					AddNewContactActivity.class);
			startActivityForResult(intent, CON_REQ_CODE);
			break;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater m = getMenuInflater();
		m.inflate(R.menu.del_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_item:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			cAdapter.delRow((int) info.id);
			cAdapter.notifyDataSetChanged();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CON_REQ_CODE) {
			if (resultCode == RESULT_OK) {
				cAdapter.notifyDataSetChanged();
			} else if (resultCode == RESULT_CANCELED) {

			}
		}
	}

}
