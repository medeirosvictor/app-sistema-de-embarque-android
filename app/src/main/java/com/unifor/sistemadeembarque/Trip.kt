package com.unifor.sistemadeembarque

import java.util.*

//val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//val currentDate = sdf.format(Date())
//System.out.println(" C DATE is  "+currentDate)

data class Trip(
    val trip_id: String? = null,
    val date: Date? = null,
    val bus_line: Int? = null,
    val bus_id: String? = null,
    val cashier_id: String? = null,
    val passenger_list: MutableList<User>? = null,
    val payment_type: String? = null
)
