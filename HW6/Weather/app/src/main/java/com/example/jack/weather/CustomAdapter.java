package com.example.jack.weather;

/**
 * Created by Jack on 4/13/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CustomAdapter extends BaseAdapter{
    Context context;
    static List<Weather> weathList;
    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, List<Weather> wList) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        weathList=wList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return weathList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return weathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        private TextView dateT,day;
        private ImageView imgView;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.activity_main2, null);

        holder.imgView = (ImageView) rowView.findViewById(R.id.condIcon);
        holder.dateT = (TextView) rowView.findViewById(R.id.date);
        holder.day = (TextView) rowView.findViewById(R.id.day);

        String[] Days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        long unixSeconds;
        Date date;
        SimpleDateFormat sdf;
        String formattedDate;
        sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        Calendar cal = Calendar.getInstance();

        Weather weather = weathList.get(position);

        unixSeconds = weather.currentCondition.getDate();
        date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        formattedDate = sdf.format(date);
        cal.setTimeInMillis(unixSeconds * 1000L);
        holder.day.setText(Days[cal.get(Calendar.DAY_OF_WEEK) - 1]);
        holder.dateT.setText(formattedDate);

        if (weather.iconData != null && weather.iconData.getByteCount() > 0) {
            holder.imgView.setImageBitmap(weather.iconData);
        }
        return rowView;
    }
}
