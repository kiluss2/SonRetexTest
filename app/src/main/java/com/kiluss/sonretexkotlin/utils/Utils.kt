package com.kiluss.sonretexkotlin.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.kiluss.sonretexkotlin.constant.DATE_FORMAT
import java.text.ParseException
import java.util.Locale

object Utils {
    private var toast: Toast? = null

    internal fun showShortToast(context: Context, message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    internal fun showLongToast(context: Context, message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast?.show()
    }

    internal fun getDate(date: String): String? {
        val format = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return try {
            format.parse(date).toString()
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }
}
