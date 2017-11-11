package com.example.zamknijryjx.liobrus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_user.*
import org.json.JSONArray

class UserActivity : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    private val USER_DATA_API = "http://laude.ct8.pl/api/user/data/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        volleyRequest = Volley.newRequestQueue(this)

        getData.setOnClickListener {
            val jsonArrayReq = JsonArrayRequest(Request.Method.GET, USER_DATA_API,
                    Response.Listener {
                        response ->
                        dataText.text = response.toString()
                    },
                    Response.ErrorListener {
                        error ->
                        dataText.text = error.toString()

                    })

            volleyRequest!!.add(jsonArrayReq)
        }

    }
}
