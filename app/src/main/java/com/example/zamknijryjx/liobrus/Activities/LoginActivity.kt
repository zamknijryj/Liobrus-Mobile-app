package com.example.zamknijryjx.liobrus.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.zamknijryjx.liobrus.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.net.CookieHandler
import java.net.CookieManager
import com.android.volley.toolbox.HttpStack
import java.net.CookiePolicy
import java.net.CookiePolicy.ACCEPT_ALL
import android.R.id.edit
import android.annotation.SuppressLint
import android.content.SharedPreferences.Editor
import android.content.SharedPreferences
import com.android.volley.toolbox.StringRequest
import java.net.CookieStore


class LoginActivity : AppCompatActivity() {

    private var volleyRequest: RequestQueue? = null
    private val LOGIN_API_URL = "http://laude.ct8.pl/api/user/login/"
    private val jsonObj = JSONObject()

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.hide()
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_login)

        CookieHandler.setDefault(CookieManager())

        volleyRequest = Volley.newRequestQueue(this)

        val intent = intent
        if (intent.extras != null) {
            Toast.makeText(this, intent.getStringExtra("info"), Toast.LENGTH_LONG).show()
        }


        loginBtn.setOnClickListener {
            login()
        }


    }

    fun newAccount(v: View) {
        val registerAct = Intent(this, RegisterActivity::class.java)
        startActivity(registerAct)
    }


    private fun login() {
        jsonObj.put("username", username.text)
        jsonObj.put("password", passwd.text)

        val req = JsonObjectRequest(Request.Method.POST, LOGIN_API_URL, jsonObj,
                Response.Listener { response ->
                    val user = Intent(this, UserActivity::class.java)
                    startActivity(user)
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this@LoginActivity, error.toString(), Toast.LENGTH_LONG).show()
                })

        req.retryPolicy = DefaultRetryPolicy(
                53000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        volleyRequest!!.add(req)
    }

    private fun login2() {

        val req = object : StringRequest(Request.Method.POST,
                LOGIN_API_URL,
                Response.Listener { response ->
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                    val user = Intent(this, UserActivity::class.java)
                    startActivity(user)

                }, Response.ErrorListener { e ->

            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }) {
            public override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("username", username.text.toString())
                params.put("password", passwd.text.toString())
                return params
            }

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded";
            }
        }
        req.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        volleyRequest!!.add(req)

    }
}
