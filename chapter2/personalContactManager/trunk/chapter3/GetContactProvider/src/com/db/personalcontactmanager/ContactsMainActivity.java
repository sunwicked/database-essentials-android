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

import com.db.personalcontactmanager.model.ContactModel;
import com.example.getcontactprovider.R;

public class ContactsMainActivity extends Activity implements OnClickListener {

	final static String TAG = "MainActivity";
	public final static int CONTACT_ADD_REQ_CODE = 100;
	public final static int CONTACT_UPDATE_REQ_CODE = 101;
	public final static String REQ_TYPE ="req_type";
	public final static String ITEM_POSITION ="item_position";
	
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
			intent.putExtra(REQ_TYPE, CONTACT_ADD_REQ_CODE);
			startActivityForResult(intent, CONTACT_ADD_REQ_CODE);
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
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		ContactModel contactObj = (ContactModel) cAdapter.getItem(info.position);
		
		switch (item.getItemId()) {
		case R.id.delete_item:
			
			cAdapter.delRow(info.position);
			cAdapter.notifyDataSetChanged();
			return true;
		case R.id.update_item:
			
			Intent intent = new Intent(ContactsMainActivity.this,
					AddNewContactActivity.class);
			intent.putExtra(ITEM_POSITION, contactObj.getId());
			intent.putExtra(REQ_TYPE, CONTACT_UPDATE_REQ_CODE);
			startActivityForResult(intent, CONTACT_ADD_REQ_CODE);
			
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CONTACT_ADD_REQ_CODE) {
			if (resultCode == RESULT_OK) {
				cAdapter.notifyDataSetChanged();
			} else if (resultCode == RESULT_CANCELED) {

			}
		} else if (requestCode == CONTACT_UPDATE_REQ_CODE) {
			if (resultCode == RESULT_OK) {
				cAdapter.notifyDataSetChanged();
			} else if (resultCode == RESULT_CANCELED) {

			}
		} 
	}

}
