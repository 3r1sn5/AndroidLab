package com.example.lab7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private int progressHare = 0;
    private int progressTortoise = 0;

    private Button btnStart;
    private SeekBar sbHare, sbTortoise;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        sbHare = findViewById(R.id.sbHare);
        sbTortoise = findViewById(R.id.sbTortoise);

        btnStart.setOnClickListener(v ->
        {
                btnStart.setEnabled(false);
                progressHare = 0;
                progressTortoise = 0;
                sbHare.setProgress(0);
                sbTortoise.setProgress(0);
                runHare();
                runTortoise();

        });
    }

    private final Handler handler = new Handler(Looper.myLooper(), new Handler.Callback()
    {
        @Override
        public boolean handleMessage(@NonNull Message msg)
        {
            Log.e("Hare", String.valueOf(progressHare));
            Log.e("Tortoise", String.valueOf(progressTortoise));

            if (msg.what == 1)
                sbHare.setProgress(progressHare);
            else if (msg.what == 2)
                sbTortoise.setProgress(progressTortoise);

            if(progressHare >= 100 && progressTortoise < 100)
            {
                Toast.makeText(MainActivity.this, "The Hare wins", Toast.LENGTH_SHORT).show();
                btnStart.setEnabled(true);
            }
            else if (progressTortoise >= 100 &&progressHare < 100)
            {
                Toast.makeText(MainActivity.this, "The tortoise wins", Toast.LENGTH_SHORT).show();
                btnStart.setEnabled(true);
            }

            return false;
        }
    });

    private void runHare()
    {
        new Thread(() ->
        {
            Boolean[] sleepProb = {true, true, false};
            while (progressHare <= 100 && progressTortoise < 100)
            {
                try
                {
                    Thread.sleep(100);
                    if(sleepProb[(int) (Math.random() * 3)])
                        Thread.sleep(300);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                progressHare += 3;
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void runTortoise()
    {
        new Thread(() ->
        {
            while (progressTortoise <= 100 && progressHare < 100)
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                progressTortoise += 1;

                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
