package com.example.kotlintoast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnToast = findViewById<Button>(R.id.btnToast)
        val btnCustom = findViewById<Button>(R.id.btnCustom)
        val btnSnackbar = findViewById<Button>(R.id.btnSnackbar)
        val btnDialog1 = findViewById<Button>(R.id.btnDialog1)
        val btnDialog2 = findViewById<Button>(R.id.btnDialog2)
        val btnDialog3 = findViewById<Button>(R.id.btnDialog3)

        val item = arrayOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")

        btnToast.setOnClickListener {
            showToast("Default Toast") //執行 showToast 方法
        }

        btnCustom.setOnClickListener {
            val toast = Toast(this)
            toast.setGravity(Gravity.TOP, 0, 50)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layoutInflater.inflate(R.layout.custom_toast, null)
            toast.show()
        }

        btnSnackbar.setOnClickListener {
            Snackbar.make(it, "Snackbar", Snackbar.LENGTH_SHORT).setAction("Button")
                {
                    showToast("Responded")
                }.show()
        }

        btnDialog1.setOnClickListener {
            //建立 AlertDialog 物件
            AlertDialog.Builder(this)
                .setTitle("Button AlertDialog")
                .setMessage("AlertDialog content")
                .setNeutralButton("Left")
                {
                        dialog, which -> showToast("Left")
                }
                .setNegativeButton("Middle")
                {
                        dialog, which -> showToast("Middle")
                }
                .setPositiveButton("Right")
                {
                        dialog, which -> showToast("Right")
                }.show()
        }

        btnDialog2.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("List AlertDialog")
                .setItems(item)
                {
                        dialogInterface, i -> showToast("You chose ${item[i]}")
                }.show()
        }

        btnDialog3.setOnClickListener {
            var position = 0
            //建立 AlertDialog 物件
            AlertDialog.Builder(this)
                .setTitle("MultiChoice AlertDialog")
                .setSingleChoiceItems(item, 0)
                {
                        dialogInterface, i -> position = i
                }
                .setPositiveButton("Confirm")
                {
                        dialog, which -> showToast("You chose ${item[position]}")
                }.show()
        }
    }
    private fun showToast(msg: String)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}