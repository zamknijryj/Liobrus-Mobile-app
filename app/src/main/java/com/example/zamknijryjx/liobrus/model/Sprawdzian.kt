package com.example.zamknijryjx.liobrus.model

/**
 * Created by zamknijryjx on 12.11.17.
 */

class Sprawdzian() {
    var przedmiot: String? = null
    var opis: String? = null
    var data: String? = null

    constructor(przedmiot: String, nauczyciel: String,
                opis: String, data:String):this(){

        this.przedmiot = przedmiot
        this.opis = opis
        this.data = data
    }

}