package com.example.zamknijryjx.liobrus.Activities

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.navigation_header.imieHeader
import kotlinx.android.synthetic.main.navigation_header.klasaHeader
import org.json.JSONArray
import org.json.JSONException
import com.example.zamknijryjx.liobrus.R
import kotlinx.android.synthetic.main.custom_navbar.*
import java.net.CookieHandler
import java.net.CookieManager


class UserActivity : AppCompatActivity() {

    private var volleyRequest: RequestQueue? = null
    private val USER_DATA_API = "http://laude.ct8.pl/api/user/data/"
    private var mToggle: ActionBarDrawerToggle? = null
    private var userImie: String? = ""
    private var userKlasa: String? = ""
    private var userSrednia: String? = ""

    private var gothamMedTF: Typeface? = null
    private var gothamLightTF: Typeface? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        mToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(mToggle!!)
        mToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setFonts()

        volleyRequest = Volley.newRequestQueue(this)

        getJSONArray()

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_sprawdziany -> {
                    // handle click
                    val sprActivity = Intent(this, SprawdzianActivity::class.java)
                    sprActivity.putExtra("imie", userImie)
                    sprActivity.putExtra("klasa", userKlasa)
                    startActivity(sprActivity)
                    true
                }

                R.id.nav_aktualizacja -> {

                    val aktualizacjaAct = Intent(this, AktualizacjaActivity::class.java)
                    startActivity(aktualizacjaAct)

                    true
                }

                else -> false
            }
        }

    }

    private fun setFonts() {
        gothamMedTF = Typeface.createFromAsset(assets, "fonts/Gotham-Medium.otf")
        gothamLightTF = Typeface.createFromAsset(assets, "fonts/Gotham-Light.otf")


        imie.typeface = gothamLightTF
        klasa.typeface = gothamLightTF
        max.typeface = gothamMedTF
        min.typeface = gothamMedTF
        srednia.typeface = gothamMedTF
        dataNumreka.typeface = gothamMedTF
        szczesliwyNumerek.typeface = gothamMedTF
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (mToggle!!.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    fun getJSONArray() {
        val jsonArrayReq = JsonArrayRequest(Request.Method.GET, USER_DATA_API,
                Response.Listener { response: JSONArray ->
                    try {
                        for (i in 0..response.length() - 1) {
                            val userObj = response.getJSONObject(i)

                            userImie = userObj.getString("imie")
                            userKlasa = userObj.getString("klasa")
                            userSrednia = userObj.getString("srednia")
                            val userDataNumerka = userObj.getString("data_numerka")
                            val userLuckyNumber = userObj.getString("szczesliwy_numerek")

                            imie.text = userImie
                            klasa.text = userKlasa
                            srednia.text = userSrednia
                            dataNumreka.text = userDataNumerka
                            szczesliwyNumerek.text = userLuckyNumber

                        }

                        try {
                            val sredniaInt = userSrednia!!.toFloat()

                            val procent = (sredniaInt * 100) / 6
                            progressBar.progress = procent.toInt()
                        } catch (e: NumberFormatException) {
                            e.printStackTrace()
                            progressBar.progress = 100
                        }


                        imieHeader.text = userImie
                        klasaHeader.text = userKlasa
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                })

        var policy = DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        jsonArrayReq.retryPolicy = policy
        volleyRequest!!.add(jsonArrayReq)
    }

}
