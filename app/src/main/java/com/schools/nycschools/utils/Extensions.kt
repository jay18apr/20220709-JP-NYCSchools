package com.schools.nycschools.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.startActivity
import java.util.Locale

/**
 * Extension function to handle view clicks safely.
 * @param onSafeClick
 */
fun View.setOnSafeClickListener(
    onSafeClick: (View) -> Unit
) {
    setOnClickListener(
        SafeClickListener { v ->
            onSafeClick(v)
        }
    )
}

fun Context.emailClick(email: String) {
    val emailIntent = Intent(Intent.ACTION_SENDTO)
    emailIntent.data = Uri.parse("mailto:$email")
    startActivity(emailIntent)
}

fun Context.directionClick(lat: String, long: String) {
    val uri = String.format(
        Locale.ENGLISH,
        "http://maps.google.com/maps?q=loc:%f,%f",
        lat.toFloat(),
        long.toFloat()
    )
    val directionIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(directionIntent)
}

fun Context.faxClick(number: String) {
    // TODO
}

fun Context.linkClick(website: String) {
    val websiteIntent = Intent(Intent.ACTION_VIEW)
    if (!website.startsWith("http://") && !website.startsWith("https://")) {
        websiteIntent.data = Uri.parse("http://" + website)
    } else {
        websiteIntent.data = Uri.parse(website)
    }
    startActivity(websiteIntent)
}

fun Context.callClick(number: String) {
    val callIntent = Intent(Intent.ACTION_DIAL)
    callIntent.data = Uri.parse("tel:$number")
    startActivity(callIntent)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
