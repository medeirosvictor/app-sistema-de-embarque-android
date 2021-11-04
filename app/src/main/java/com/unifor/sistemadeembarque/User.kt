package com.unifor.sistemadeembarque

import android.provider.ContactsContract
import java.math.BigDecimal

data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val balance: Double? = 00.00,
    val type: String? = null,
    val profile_picture: String? = null,
    val trips: MutableList<Trip>? = null,
    val activeTrip: Trip? = null,
    val status: String? = "inativo"
)
