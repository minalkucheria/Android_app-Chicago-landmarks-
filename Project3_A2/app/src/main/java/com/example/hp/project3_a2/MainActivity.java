package com.example.hp.project3_a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private MyBroadcastReceiver mBroadcastReceiver;
    GridView gridview;
    protected static final String identifier1="A";

    //Array of reference to images in drawable
    public static int[] ThumbId = {
            R.drawable.willis_tower,R.drawable.millenium_park,R.drawable.cloud_gate,
            R.drawable.shedd_aquarium ,R.drawable.wrigley_field,R.drawable.museum};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridview = (GridView) findViewById(R.id.gridview);  //Retrieve the widgets

        gridview.setAdapter(new ImageAdapter(this, ThumbId));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //Create a new intent calling activity to call activity view_image
                Intent i = new Intent(getApplicationContext(),view_image.class);
                // Pass image index
                i.putExtra(identifier1, ThumbId[position]);
                //Starting activity view_image
                startActivity(i);
            }
        });
    }

    //Broadcast receiver
    public static class MyBroadcastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            //Start desired activity
            Intent i=new Intent();
            i.setClassName("com.example.hp.project3_a2","com.example.hp.project3_a2.MainActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }

    }


}
