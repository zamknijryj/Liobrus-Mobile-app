package com.example.zamknijryjx.liobrus.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.zamknijryjx.liobrus.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn.setOnClickListener {
            val login_activity = Intent(this, LoginActivity::class.java)
            startActivity(login_activity)

        }

    }
}
