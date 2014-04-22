package com.db.personalcontactmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.db.personalcontactmanager.databasemanager.DatabaseManager;
import com.db.personalcontactmanager.model.ContactModel;

public class AddNewContact extends Activity {

	private TextView contactName, contactPhone, contactEmail, contactPhoto;
	private Button doneButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		init();

		doneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				prepareSendData();

			}
		});

	}

	private void prepareSendData() {
		if (TextUtils.isEmpty(contactName.getText().toString())
				|| TextUtils.isEmpty(contactPhone.getText().toString()))

		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					AddNewContact.this);
			alertDialogBuilder.setTitle("Empty fields");
			alertDialogBuilder.setMessage("Please fill phone number and name")
					.setCancelable(true);
			alertDialogBuilder.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
			alertDialogBuilder.show();
		} else {
			ContactModel contact = new ContactModel();
			contact.setName(contactName.getText().toString());
			contact.setContactNo(contactPhone.getText().toString());
			contact.setEmail(contactEmail.getText().toString());
			contact.setPhoto(contactPhoto.getText().toString());
			DatabaseManager dm = new DatabaseManager(this);
			dm.addRow(contact);
			setResult(RESULT_OK);
			finish();

		}
	}

	private void init() {

		contactName = (TextView) findViewById(R.id.contactName);
		contactPhone = (TextView) findViewById(R.id.contactPhone);
		contactEmail = (TextView) findViewById(R.id.contactEmail);
		contactPhoto = (TextView) findViewById(R.id.contactPhoto);
		doneButton = (Button) findViewById(R.id.done);
	}

}
