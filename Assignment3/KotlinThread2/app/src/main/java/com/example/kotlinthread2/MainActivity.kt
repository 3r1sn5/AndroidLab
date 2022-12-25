package com.example.kotlinthread2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    //建立變數以利後續綁定元件
    private lateinit var btnCalculate: Button
    private lateinit var edHeight: EditText
    private lateinit var edWeight: EditText
    private lateinit var edAge: EditText
    private lateinit var tvWeight: TextView
    private lateinit var tvFat: TextView
    private lateinit var tvBmi: TextView
    private lateinit var tvProgress: TextView
    private lateinit var progressBar2: ProgressBar
    private lateinit var llProgress: LinearLayout
    private lateinit var btnMale: RadioButton
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCalculate = findViewById(R.id.btnCalculate)
        edHeight = findViewById(R.id.edHeight)
        edWeight = findViewById(R.id.edWeight)
        edAge = findViewById(R.id.edAge)
        tvWeight = findViewById(R.id.tvWeight)
        tvFat = findViewById(R.id.tvFat)
        tvBmi = findViewById(R.id.tvBmi)
        tvProgress = findViewById(R.id.tvProgress)
        progressBar2 = findViewById(R.id.progressBar2)
        llProgress = findViewById(R.id.llProgress)
        btnMale = findViewById(R.id.btnMale)
        btnCalculate.setOnClickListener{
            when
            {
                edHeight.length() < 1 -> showToast("Enter Height")
                edWeight.length() < 1 -> showToast("Enter Weight")
                edAge.length() < 1 -> showToast("Enter Age")
                else -> runCoroutines()
            }
        }
    }
    private fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    private fun runCoroutines() {
        tvWeight.text = "Ideal Weight\n None"
        tvFat.text = "Body Fat\n None"
        tvBmi.text = "BMI\n None"
        //初始化進度條
        progressBar2.progress = 0
        tvProgress.text = "0%"
        //顯示進度條
        llProgress.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Main)
        {
            var progress = 0
            while (progress < 100)
            {
                delay(50)
                progressBar2.progress = progress
                tvProgress.text = "$progress%"
                progress++
            }
            llProgress.visibility = View.GONE
            val height = edHeight.text.toString().toDouble()
            val weight = edWeight.text.toString().toDouble()
            val age = edAge.text.toString().toDouble()
            val bmi = weight / ((height / 100).pow(2))
            val (stand_weight, body_fat) = if (btnMale.isChecked) {
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }
            tvWeight.text = "Ideal Weight \n${String.format("%.2f", stand_weight)}"
            tvFat.text = "Body Fat \n${String.format("%.2f", body_fat)}"
            tvBmi.text = "BMI \n${String.format("%.2f", bmi)}"
        }
    }
}