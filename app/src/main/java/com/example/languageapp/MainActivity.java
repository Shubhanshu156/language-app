package com.example.languageapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Struct;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //numberclicklistner onclicklistner=;
        TextView findnumber=(TextView) findViewById(R.id.numbers);


        findnumber.setOnClickListener(new View.OnClickListener(){
            @Override

                public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,numberactivity.class);
                startActivity(i);
                    }
        });




    }
    public void openfamily(View view){
        Intent i=new Intent(this,family.class);
        startActivity(i);

    }
    public void opennumber(View view){
        Intent i=new Intent(this,numberactivity.class);
        startActivity(i);

    }
    public void opencolor(View view){
        Intent i=new Intent(this,colors.class);
        startActivity(i);

    }
    public void openphrases(View view){
        Intent i=new Intent(this,phrases.class);
        startActivity(i);

    }
}