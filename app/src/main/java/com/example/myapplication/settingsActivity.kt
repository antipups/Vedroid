package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible


class settingsActivity : AppCompatActivity() {

    var checkBox_wifi : CheckBox? = null
    var checkBox_bluetooth : CheckBox? = null
    var checkBox_brightness : CheckBox? = null
    var checkBox_dist : CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        checkBox_wifi = findViewById(R.id.checkBox_wifi)
        checkBox_bluetooth = findViewById(R.id.checkBox_bluetooth)
        checkBox_brightness = findViewById(R.id.checkBox_brigtness)
        checkBox_dist = findViewById(R.id.checkBox_dist)
        addActionListeners()
    }

    private fun addActionListeners() {
        /**
         * Функиця ставящая прослушки на все чекбоксы, при нажатии которых включается
         * редактирование нужного элемента
        */


        checkBox_wifi?.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.switch_wifi).isEnabled = checkBox_wifi?.isChecked!!
        })

        checkBox_bluetooth?.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.switch_bluetooth).isEnabled = checkBox_bluetooth?.isChecked!!
        })

        checkBox_brightness?.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.seekBar_brigtness).isInvisible = !checkBox_brightness?.isChecked!!
        })

        checkBox_dist?.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.switch_dist).isEnabled = checkBox_dist?.isChecked!!
        })


    }




}