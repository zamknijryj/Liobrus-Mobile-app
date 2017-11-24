package com.example.zamknijryjx.liobrus.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.zamknijryjx.liobrus.R
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private var volleyRequest: RequestQueue? = null
    private var REGISTER_API_LINK = "http://laude.ct8.pl/api/user/register/"
    private var jsonObject = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar!!.hide()

        volleyRequest = Volley.newRequestQueue(this)

        registerBtn.setOnClickListener {
            register()
        }
    }

    public fun loginAccount(v: View) {
        var loginAct = Intent(this, LoginActivity::class.java)
        startActivity(loginAct)
    }

    private fun register() {
        jsonObject.put("username", username.text)
        jsonObject.put("email", email.text)
        jsonObject.put("password", passwd.text)
        var req = JsonObjectRequest(Request.Method.POST, REGISTER_API_LINK, jsonObject,
                Response.Listener {
                    response ->
                    val loginAct = Intent(this, LoginActivity::class.java)
                    loginAct.putExtra("info", "Konto zostało założone. Prosimy się zalogować")
                    startActivity(loginAct)
                },
                Response.ErrorListener {
                    error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                })

        volleyRequest!!.add(req)
    }
}
