package com.example.i_calculator.ui

class UIUtil {
    private val emptyError = " must be not empty\n"

    fun validateEmail(inputEmail: String?): String {
        val email = trimInput(inputEmail)
//        val pattern = ("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
//                "\\@" +
//                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
//                "(" +
//                "\\." +
//                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
//                ")+").toRegex()
        return when {
            email.isEmpty() -> "Email$emptyError"
//            !pattern.matches(email) -> "Email invalid\n"
            else -> ""
        }
    }

    fun validatePassword(password: String?): String {
        val pattern = """^(?=.*[0-9])(?=.*[@#$%!\-_?&])(?=\S+$).{8,}${'$'}""".toRegex()
        return when {
            password.isNullOrEmpty() -> "Password$emptyError"
            password.length < 8 -> "Password must has at least 8 characters\n"
            !pattern.matches(password) -> "Password must inclusion of at least 1 special character and 1 number character\n"
            else -> ""
        }
    }

    fun checkRePassword(pass: String?, rePass: String?): String {
        return when {
            pass.isNullOrEmpty() && rePass.isNullOrEmpty() -> "Please re-enter the password\n"
            rePass != pass -> "Re-password does not match\n"
            else -> ""
        }
    }

    private fun trimInput(s: String?): String {
        return if (s.isNullOrEmpty())
            ""
        else s.trim().replace("\\s+".toRegex(), " ")
    }
}