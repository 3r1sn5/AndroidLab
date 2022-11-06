package com.example.assignment1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declaration
        val edName = findViewById<EditText>(R.id.edName)
        val tvText = findViewById<TextView>(R.id.tvText)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvWinner = findViewById<TextView>(R.id.tvWinner)
        val tvMmora = findViewById<TextView>(R.id.tvMmora)
        val tvCmora = findViewById<TextView>(R.id.tvCmora)
        val btnScissor = findViewById<RadioButton>(R.id.btnScissor)
        val btnRock = findViewById<RadioButton>(R.id.btnRock)
        val btnPaper = findViewById<RadioButton>(R.id.btnPaper)
        val btnMora = findViewById<Button>(R.id.btnMora)

        btnMora.setOnClickListener(View.OnClickListener
        {
            //Check if name is input.
            if (edName.length() < 1)
            {
                tvText.text = "請輸入玩家姓名"
            }
            else
            {
                tvName.text = String.format("名字\n%s", edName.text.toString())
                if (btnScissor.isChecked)
                {
                    tvMmora.text = String.format("我方出拳\n剪刀")
                }
                else if (btnRock.isChecked)
                {
                    tvMmora.text = String.format("我方出拳\n石頭")
                } else {
                    tvMmora.text = String.format("我方出拳\n布")
                }

                //Random for computer mora.
                val computer = (Math.random() * 3).toInt()

                when (computer)
                {
                    0 -> tvCmora.text = String.format("電腦出拳\n剪刀")
                    1 -> tvCmora.text = String.format("電腦出拳\n石頭")
                    else -> tvCmora.text = String.format("電腦出拳\n布")
                }


                //Result
                if (btnScissor.isChecked && computer == 2 || btnRock.isChecked && computer == 0 || btnPaper.isChecked && computer == 1)
                {
                    tvWinner.text = String.format("勝利者\n${edName.text}".trimIndent())
                    tvText.text = String.format("恭喜你獲勝了！！！")
                }
                else if (btnScissor.isChecked && computer == 1 || btnRock.isChecked && computer == 2 || btnPaper.isChecked && computer == 0)
                {
                    tvWinner.text = String.format("勝利者\n電腦")
                    tvText.text = "可惜，電腦獲勝了！"
                }
                else
                {
                    tvWinner.text = String.format("勝利者\n平手")
                    tvText.text = "平局，請再試一次！"
                }
            }
        })
    }
}