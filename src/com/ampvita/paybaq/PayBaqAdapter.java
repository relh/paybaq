package com.ampvita.paybaq;

import java.io.FileOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PayBaqAdapter extends ArrayAdapter<String> {
	Context context;
	int layoutResourceId;
	List<String> data;
	private ArrayAdapter<String> myself = this;
	private int pos;

	public PayBaqAdapter(Context context, int layoutResourceId,
			List<String> payBaqArray) {
		super(context, layoutResourceId, payBaqArray);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = payBaqArray;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		InfoHolder holder = null;
		final String activity = data.get(position);

		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new InfoHolder();
			holder.nameAmt = (TextView)row.findViewById(R.id.textInfo);
			holder.paid = (Button)row.findViewById(R.id.buttonPaid);
			holder.reminder = (Button)row.findViewById(R.id.buttonReminder);

			row.setTag(holder);
		}
		else
		{
			holder = (InfoHolder)row.getTag();
		}

		holder.nameAmt.setText(data.get(position));
		holder.paid.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				data.remove(activity);
				myself.notifyDataSetChanged();
			}
		});

		holder.reminder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String[] parts = myself.getItem(pos).split("\\t");
				
				Log.v("tired", parts[0]);
				Log.v("tired", parts[1]);
				Log.v("tired", parts[2]);
				if (parts.length > 3) {
					data.set(pos, parts[0] + " " + parts[1] + "\t" + parts[2] + "\t" + (Integer.parseInt(parts[3])+1));
				} else if (parts.length == 3) {
					data.set(pos, parts[0] + "\t" + parts[1] + "\t" + (Integer.parseInt(parts[2])+1));
				}
				myself.notifyDataSetChanged();
			}
		});

		return row;

	}

	static class InfoHolder {
		TextView nameAmt;
		Button paid;
		Button reminder;
	}

	@Override
	public void remove(String object) {
		// TODO Auto-generated method stub
		super.remove(object);
	}
}