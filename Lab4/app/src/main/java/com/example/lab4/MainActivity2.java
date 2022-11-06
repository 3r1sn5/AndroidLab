package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity2 extends AppCompatActivity
{
    private Button sendBtn;
    private EditText setDrink;
    private RadioGroup rg1, rg2;
    private String sugar = "無糖";
    private String iceOpt = "去冰";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Initial
        super.onCreate(savedInstanceState);
        //Connect to XML
        setContentView(R.layout.activity_main2);

        rg1 = findViewById(R.id.radioGroup);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case R.id.radioButton1:
                        sugar = "無糖";
                        break;
                    case R.id.radioButton2:
                        sugar = "少糖";
                        break;
                    case R.id.radioButton3:
                        sugar = "半糖";
                        break;
                    case R.id.radioButton4:
                        sugar = "全糖";
                        break;
                }
            }
        });

        rg2 = findViewById(R.id.radioGroup2);
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case R.id.radioButton5:
                        iceOpt = "去冰";
                        break;
                    case R.id.radioButton6:
                        iceOpt = "微冰";
                        break;
                    case R.id.radioButton7:
                        iceOpt = "少冰";
                        break;
                    case R.id.radioButton8:
                        iceOpt = "正常冰";
                        break;
                }
            }
        });

        sendBtn = findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setDrink = findViewById(R.id.edDrink);
                String drink = setDrink.getText().toString();
                Intent i  = new Intent();
                //Put Sugar and Ice into bundle
                Bundle b = new Bundle();
                b.putString("Sugar", sugar);
                b.putString("Drink", drink);
                b.putString("Ice", iceOpt);
                i.putExtras(b);
                //101 mark states. Record intent
                setResult(101, i);
                finish();
            }
        });
    }
}