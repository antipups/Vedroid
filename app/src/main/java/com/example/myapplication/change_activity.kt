package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.activity_settings.*

class change_activity : AppCompatActivity() {

    private lateinit var checkBox_wifi : CheckBox
    private lateinit var checkBox_bluetooth : CheckBox
    private lateinit var checkBox_brightness : CheckBox
    private lateinit var checkBox_dist : CheckBox
    private lateinit var button_update : Button
    private lateinit var button_delete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)

        checkBox_wifi = findViewById(R.id.checkBox_wifi)
        checkBox_bluetooth = findViewById(R.id.checkBox_bluetooth)
        checkBox_brightness = findViewById(R.id.checkBox_brigtness)
        checkBox_dist = findViewById(R.id.checkBox_dist)
        button_update = findViewById(R.id.button_change)
        button_delete = findViewById(R.id.button_delete)
        addActionListeners()
    }

    private fun addActionListeners() {
        /**
         * Функиця ставящая прослушки на все чекбоксы, при нажатии которых включается
         * редактирование нужного элемента
         */


        checkBox_wifi.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.switch_wifi).isEnabled = checkBox_wifi.isChecked
        })

        checkBox_bluetooth.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.switch_bluetooth).isEnabled = checkBox_bluetooth.isChecked
        })

        checkBox_brightness.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.seekBar_brigtness).isInvisible = !checkBox_brightness.isChecked
        })

        checkBox_dist.setOnClickListener(View.OnClickListener {
            findViewById<Switch>(R.id.switch_dist).isEnabled = checkBox_dist.isChecked
        })

        button_update.setOnClickListener(View.OnClickListener {
            val db = FeedReaderContract.FeedReaderDbHelper(this)
            db.updateData(conveter())
            finish()
        })

        button_delete.setOnClickListener(View.OnClickListener {
            val db = FeedReaderContract.FeedReaderDbHelper(this)
            db.deleteData(intent.getStringExtra("title"))
            finish()
        })
    }

    fun conveter(): Setting {
        val title = intent.getStringExtra("title")
        var wifi: Int = 2
        var bluetooth: Int = 2
        var brightness: Int = -1
        if (switch_wifi.isEnabled) wifi =  if (switch_wifi.isChecked) 1 else 0
        if (switch_bluetooth.isEnabled) bluetooth =  if (switch_bluetooth.isChecked) 1 else 0
        if (!seekBar_brigtness.isInvisible) brightness = seekBar_brigtness.progress
        return Setting(title, wifi, bluetooth, brightness)
    }
}