package com.next.simply.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.next.simply.R;

/**
 * Created by manfredi on 21/08/15.
 */
public class SearchAdapter extends BaseAdapter {
    private Context mContext;
    private String mName;
    private String mNumber;

    public SearchAdapter(Context context, String name, String number) {
        mContext = context;
        mName = name;
        mNumber = number;
    }

    @Override
    public int getCount() {
        return 0;
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_list_item, null);
            holder = new ViewHolder();
            holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
            holder.callButton = (ImageView) convertView.findViewById(R.id.callButton);
            holder.messageButton = (ImageView) convertView.findViewById(R.id.messageButton);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameLabel.setText(mName);

        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dial = "tel:" + mNumber;
                Uri telUri = Uri.parse(dial);
                Intent intent = new Intent(Intent.ACTION_DIAL, telUri);
                mContext.startActivity(intent);
            }
        });

        holder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dial = "sms:" + mNumber;
                Uri telUri = Uri.parse(dial);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("vnd.android-dir/mms-sms");
                intent.setData(telUri);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView nameLabel;
        ImageView callButton;
        ImageView messageButton;
    }
}


