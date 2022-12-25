package com.example.kotlinthread1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity()
{
    private var progressRabbit = 0
    private var progressTurtle = 0
    private lateinit var btnStart: Button
    private lateinit var sbHare: SeekBar
    private lateinit var sbTortoise: SeekBar
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart = findViewById(R.id.btnStart)
        sbHare = findViewById(R.id.sbHare)
        sbTortoise = findViewById(R.id.sbTortoise)
        btnStart.setOnClickListener {
            btnStart.isEnabled = false
            progressRabbit = 0
            progressTurtle = 0
            sbHare.progress = 0
            sbTortoise.progress = 0
            runRabbit()
            runTurtle()
        }
    }
    private fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    private val handler = Handler(Looper.getMainLooper()) { msg ->
        if (msg.what == 1)
            sbHare.progress = progressRabbit
        if (progressRabbit >= 100 && progressTurtle < 100) {
            showToast("The hare wins!")
            btnStart.isEnabled = true
        }
        true
    }
    private fun runRabbit() {
        Thread {
            val sleepProbability = arrayOf(true, true, false)
            while (progressRabbit < 100 && progressTurtle < 100) {
                try
                {
                    Thread.sleep(100)
                    if (sleepProbability.random())
                        Thread.sleep(300)
                }
                catch (e: InterruptedException)
                {
                    e.printStackTrace()
                }
                progressRabbit += 3
                val msg = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }
    private fun runTurtle() {
        GlobalScope.launch(Dispatchers.Main)
        {
            while (progressTurtle < 100 && progressRabbit < 100)
            {
                delay(100)
                progressTurtle += 1
                sbTortoise.progress = progressTurtle
                if (progressTurtle >= 100 && progressRabbit < 100)
                {
                    showToast("The tortoise wins!")
                    btnStart.isEnabled = true
                }
            }
        }
    }
}