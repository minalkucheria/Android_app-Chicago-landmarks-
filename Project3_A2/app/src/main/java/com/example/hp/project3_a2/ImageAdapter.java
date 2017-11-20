package com.example.hp.project3_a2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by HP on 10/29/2017.
 */

public class ImageAdapter extends BaseAdapter{

    Context mContext;
    int[] Image_Id;
    public ImageAdapter(Context c,int[] ThumbId) {

        mContext=c;
        Image_Id=ThumbId;
    }

    public int getCount() {
        return Image_Id.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.view_gallery, null);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);




            //textView.setText(result[position]);
            imageView.setImageResource(Image_Id[position]); //Image is set in thumbnail
            //grid.setPadding(5,5,5,5);//defines the padding for all sides
        } else {
            grid = (View) convertView;
        }


        return grid;
    }
}
