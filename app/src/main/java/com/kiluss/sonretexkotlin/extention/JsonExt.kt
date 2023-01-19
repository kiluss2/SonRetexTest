package com.kiluss.sonretexkotlin.extention

import org.json.JSONArray
import org.json.JSONException

@Throws(JSONException::class)
fun JSONArray.toList(): List<Any> {
    val list = mutableListOf<Any>()
    for (i in 0 until this.length()) {
        var value: Any = this[i]
        when (value) {
            is JSONArray -> value = value.toList()
        }
        list.add(value)
    }
    return list
}
