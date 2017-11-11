package com.example.zamknijryjx.liobrus

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookieStore

class LoginActivity : AppCompatActivity() {

    private var volleyRequest: RequestQueue? = null
    private val REGISTER_API_URL = "http://laude.ct8.pl/api/user/register/"
    private val DATA_URL = "http://laude.ct8.pl/api/user/data/"
    private val LOGIN_API_URL = "http://laude.ct8.pl/api/user/login/"
    private val jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        volleyRequest = Volley.newRequestQueue(this)

        val cookieMan = CookieManager()
        CookieHandler.setDefault(cookieMan)

        loginBtn.setOnClickListener {

            login()

        }

    }


    private fun login() {
        jsonObj.put("username", username.text)
        jsonObj.put("password", passwd.text)

        val req = JsonObjectRequest(Request.Method.POST, LOGIN_API_URL, jsonObj,
                Response.Listener {
                    response ->
                    Toast.makeText(this@LoginActivity, response["status"].toString(), Toast.LENGTH_LONG).show()
                    val user = Intent(this, UserActivity::class.java)
                    startActivity(user)
                },
                Response.ErrorListener {
                    error ->
                    Toast.makeText(this@LoginActivity, error.toString(), Toast.LENGTH_LONG).show()
                })

        volleyRequest!!.add(req)
    }
}
