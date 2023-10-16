package com.thomas.excel.panel

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by yangzhikuan
 * Date 2023/10/16
 * Description
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scoreText.setOnClickListener {
            startActivity(Intent(applicationContext, ScoreActivity::class.java))
        }
        queryText.setOnClickListener {
            startActivity(Intent(applicationContext, SpecifyActivity::class.java))
        }
    }


}