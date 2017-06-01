package com.applicationtest.vbr.designtest4.com.vbr.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.applicationtest.vbr.designtest4.R;

/**
 * Created by C5245675 on 4/18/2017.
 */
public class CustomAdapterViewFlipper extends BaseAdapter {
    Context context;
    int[] fruitImages;
    String[] fruitNames;
    LayoutInflater inflter;

    public CustomAdapterViewFlipper(Context applicationContext, String[] fruitNames, int[] fruitImages) {
        this.context = applicationContext;
        this.fruitImages = fruitImages;
        this.fruitNames = fruitNames;
        inflter = (LayoutInflater.from(applicationContext));

    }

    @Override
    public int getCount() {
        return fruitNames.length;
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
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.list_item_viewflipper, null);
        TextView fruitName = (TextView) view.findViewById(R.id.fruitName);
        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruitImage);
        fruitName.setText(fruitNames[position]);
        fruitImage.setImageResource(fruitImages[position]);
        return view;
    }
}



