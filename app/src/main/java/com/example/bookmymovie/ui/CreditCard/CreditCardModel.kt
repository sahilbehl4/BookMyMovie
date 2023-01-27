package com.example.bookmymovie.ui.CreditCard

import com.google.firebase.database.Exclude

data class CreditCardModel(var ccNumber: String, var date: String, var cvv: String) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "ccNumber" to ccNumber,
            "date" to date,
            "cvv" to cvv
        )
    }
}