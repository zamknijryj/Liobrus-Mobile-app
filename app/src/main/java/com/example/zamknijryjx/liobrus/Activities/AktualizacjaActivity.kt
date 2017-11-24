package com.example.zamknijryjx.liobrus.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.zamknijryjx.liobrus.R
import kotlinx.android.synthetic.main.activity_aktualizacja.*
import org.json.JSONObject
import com.android.volley.toolbox.StringRequest
//import javax.swing.UIManager.put
import com.android.volley.AuthFailureError




class AktualizacjaActivity : AppCompatActivity() {

    private val UPDATE_URL = "http://laude.ct8.pl/api/user/data/aktualizacja/"
    private val link = "http://laude.ct8.pl/api/user/login/"
    private var volleyRequest: RequestQueue? = null
    private val jsonObj = JSONObject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_aktualizacja)

        volleyRequest = Volley.newRequestQueue(this)

//        val cookieMan = CookieManager()
//        CookieHandler.setDefault(cookieMan)

        updateButton.setOnClickListener {
            aktualizacja2()
        }

    }


    private fun aktualizacja() {

        jsonObj.put("username", usernameEdit.text)
        jsonObj.put("password", passwordEdit.text)

        val req = JsonObjectRequest(Request.Method.POST, link, jsonObj,
                Response.Listener { response ->
                    Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                })




        req.retryPolicy = DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

//        req.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        volleyRequest!!.add(req)
    }

    private fun aktualizacja2() {

        val req = object : StringRequest(Request.Method.POST,
                UPDATE_URL,
                Response.Listener { response ->
                    Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()


                }, Response.ErrorListener { e ->

            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }) {
            public override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("librus_user", usernameEdit.text.toString())
                params.put("librus_pswd", passwordEdit.text.toString())
                return params
            }

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded";
            }

//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                headers.put("Content-Type", "application/json; charset=utf-8")
//                return headers
//            }

        }
        req.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        volleyRequest!!.add(req)

    }

}
