package uz.xia.bazar.ui.main

import android.util.Patterns

data class SignInForm(
    val email: String,
    val password: String,
) {
    fun isValid() = email.isNotBlank() &&
            Patterns.EMAIL_ADDRESS.matcher(email)
        .matches() && password.isNotBlank() &&
            password.length >= 6
}

data class SignUpForm(
    val email: String,
    val password: String,
    val retryPassword: String,
) {
    fun isValid() = email.isNotBlank() &&
            Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() && password.isNotBlank() &&
            password.length >= 6 && password.equals(retryPassword)
}