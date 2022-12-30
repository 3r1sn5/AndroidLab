package com.example.kotlincontentprovidersub

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private val uri = Uri.parse("content://com.example.kotlincontentprovidersub")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        findViewById<ListView>(R.id.listView).adapter = adapter
        setListener()
    }

    private fun setListener()
    {
        val edBook = findViewById<EditText>(R.id.edBook)
        val edPrice = findViewById<EditText>(R.id.edPrice)
        findViewById<Button>(R.id.btnInsert).setOnClickListener{
            val name = edBook.text.toString()
            val price = edPrice.text.toString()
            if (name.isEmpty() || price.isEmpty())
                showToast("Title/Price can't be empty!")
            else {
                val values = ContentValues()
                values.put("book", name)
                values.put("price", price)
                val contentUri = contentResolver.insert(uri, values)
                if (contentUri != null) {
                    showToast("Add $name, Price:$price")
                    cleanEditText()
                } else
                    showToast("Failed to add")
            }
        }
        findViewById<Button>(R.id.btnUpdate).setOnClickListener{
            val name = edBook.text.toString()
            val price = edPrice.text.toString()
            if (name.isEmpty() || price.isEmpty())
                showToast("Title/Price can't be empty")
            else {
                val values = ContentValues()
                values.put("price", price)
                val count = contentResolver.update(uri, values, name, null)
                if (count > 0)
                {
                    showToast("Update$name, Price:$price")
                    cleanEditText()
                }
                else
                    showToast("Failed to update")
            }
        }
        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            val name = edBook.text.toString()
            if (name.isEmpty())
                showToast("Title can't be empty")
            else
            {
                val count = contentResolver.delete(uri, name, null)
                if (count > 0)
                {
                    showToast("Delete:${name}")
                    cleanEditText()
                }
                else
                    showToast("Failed to delete")
            }
        }
        findViewById<Button>(R.id.btnQuery).setOnClickListener {
            val name = edBook.text.toString()
            val selection = if (name.isEmpty()) null else name
            val c = contentResolver.query(uri, null, selection, null, null)
            c ?: return@setOnClickListener
            c.moveToFirst()
            items.clear()
            showToast("${c.count} results")
            for (i in 0 until c.count) {
                items.add("Title:${c.getString(0)}\t\t\t\t Price:${c.getInt(1)}")
                c.moveToNext()
            }
            adapter.notifyDataSetChanged()
            c.close()
        }
    }
    private fun showToast(text: String) =
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()
    private fun cleanEditText() {
        findViewById<EditText>(R.id.edBook).setText("")
        findViewById<EditText>(R.id.edPrice).setText("")
    }
}