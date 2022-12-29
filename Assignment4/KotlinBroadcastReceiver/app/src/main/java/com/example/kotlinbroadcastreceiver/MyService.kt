package com.example.kotlinbroadcastreceiver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MyService : Service()
{
    private var channel = ""
    private lateinit var thread: Thread
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
        intent?.extras?.let {
            channel = it.getString("channel", "")
        }
        broadcast(
            when(channel)
            {
                "music" -> "Welcome to music channel"
                "new" -> "Welcome to news channel"
                "sport" -> "Welcome to sport channel"
                else -> "Wrong channel"
            }
        )

        if (::thread.isInitialized && thread.isAlive)
            thread.interrupt()
        thread = Thread{
            try
            {
                Thread.sleep(3000) //延遲三秒
                broadcast(
                    when(channel)
                    {
                        "music" -> "Top 10 song of the month"
                        "new" -> "Exclusive news"
                        "sport" -> "NBA game of the week"
                        else -> "Wrong channel"
                    }
                )
            }
            catch (e: InterruptedException)
            {
                e.printStackTrace()
            }
        }
        thread.start()
        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder? = null
    private fun broadcast(msg: String) =
        sendBroadcast(Intent(channel).putExtra("msg", msg))
}