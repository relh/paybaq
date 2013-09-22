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
			holder.display = (TextView)row.findViewById(R.id.textInfo);
			holder.paid = (Button)row.findViewById(R.id.buttonPaid);
			holder.reminder = (Button)row.findViewById(R.id.buttonReminder);

			row.setTag(holder);
		}
		else
		{
			holder = (InfoHolder)row.getTag();
		}

		holder.info = data.get(position);
		String[] parts = holder.info.split("\\t");
		holder.display.setText(parts[0]); // Name
		holder.display.setText(holder.display.getText() + " owes $" + parts[3]); // Amount
		holder.display.setText(holder.display.getText() + " for " + parts[2]); // Amount
		holder.display.setText(holder.display.getText() + " (reminded " + parts[4] + " times)"); // Amount
		
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
				String[] parts = activity.split("\\t");
				data.remove(activity);
				
				// Name, Number, Reason, Amount, Level
				if (parts.length == 5) {
					int k = Integer.parseInt(parts[4]) + 1;
					if (k <= 10) {
						data.add(parts[0] + "\t" + parts[1] + "\t" + parts[2] + "\t" + parts[3] + "\t" + k);
						int val = (int) (Math.random()*Integer.parseInt(StartActivity.tiers[k-1][0]));
						String msg = StartActivity.tiers[k-1][val+1];
						msg = msg.replace("[reason]", parts[2]);
						msg = msg.replace("[price]", parts[3]);
						new SendSMS().execute(parts[1], msg);
					}
				}
				myself.notifyDataSetChanged();	
			}
		});

		return row;

	}

	static class InfoHolder {
		TextView display;
		String info;
		Button paid;
		Button reminder;
	}

	@Override
	public void remove(String object) {
		// TODO Auto-generated method stub
		super.remove(object);
	}
}