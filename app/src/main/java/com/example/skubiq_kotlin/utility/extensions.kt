package com.example.skubiq_kotlin.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.Group
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.bluboy.android.presentation.utility.AppConstant
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*
import java.util.regex.Pattern

/**
 * Extension functions for set visibility of any view by calling
 * yourView.visible()
 * yourView.gone()
 */
fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}



fun Activity.getDeviceName(): String {
    return android.os.Build.MANUFACTURER + android.os.Build.MODEL
}

fun String.removeLeadingZero(): String {
    for (i in 0 until this.length) {
        if (this[i] != '0') {
            return this.substring(i)
        }
    }
    return  "0"
}

fun Activity.getDeviceUniqueId(): String {
    return Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
}


fun isPanValid(source: String): Boolean {
    if (source.trim { it <= ' ' }.isEmpty()) return false
    val expression = "[A-Z]{5}[0-9]{4}[A-Z]{1}"
    val p = Pattern.compile(
        expression,
        Pattern.CASE_INSENSITIVE
    )
    val m = p.matcher(source)
    return m.matches() && source.trim { it <= ' ' }.isNotEmpty()
}

fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}



fun Int.toTwoDigit(): String {
    return if (this < 9) {
        "0${this}"
    } else {
        "$this"
    }
}

fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}


/**
 * Hide Keyboard
 */
fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun isEmulator(): Boolean {
    return (Build.FINGERPRINT.contains("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
            || "google_sdk" == Build.PRODUCT)
}


/**
 * For start activity
 *
 * @param intent
 * @param requestCode - Nullable
 */
fun Activity.startActivityCustom(intent: Intent, requestCode: Int? = 0) {
    if (requestCode != null) {
        this.startActivityForResult(intent, requestCode)
    } else {
        this.startActivity(intent)
    }
}



fun getTouchStatus(activity: Activity) {
    activity.window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Fragment.startActivityCustom(intent: Intent, requestCode: Int? = 0) {
    if (requestCode != null) {
        this.startActivityForResult(intent, requestCode)
    } else {
        this.startActivity(intent)
    }
}


/**
 * For load image
 *
 * @param image - image url, file, uri
 */


fun String.titleCase(): String {
    return split(" ").map { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }
        .joinToString(" ")
}


/**
 * For show dialog
 *
 * @param title - title which shown in dialog (application name)
 * @param msg - message which shown in dialog
 * @param positiveText - positive button text
 * @param listener - positive button listener
 * @param negativeText - negative button text
 * @param negativeListener - negative button listener
 * @param icon - drawable icon which shown is dialog
 */


/**
 * For validate email, mobile, password
 */





fun Context.getDeviceTimeZone(): String {
    val timeZone: String = Calendar.getInstance().timeZone.id
    return timeZone
}

fun Activity.openPermissionSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", this.packageName, null)
    intent.data = uri
    startActivityForResult(intent, AppConstant.INTENT_SETTINGS)
}

fun Context.getAndroidID(): String {
    return Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID)
}

fun Context.hideKeyboardFrom(view: View) {
    val imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}



fun getTimeAgo(time1: Long): String {
    val SECOND_MILLIS = 1000
    val MINUTE_MILLIS = 60 * SECOND_MILLIS
    val HOUR_MILLIS = 60 * MINUTE_MILLIS
    val DAY_MILLIS = 24 * HOUR_MILLIS

    var time = time1
    if (time < 1000000000000L) {
        time *= 1000
    }

    val now = Calendar.getInstance().time.time
    if (time > now || time <= 0) {
        return "in the future"
    }

    val diff = now - time
    return when {
        diff < 48 * HOUR_MILLIS -> "Yesterday"
        else -> "${diff / DAY_MILLIS} days ago"
    }
}

fun AppCompatTextView.setHtmlString(content: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.setText(HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY))
    } else {
        this.setText(Html.fromHtml(content))
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun AppCompatActivity.statusBarGone() {
//    window.apply {
//        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        statusBarColor = ContextCompat.getColor(context,R.color.colorBlackOpacity30) //Color.TRANSPARENT
//    }
}

fun AppCompatActivity.changeStatusBarColor(@ColorRes color: Int) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        window.statusBarColor = ContextCompat.getColor(this, color)
//    }
}

fun AppCompatActivity.setLightStatusBar(isLight: Boolean) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        var flags = window.decorView.systemUiVisibility
//        flags = if (isLight) {
//            flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        } else {
//            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
//        }
//        window.decorView.systemUiVisibility = flags
//    }
}


//fun AppCompatActivity.showImagePicker(imageSelectedListener: TedBottomPicker.OnImageSelectedListener) {
//    gun0912.tedbottompicker.TedBottomPicker.Builder(this)
//        .setOnImageSelectedListener(imageSelectedListener)
//        .setPeekHeight(600)
//        .showTitle(true)
//        .setPreviewMaxCount(10000)
//        .setTitle(R.string.select_from_gallery as Int)
//        .setCompleteButtonText(R.string.done)
//        .setEmptySelectionText(R.string.select_images as Int)
//        .showCameraTile(true)
//        .showGalleryTile(false)
//        .setImageProvider { imageView, imageUri ->
//            GlideApp.with(this)
//                .load(imageUri)
//                .placeholder(R.drawable.icn_placeholder_square)
//                .into(imageView)
//        }
//        .create()
//        .show(this.supportFragmentManager)
//}


fun Activity?.setAdjustPan() {
    this?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
}

fun Activity?.setAdjustResize() {
    this?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
}

fun Activity?.setAdjustNothing() {
    this?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
}

fun Activity?.setUnspecified() {
    this?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
}

fun Activity?.setFlagFullScreen() {
    this?.window?.setSoftInputMode(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Context.getDeviceIpAddress(): String? {
    var actualConnectedToNetwork: String? = null
    val connManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (connManager != null) {
        val mWifi: NetworkInfo? = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (mWifi?.isConnected() == true) {
            actualConnectedToNetwork = getWifiIp()
        }
    }
    if (TextUtils.isEmpty(actualConnectedToNetwork)) {
        actualConnectedToNetwork = getNetworkInterfaceIpAddress()
    }
    if (TextUtils.isEmpty(actualConnectedToNetwork)) {
        actualConnectedToNetwork = "127.0.0.1"
    }
    return actualConnectedToNetwork
}

fun Context.getWifiIp(): String? {
    val mWifiManager: WifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
    if (mWifiManager != null && mWifiManager.isWifiEnabled()) {
        val ip: Int = mWifiManager.getConnectionInfo().getIpAddress()
        return ((ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF) + "."
                + (ip shr 24 and 0xFF))
    }
    return ""
}

fun Context.getNetworkInterfaceIpAddress(): String? {
    try {
        val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
        while (en.hasMoreElements()) {
            val networkInterface: NetworkInterface = en.nextElement()
            val enumIpAddr: Enumeration<InetAddress> = networkInterface.getInetAddresses()
            while (enumIpAddr.hasMoreElements()) {
                val inetAddress: InetAddress = enumIpAddr.nextElement()
                if (!inetAddress.isLoopbackAddress() && inetAddress is Inet4Address) {
                    val host: String = inetAddress.getHostAddress()
                    if (!TextUtils.isEmpty(host)) {
                        return host
                    }
                }
            }
        }
    } catch (ex: java.lang.Exception) {
        Log.e("IP Address", "getLocalIpAddress", ex)
    }
    return ""
}


fun getIPAddress(useIPv4: Boolean): String {
    try {
        val interfaces: List<NetworkInterface> =
            Collections.list(NetworkInterface.getNetworkInterfaces())
        for (intf in interfaces) {
            val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
            for (addr in addrs) {
                if (!addr.isLoopbackAddress) {
                    val sAddr = addr.hostAddress
                    //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                    val isIPv4 = sAddr.indexOf(':') < 0
                    if (useIPv4) {
                        if (isIPv4) return sAddr
                    } else {
                        if (!isIPv4) {
                            val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                            return if (delim < 0) sAddr.toUpperCase() else sAddr.substring(0, delim)
                                .toUpperCase()
                        }
                    }
                }
            }
        }
    } catch (ignored: java.lang.Exception) {
    } // for now eat exceptions
    return ""
}


fun getDeviceSerialNumber(): String {
    return Build.SERIAL
}


fun getMacAddress(activity: Activity): String {
    val wifiManager = activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    return wifiManager.connectionInfo.macAddress
}

fun getToolbarLogoIcon(toolbar: Toolbar): View {
    val hadContentDescription = android.text.TextUtils.isEmpty(toolbar.logoDescription)
    val contentDescription = if (!hadContentDescription) toolbar.logoDescription.toString() else "logoContentDescription"
    toolbar.logoDescription = contentDescription
    val potentialViews = ArrayList<View>()
    toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
    val logoIcon: View?
    logoIcon = if (potentialViews.size > 0) {
        potentialViews[0]
    } else null
    if (hadContentDescription) {
        toolbar.logoDescription = null
    }
    return logoIcon!!
}






