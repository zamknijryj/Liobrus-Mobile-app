package com.example.zamknijryjx.liobrus.model

/**
 * Created by zamknijryjx on 23.11.17.
 */

class Wiadomosc() {
    var nadawca: String? = null
    var temat: String? = null
    var data: String? = null

    constructor(nadawca: String, temat: String,
                data: String):this(){

        this.nadawca = nadawca
        this.temat = temat
        this.data = data
    }
}