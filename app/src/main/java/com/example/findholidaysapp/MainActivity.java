package com.example.findholidaysapp;

//import com.example.findholidaysapp.model.ListHolidaysInfo.listHolidaysInfo;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.util.Output;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findholidaysapp.model.ListHolidaysInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetDataFromInternet.AsyncResponse, MyAdapter.ListItemClickListener  {


    private static final String TAG = "MainActivity";
    private Toast toast;
    private ListHolidaysInfo listHolidaysInfo;


    private TextView listOfHolidays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

        URL url=new URL("https://calendarific.com/api/v2/holidays?api_key=340098152bac11f7447dbf55726ee364ea07977a&country=RU&year=2023");

            new GetDataFromInternet(this).execute(url);

        } catch (MalformedURLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void processFinish(String output) {
        Log.d(TAG, "processFinish: "+output);

        try {
            JSONObject outputJSON = new JSONObject(output);
            JSONObject responseJSON=outputJSON.getJSONObject("response");
            JSONArray array = responseJSON.getJSONArray("holidays");
            int length=array.length();

            listHolidaysInfo=new ListHolidaysInfo(length);

            ArrayList <String> namesHolidays=new ArrayList<String>();

            for(int i=0; i<length; i++) {
                JSONObject obj=array.getJSONObject(i);
                String name=obj.getString("name");


                JSONObject obj_date=obj.getJSONObject("date");
                String data_iso=obj_date.getString("iso");

                namesHolidays.add(name);
                Log.d(TAG, "processFinish: "+name+" "+data_iso);
                listHolidaysInfo.addHoliday(name, data_iso, i);
            }


            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new MyAdapter(listHolidaysInfo, length, this));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        CharSequence text  = listHolidaysInfo.listHolidaysInfo[clickedItemIndex].getHoliday_name();
        int duration = Toast.LENGTH_SHORT;
        if(toast!= null){
            toast.cancel();
        }
        toast = Toast.makeText(this,text,duration);
        toast.show();
    }
}