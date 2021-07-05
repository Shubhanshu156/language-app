package com.example.languageapp;
import android.util.Log;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.Currency;

public class wordadapter extends ArrayAdapter<WORDS> {
    private int mcolorid;


    public wordadapter(@NonNull Context context, ArrayList<WORDS> words,int colorid) {
        super(context, 0, words);
        mcolorid=colorid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_view, parent, false);
        }
        WORDS CURRENT = getItem(position);
        TextView meowk = (TextView) listItemView.findViewById(R.id.mewok);
        meowk.setText(CURRENT.getMeowkword());
        TextView english = (TextView) listItemView.findViewById(R.id.english);
        english.setText(CURRENT.getEnglishword());
        ImageView im = (ImageView) listItemView.findViewById(R.id.image);
        if (CURRENT.hasimage()) {

            im.setImageResource(CURRENT.getimageid());
            im.setVisibility(View.VISIBLE);
        }
        else{
            im.setVisibility(View.GONE);

        }
        //log.i(CURRENT.getEnglishword());
       // log.i(CURRENT.getMeowkword())
        //String ms= CURRENT.getMeowkword();

        //Log.d("hello",ms);
        View textcontainer=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mcolorid);
       textcontainer.setBackgroundColor(color);
        return listItemView;

    }
}
