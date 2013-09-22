package com.ampvita.paybaq;

import java.util.List;

import android.app.Activity;
import android.content.Context;
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

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		InfoHolder holder = null;
		final String activity = data.get(position);

		if (row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new InfoHolder();
			holder.display = (TextView) row.findViewById(R.id.textInfo);
			holder.paid = (Button) row.findViewById(R.id.buttonPaid);
			holder.reminder = (Button) row.findViewById(R.id.buttonReminder);

			row.setTag(holder);
		} else {
			holder = (InfoHolder)row.getTag();
		}

		holder.info = data.get(position);
		String[] parts = holder.info.split("\\t");
		String name = parts[0];
		String reason = parts[2];
		String amount = parts[3];
		String tier = parts[4];
		if (tier.equals("9")) {
			holder.display.setText(name + " owes $" + amount + " for " + reason + " (reminded too many times).");
		} else {
			holder.display.setText(name + " owes $" + amount + " for " + reason + " (reminded " + tier + " times).");	
		}
		holder.paid.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				data.remove(activity);
				myself.notifyDataSetChanged();
			}
		});

		holder.reminder.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String[] parts = activity.split("\\t");
				//data.remove(activity);
				int index = data.indexOf(activity);
				data.remove(activity);

				// Name, Number, Reason, Amount, Tier
				if (parts.length == 5) {
					int k = Integer.parseInt(parts[4]) + 1; // increase Tier
					if (k > 9) {
						k = 9;
					}
					data.add(index, parts[0] + "\t" + parts[1] + "\t" + parts[2] + "\t" + parts[3] + "\t" + k);
					int val = (int) (Math.random()*Integer.parseInt(StartActivity.tiers[k-1][0]));
					String msg = StartActivity.tiers[k-1][val+1];
					msg = msg.replace("[reason]", parts[2]);
					msg = msg.replace("[price]", parts[3]);
					new SendSMS().execute(parts[1], msg);
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
}