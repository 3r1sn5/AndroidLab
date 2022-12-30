package com.example.kotlinapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity()
{
    private lateinit var btnQuery: Button
    class MyObject
    {
        lateinit var result: Result
        class Result
        {
            lateinit var records: Array<Record>
            class Record {
                var SiteName = ""
                var Status = ""
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnQuery = findViewById(R.id.btnQuery)
        btnQuery.setOnClickListener {
            btnQuery.isEnabled = false
            sendRequest()
        }
    }
    private fun sendRequest() {
        val url = "https://api.italkutalk.com/api/air"

        val req = Request.Builder()
            .url(url)
            .build()
        OkHttpClient().newCall(req).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val myObject = Gson().fromJson(json, MyObject::class.java)
                showDialog(myObject)
            }
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    btnQuery.isEnabled = true
                    Toast.makeText(this@MainActivity, "Failed to check$e", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun showDialog(myObject: MyObject) {
        val items = arrayOfNulls<String>(myObject.result.records.size)
        myObject.result.records.forEachIndexed { index, data ->
            items[index] = "Area ：${data.SiteName}, Status ：${data.Status}"
        }
        runOnUiThread{
            btnQuery.isEnabled = true
            AlertDialog.Builder(this@MainActivity).setTitle("Taipei City Air Quality").setItems(items, null).show()
        }
    }
}