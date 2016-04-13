package com.example.jack.weather;

/**
 * Created by Jack on 4/13/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    List<Weather> weathList;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, List<Weather> wList) {
        // TODO Auto-generated constructor stub
        //result=prgmNameList;
        context=mainActivity;
        weathList=wList;
        //imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int i = 0;
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
        TextView tv;
        ImageView img;
        private Button submit;
        private EditText cityText;
        private TextView condDescr;
        private TextView temp;
        private TextView press;
        private TextView windSpeed;
        private TextView windDeg;

        private TextView tempMax;
        private TextView tempMin;

        private TextView hum, dateT,day;
        private ImageView imgView;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.activity_main2, null);

        holder.cityText = (EditText) rowView.findViewById(R.id.cityText);
        holder.condDescr = (TextView) rowView.findViewById(R.id.condDescr);
        //holder.temp = (TextView) rowView.findViewById(R.id.temp);
        holder.hum = (TextView) rowView.findViewById(R.id.hum);
        holder.press = (TextView) rowView.findViewById(R.id.press);
        holder.windSpeed = (TextView) rowView.findViewById(R.id.windSpeed);
        holder.windDeg = (TextView) rowView.findViewById(R.id.windDeg);
        holder.imgView = (ImageView) rowView.findViewById(R.id.condIcon);
        holder.submit = (Button) rowView.findViewById(R.id.button);
        holder.dateT = (TextView) rowView.findViewById(R.id.date);
        holder.day = (TextView) rowView.findViewById(R.id.day);
        holder.tempMax = (TextView) rowView.findViewById(R.id.tempMax);
        holder.tempMin = (TextView) rowView.findViewById(R.id.tempMin);

        // TODO TESTING
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

        holder.tempMax.setText("" + weather.temperature.getMaxTemp());
        holder.tempMin.setText("" + weather.temperature.getMinTemp());


        if (weather.iconData != null && weather.iconData.getByteCount() > 0) {
            holder.imgView.setImageBitmap(weather.iconData);
        }
        holder.condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
        //holder.temp.setText("" + Math.round((weather.temperature.getTemp())) + "°F");
        holder.hum.setText("" + weather.currentCondition.getHumidity() + "%");
        holder.press.setText("" + weather.currentCondition.getPressure() + " hPa");
        holder.windSpeed.setText("" + weather.wind.getSpeed() + " mph");
        holder.windDeg.setText("" + weather.wind.getDeg() + "\u00b0");


        return rowView;
    }

}
