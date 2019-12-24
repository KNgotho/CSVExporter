package com.example.csvexporter

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.opencsv.CSVWriter
import kotlinx.android.synthetic.main.view_data.*
import java.io.File
import java.io.FileWriter
import java.lang.Exception

class  ViewData : AppCompatActivity(){


    var dbHandler = DBHelper(this)
    var dataList = ArrayList<HashMap<String, String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_data)

        loadIntoList()


        export_btn.setOnClickListener(){
            permissionChecker()
            exportData()
        }
    }

    fun permissionChecker(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){

                // Insert any necessary explanations for the user
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_PERMISSION)
            }else{
            Toast.makeText(this, "READ PERMISSIONS GRANTED", Toast.LENGTH_LONG).show()
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // Insert any necessary explanations for the user
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_PERMISSION)
            }else{
            Toast.makeText(this, "WRITE PERMISSIONS GRANTED", Toast.LENGTH_LONG).show()
        }


    }

companion object{
    const val READ_PERMISSION = 111
    const val WRITE_PERMISSION = 222
}

    fun loadIntoList(){

        dataList.clear()
        val cursor = dbHandler.getAllData()


        cursor!!.moveToFirst()

        while (!cursor.isAfterLast){
            val map = HashMap<String, String>()
            map["data_id"] = cursor.getString(cursor.getColumnIndex(DBHelper.DATA_ID))
            map["data_out"] = cursor.getString(cursor.getColumnIndex(DBHelper.DATA_TIME))

            dataList.add(map)

            cursor.moveToNext()

    }
        findViewById<ListView>(R.id.list_view).adapter = CustomAdapter(this, dataList)

    }
    fun exportData(){
        var exportList = arrayOf("", "")
        var exportDir = File(Environment.getExternalStorageDirectory(), "")
        if(!exportDir.exists())
        {
            exportDir.mkdirs()
        }
        var file = File(exportDir, "Data.csv")
        try {

                file.createNewFile()
                var csvWrite = CSVWriter(FileWriter(file))
                var curCSV = dbHandler.getAllData()
                csvWrite.writeNext(curCSV!!.columnNames)

                while (curCSV.moveToNext())
                {
                    exportList  =  arrayOf(curCSV.getString(0), curCSV.getString(1))

                    csvWrite.writeNext(exportList)
                }
            csvWrite.close()
            curCSV.close()

        } catch (sqlEx: Exception){
            Log.e("ViewData", sqlEx.message, sqlEx)
        }


    }


}