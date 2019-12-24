package com.example.csvexporter

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHandler = DBHelper(this)


        test_view.setOnClickListener() {

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

            val time = current.format(formatter)
            dbHandler.addData(time)
            Toast.makeText(this, "Data added", Toast.LENGTH_LONG).show()


        }

        view_data.setOnClickListener() {
            var view_data_intent = Intent(this, ViewData::class.java)

            startActivity(view_data_intent)
        }

    }
}
