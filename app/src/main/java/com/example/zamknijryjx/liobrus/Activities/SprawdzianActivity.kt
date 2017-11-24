package com.example.zamknijryjx.liobrus.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.zamknijryjx.liobrus.R
import com.example.zamknijryjx.liobrus.data.SprawdzianListAdapter
import com.example.zamknijryjx.liobrus.model.Sprawdzian
import kotlinx.android.synthetic.main.activity_sprawdzian.*
import kotlinx.android.synthetic.main.navigation_header.imieHeader
import kotlinx.android.synthetic.main.navigation_header.klasaHeader

class SprawdzianActivity : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    private val SPRAWDZIANY_API_DATA = "http://laude.ct8.pl/api/sprawdzian/data/"
    var sprList: ArrayList<Sprawdzian>? = null
    var sprAdapter: SprawdzianListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    private var mToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sprawdzian)

        volleyRequest = Volley.newRequestQueue(this)
        sprList = ArrayList<Sprawdzian>()

        mToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(mToggle!!)
        mToggle!!.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    // handle click
                    val userActivity = Intent(this, UserActivity::class.java)
                    startActivity(userActivity)
                    true
                }
                else -> false
            }
        }

        getSprawdziany()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (mToggle!!.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun getSprawdziany() {
        var req = JsonArrayRequest(Request.Method.GET, SPRAWDZIANY_API_DATA,
                Response.Listener { response ->

                    for (i in 0..response.length() - 1) {
                        var sprObj = response.getJSONObject(i)

                        var sprPrzedmiot = sprObj.getString("przedmiot")
                        var sprOpis = sprObj.getString("opis")
                        var sprData = sprObj.getString("data")

                        var spr = Sprawdzian()
                        spr.przedmiot = sprPrzedmiot
                        spr.opis = sprOpis
                        spr.data = sprData

                        sprList!!.add(spr)

                        sprAdapter = SprawdzianListAdapter(sprList!!, this)
                        layoutManager = LinearLayoutManager(this)

                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = sprAdapter

                    }

                    // get extra info from previous intent
                    var intent = intent
                    if (intent.extras != null) {
                        imieHeader.text = intent.getStringExtra("imie")
                        klasaHeader.text = intent.getStringExtra("klasa")
                    }


                    try {
                        sprAdapter!!.notifyDataSetChanged()
                    } catch (e: KotlinNullPointerException) {
                        e.printStackTrace()
                        TODO("Dodać liczbe spr")
                    }

                },
                Response.ErrorListener {

                })

        volleyRequest!!.add(req)
    }

}
