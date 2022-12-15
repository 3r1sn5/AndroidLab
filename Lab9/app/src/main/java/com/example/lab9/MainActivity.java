package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private TextView tvClock;
    private Button btnStart;
    private Boolean flag = false;
    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle b = intent.getExtras();
            tvClock.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", b.getInt("H"), b.getInt("M"), b.getInt("S")));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvClock = findViewById(R.id.tvClock);
        btnStart = findViewById(R.id.btnStart);

        registerReceiver(receiver, new IntentFilter("MyMessage"));

        flag = MyService.flag;

        if(flag)
        {
            btnStart.setText("Pause");
        }
        else
        {
            btnStart.setText("Start");
        }

        btnStart.setOnClickListener(v ->
        {
            flag = !flag;

            if(flag)
            {
                btnStart.setText("Pause");
                Toast.makeText(this, "Timing Start", Toast.LENGTH_SHORT).show();
            }
            else
            {
                btnStart.setText("Start");
                Toast.makeText(this, "Timing Pause", Toast.LENGTH_SHORT).show();
            }

            startService(new Intent(this, MyService.class).putExtra("flag", flag));
        });
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}