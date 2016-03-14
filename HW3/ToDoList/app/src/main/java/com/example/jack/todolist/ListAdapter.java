package com.example.jack.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
//import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class ListAdapter extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    ToDo tempValues=null;

    /*************  CustomAdapter Constructor *****************/
    public ListAdapter(Activity a, ArrayList d,Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater)activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView title;
        public Button showDetail;
        public Button editDetail;
        public CheckBox cBox;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.to_do_layout, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.title = (TextView) vi.findViewById(R.id.layout_title);
            holder.editDetail = (Button) vi.findViewById(R.id.edit_detail);
            holder.showDetail = (Button) vi.findViewById(R.id.show_detail);
            holder.cBox = (CheckBox) vi.findViewById(R.id.checkBox);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.title.setText("Nothing To Do!");
            holder.editDetail.setVisibility(View.GONE);
            holder.showDetail.setVisibility(View.GONE);
            holder.cBox.setVisibility(View.GONE);
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( ToDo ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.title.setText(tempValues.getTitle());
//            holder.text1.setText( tempValues.getUrl() );
//            holder.image.setImageResource(
//                    res.getIdentifier(
//                            "com.example.jack.todolist:drawable/"+tempValues.getImage()
//                            ,null,null));


            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));
        }

        Button showButton = (Button) vi.findViewById(R.id.show_detail);
        Button editButton = (Button) vi.findViewById(R.id.edit_detail);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo current = (ToDo) data.get(position);
                //System.out.println("Showing: " + current.getTitle());
                Intent intent = new Intent(v.getContext(), ShowActivity.class);
                intent.putExtra("Title", current.getTitle());
                intent.putExtra("Description", current.getDescription());
                intent.putExtra("Date", current.getDate());
                v.getContext().startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo current = (ToDo) data.get(position);
                //System.out.println("Editing: " + current.getTitle());
                Intent intent = new Intent(v.getContext(), EditActivity.class);
                intent.putExtra("Title", current.getTitle());
                intent.putExtra("Description", current.getDescription());
                intent.putExtra("Date", current.getDate());
                int i = 0;
                for (ToDo toDo : (ArrayList<ToDo>) data) {
                    intent.putExtra("Data" + i, new ToDo(toDo.getTitle(), toDo.getDescription(), toDo.getDate()));
                    i++;
                }
                intent.putExtra("Position", position);
                v.getContext().startActivity(intent);
            }
        });

        holder.cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                System.out.println("Changed at position:" + position);
                if (isChecked) {
                    MainActivity.toDeleteArray.add(position);
//                    System.out.println("Added to main array");
                } else {
                    MainActivity.toDeleteArray.remove((Object)position);
//                    System.out.println("deleting from main array");
                }
//                System.out.println(MainActivity.toDeleteArray.toString());
            }
        });

        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            MainActivity sct = (MainActivity)activity;

//            System.out.println("Item click at pos: " + mPosition);

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            sct.onItemClick(mPosition);
        }
    }
}