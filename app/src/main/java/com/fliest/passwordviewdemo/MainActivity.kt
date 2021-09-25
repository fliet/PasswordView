package com.fliest.passwordviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import com.fliest.passwordview.NumberInputView
import com.fliest.passwordview.OnInputFinishListener
import com.fliest.passwordview.PasswordView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val numberInputView: NumberInputView = findViewById(R.id.number_input_view)
        val passwordView: PasswordView = findViewById(R.id.password_view)

        numberInputView.onInputFinishListener = object : OnInputFinishListener{
            override fun onInputFinish(inputStr: String) {
                Log.e("NumberInputView", "input text is $inputStr")
            }

        }

        passwordView.onInputFinishListener = object : OnInputFinishListener{
            override fun onInputFinish(inputStr: String) {
                Log.e("PasswordView", "password is $inputStr")
            }

        }*/
    }
}