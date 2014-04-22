package com.db.personalcontactmanager;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.personalcontactmanager.databasemanager.DatabaseManager;
import com.db.personalcontactmanager.model.ContactModel;

public class CustomListAdapter extends BaseAdapter {
	DatabaseManager dm;
	ArrayList<ContactModel> smsModelList;
	LayoutInflater inflater;
	Context _context;

	public CustomListAdapter(Context context) {

		smsModelList = new ArrayList<ContactModel>();
		_context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dm = new DatabaseManager(_context);
		smsModelList = dm.getAllData();

	}

	@Override
	public int getCount() {
		return smsModelList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder vHolder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.contact_list_row, null);
			vHolder = new ViewHolder();

			vHolder.contact_name = (TextView) vi
					.findViewById(R.id.contact_name);
			vHolder.contact_phone = (TextView) vi
					.findViewById(R.id.contact_phone);
			vHolder.contact_email = (TextView) vi
					.findViewById(R.id.contact_email);
			vHolder.contact_photo = (ImageView) vi
					.findViewById(R.id.contact_photo);
			vi.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) vi.getTag();
		}
		ContactModel contactObj = smsModelList.get(position);

		vHolder.contact_name.setText(contactObj.getName());
		vHolder.contact_phone.setText(contactObj.getContactNo());
		vHolder.contact_email.setText(contactObj.getEmail());

		return vi;
	}

	static class ViewHolder {
		ImageView contact_photo;
		TextView contact_name, contact_phone, contact_email;
	}
}
