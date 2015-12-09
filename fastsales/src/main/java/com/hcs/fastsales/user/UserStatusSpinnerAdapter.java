package com.hcs.fastsales.user;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hcs.fastsales.dto.user.DtoUserStatus;

import java.util.List;

/**
 * Created by fernando.ramirez on 24/06/2015.
 */
public class UserStatusSpinnerAdapter extends ArrayAdapter<DtoUserStatus> {

    private Context mContext;
    private List<DtoUserStatus> mValues;

    public UserStatusSpinnerAdapter(Context context, int textViewResourceId, List<DtoUserStatus> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mValues = objects;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public DtoUserStatus getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // This is for the first item before dropdown or default state.
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setTextSize(18);
        label.setText(mValues.get(position).getStatusName());
        label.setHeight(50);
        label.setMaxLines(1);
        label.setGravity(Gravity.LEFT | Gravity.CENTER);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // This is when user click the spinner and list of item display
        // beneath it
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setTextSize(15);
        label.setText(mValues.get(position).getStatusName() + "\n" + mValues.get(position).getDescription());
        label.setHeight(80);
        label.setMaxLines(2);
        label.setGravity(Gravity.LEFT | Gravity.CENTER);
        return label;
    }
}