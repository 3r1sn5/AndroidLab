package com.example.kotlinsql

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity()
{
    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbrw = MyDBHelper(this).writableDatabase
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        findViewById<ListView>(R.id.listView).adapter = adapter
        setListener()
    }

    override fun onDestroy()
    {
        dbrw.close()
        super.onDestroy()
    }

    private fun setListener() {
        val edBook = findViewById<EditText>(R.id.edBook)
        val edPrice = findViewById<EditText>(R.id.edPrice)
        findViewById<Button>(R.id.btnInsert).setOnClickListener {
            if (edBook.length() < 1 || edPrice.length() < 1)
                showToast("Title/Price can't be empty")
            else
                try
                {
                    dbrw.execSQL("INSERT INTO myTable(book, price) VALUES(?,?)", arrayOf(edBook.text.toString(), edPrice.text.toString()))
                    showToast("Add ${edBook.text}, Price : ${edPrice.text}")
                    cleanEditText()
                }
                catch (e: Exception)
                {
                    showToast("Failed to add : $e")
                }
        }
        findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            if (edBook.length() < 1 || edPrice.length() < 1)
                showToast("Title/Price can't be empty")
            else
                try
                {
                    dbrw.execSQL("UPDATE myTable SET price = ${edPrice.text} WHERE book LIKE '${edBook.text}'")
                    showToast("Update ${edBook.text}, Price : ${edPrice.text}")
                    cleanEditText()
                }
                catch (e: Exception)
                {
                    showToast("Failed to Update : $e")
                }
        }
        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            if (edBook.length() < 1)
                showToast("Title can't be empty")
            else
                try
                {
                    dbrw.execSQL("DELETE FROM myTable WHERE book LIKE '${edBook.text}'")
                    showToast("Delete ${edBook.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast("Failed to delete : $e")
                }
        }
        findViewById<Button>(R.id.btnQuery).setOnClickListener {
            val queryString = if (edBook.length() < 1)
                "SELECT * FROM myTable"
            else
                "SELECT * FROM myTable WHERE book LIKE '${edBook.text}'"
            val c = dbrw.rawQuery(queryString, null)
            c.moveToFirst()
            items.clear()
            showToast("${c.count} results")
            for (i in 0 until c.count) {
                items.add("Title : ${c.getString(0)}\t\t\t\t Price : ${c.getInt(1)}")
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