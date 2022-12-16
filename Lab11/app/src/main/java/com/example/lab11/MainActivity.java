package com.example.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private EditText edBook, edPrice;
    private Button btnSearch, btnAdd, btnUpdate, btnDelete;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private SQLiteDatabase dbrw;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edBook = findViewById(R.id.edBook);
        edPrice = findViewById(R.id.edPrice);
        btnSearch = findViewById(R.id.btnSearch);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        dbrw = new MyDBHelper(this).getWritableDatabase();

        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Cursor c;
                if(edBook.length() < 1)
                {
                    c = dbrw.rawQuery("SELECT * FROM myTable", null);
                }
                else
                {
                    c = dbrw.rawQuery("SELECT * FROM myTable WHERE book LIKE '" + edBook.getText().toString() + "'", null);
                }

                c.moveToFirst();
                items.clear();
                Toast.makeText(MainActivity.this, c.getCount() + " results", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < c.getCount(); i++)
                {
                    items.add("Title : " + c.getString(0) + "\t\t\t\tPrice : " + c.getString(1));
                    c.moveToNext();
                }

                adapter.notifyDataSetChanged();
                c.close();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(edBook.length() < 1 || edPrice.length() < 1)
                {
                    Toast.makeText(MainActivity.this, "Title/Price can't be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {
                        dbrw.execSQL("INSERT INTO myTable(book, price) VALUES(?, ?)", new Object[]{edBook.getText().toString(), edPrice.getText().toString()});
                        Toast.makeText(MainActivity.this, "Add Title " + edBook.getText().toString() + "  Price " + edPrice.getText().toString(), Toast.LENGTH_SHORT).show();
                        edBook.setText("");
                        edPrice.setText("");
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, "Failed to add : " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(edBook.length() < 1 || edPrice.length() < 1)
                {
                    Toast.makeText(MainActivity.this, "Title/Price can't be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {
                        dbrw.execSQL("UPDATE myTable SET price = " + edPrice.getText().toString() + " WHERE book LIKE '" + edBook.getText().toString() + "'");
                        Toast.makeText(MainActivity.this, "Update Title " + edBook.getText().toString() + "  Price " + edPrice.getText().toString(), Toast.LENGTH_SHORT).show();
                        edBook.setText("");
                        edPrice.setText("");
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, "Failed to Update : " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(edBook.length() < 1)
                {
                    Toast.makeText(MainActivity.this, "Title can't be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {
                        dbrw.execSQL("DELETE FROM myTable WHERE book LIKE '" + edBook.getText().toString() + "'");
                        Toast.makeText(MainActivity.this, "Delete Title " + edBook.getText().toString(), Toast.LENGTH_SHORT).show();
                        edBook.setText("");
                        edPrice.setText("");
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, "Failed to delete : " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        dbrw.close();
    }
}

