package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

class QuakeAdapter extends ArrayAdapter<Earthquake> {
    QuakeAdapter(Context context, List<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }
    public View getView(int pos, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Earthquake currEarthquake = getItem(pos);

        TextView magView = (TextView) listItemView.findViewById(R.id.quake_mag);
        DecimalFormat formatter = new DecimalFormat("0.0");
        String mag = formatter.format(currEarthquake.getMag());
        magView.setText(mag);

        TextView locOffsetView = (TextView) listItemView.findViewById(R.id.quake_loc_offset);
        TextView locPrimaryView = (TextView) listItemView.findViewById(R.id.quake_loc_primary);
        String loc = currEarthquake.getLocation();
        String locOffset, locPrimary;
        if(loc.contains(" of ")){
            locOffset = loc.split(" of ")[0].concat(" of");
            System.out.println(locOffset);
            locPrimary = loc.split(" of ")[1];
            System.out.println(locPrimary);
        } else {
            locOffset = "Near the";
            locPrimary = loc;
        }
        locOffsetView.setText(locOffset);
        locPrimaryView.setText(locPrimary);

        Date date = new Date(currEarthquake.getTimeInMillis());

        String formattedDate = formatDate(date);
        TextView dateView = (TextView) listItemView.findViewById(R.id.quack_date);
        dateView.setText(formattedDate);


        String formattedTime = formatTime(date);
        TextView timeView = (TextView) listItemView.findViewById(R.id.quack_time);
        timeView.setText(formattedTime);

        GradientDrawable magCircle = (GradientDrawable) magView.getBackground();
        int magColor = getMagnitudeColor(currEarthquake.getMag());
        magCircle.setColor(magColor);

        return listItemView;
    }

    private int getMagnitudeColor(double mag) {
        int magColorId;
        int magFloor = (int) Math.floor(mag);
        switch(magFloor){
            case 0:
            case 1:
                magColorId = R.color.magnitude1;
                break;
            case 2:
                magColorId = R.color.magnitude2;
                break;
            case 3:
                magColorId = R.color.magnitude3;
                break;
            case 4:
                magColorId = R.color.magnitude4;
                break;
            case 5:
                magColorId = R.color.magnitude5;
                break;
            case 6:
                magColorId = R.color.magnitude6;
                break;
            case 7:
                magColorId = R.color.magnitude7;
                break;
            case 8:
                magColorId = R.color.magnitude8;
                break;
            case 9:
                magColorId = R.color.magnitude9;
                break;
            default:
                magColorId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magColorId);

    }

    private String formatTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(date);
    }

    private String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MMM DD, yyyy");
        return format.format(date);
    }



}
