package uz.xia.bazar.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun TextInputEditText.errorCheckingTextChanges(
    textInputLayout: TextInputLayout,
    @StringRes errorMessageId: Int,
    isValid: (String) -> Boolean
): Observable<String> {

    return textChanges().mapToString().doOnNext { input ->
        if (input.isNotEmpty()) {
            textInputLayout.error =
                if (isValid(input)) null else textInputLayout.context.getString(errorMessageId)
        }
    }
}

fun Fragment.hideKeyboard() = activity?.run {
    currentFocus?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Context.getBitmapFromVector(drawableId: Int): Bitmap? {
    val drawable = ContextCompat.getDrawable(this, drawableId) ?: return null

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    ) ?: return null
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}

fun Observable<CharSequence>.mapToString(): Observable<String> = this.map { it.toString() }


fun <T> Observable<T>.throttleFirstShort() = this.throttleFirst(500L, TimeUnit.MILLISECONDS)!!

object Validation {
    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    // val emailPattern="^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$"

    fun isValidPassword(password: String) = password.length >= 6
}

fun <T> lazyFast(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

fun Int.toDp() :Int = (this / Resources.getSystem().displayMetrics.density).toInt()