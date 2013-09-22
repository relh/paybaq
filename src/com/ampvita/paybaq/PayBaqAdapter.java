package com.ampvita.paybaq;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PayBaqAdapter extends ArrayAdapter<String> {
   Context context;
   int layoutResourceId;
   List<String> data;
   
   public PayBaqAdapter(Context context, int layoutResourceId, List<String> payBaqArray) {
       super(context, layoutResourceId, payBaqArray);
       this.layoutResourceId = layoutResourceId;
       this.context = context;
       this.data = payBaqArray;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
       View row = convertView;
       InfoHolder holder = null;

       if(row == null)
       {
           LayoutInflater inflater = ((Activity)context).getLayoutInflater();
           row = inflater.inflate(layoutResourceId, parent, false);

           holder = new InfoHolder();
           holder.nameAmt = (TextView)row.findViewById(R.id.textInfo);

           row.setTag(holder);
       }
       else
       {
           holder = (InfoHolder)row.getTag();
       }
       
       holder.nameAmt.setText(data.get(position));

      return row;

   }

   static class InfoHolder
   {
      // ImageView image;
       TextView nameAmt;
   }
}