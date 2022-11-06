package com.example.lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    //Declaration
    private Button btn;
    private TextView tvMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect to XML
        tvMeal = findViewById(R.id.tvMeal);
        btn = findViewById(R.id.btnChoice);

        //Listener
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivityForResult(new Intent(MainActivity.this, MainActivity2.class), 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) return;

        if(requestCode == 1)
        {
            if(resultCode == 101)
            {
                Bundle b = data.getExtras();
                String str1 = b.getString("Drink");
                String str2 = b.getString("Sugar");
                String str3 = b.getString("Ice");
                tvMeal.setText(String.format("飲料 ： %s\n\n甜度 ： %s\n\n冰塊 ： %s\n\n", str1, str2, str3));
            }
        }
    }
}