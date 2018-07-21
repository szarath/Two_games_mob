package com.example.szara_000.team02_mobile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by szara on 9/28/2016.
 */
public class customgrid extends BaseAdapter {

    private Context mContext;
    private final String[] text;
    private final Drawable[] image;
    private final int[] price;
    private final String[] location;
    private final String[] platform;
    private final String[] premium;
    public customgrid(Context context, String[] text, Drawable[] image, int[] price,String[] platform,String[] location, String[] premium )
    {
        mContext = context;
        this.image = image;
        this.text = text;
        this.price = price;
        this.platform = platform;
        this.location = location;
        this.premium = premium;
    }

    @Override
    public int getCount() {
        return price.length;
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
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String star= "★";
        if (convertView == null) {

            grid = new View(mContext);



        } else {
            grid = (View) convertView;
        }
        grid = inflater.inflate(R.layout.gridimagetext, null);
        TextView textView = (TextView) grid.findViewById(R.id.gridtext);
        ImageView imageView = (ImageView)grid.findViewById(R.id.gridimage);
        TextView priceview = (TextView) grid.findViewById(R.id.gridprice);
        TextView platview = (TextView) grid.findViewById(R.id.gridplat);
        TextView locview = (TextView) grid.findViewById(R.id.gridloc);
        if(premium[position].toString().contains("True"))
        {
            textView.setText(text[position] + " " + star);
        }
        else{
            textView.setText(text[position]);
        }

        imageView.setImageDrawable(image[position]);
        platview.setText("Platform: " + platform[position]);
        locview.setText("Location: " + location[position]);
        priceview.setText("Price: R" +price[position]);

        return grid;
    }
}
