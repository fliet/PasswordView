package com.fliest.passwordviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.edit_text)

        //editText.setTextSelectHandle(ResourcesCompat.getDrawable(resources, R.drawable.shape_text_select_handle, theme)!!)
    }
}