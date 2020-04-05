package net.mortalsilence.indierace.dto

import java.util.*

data class LatLngTimeEle(val latitude: Double,
                         val longitude: Double,
                         val elevation: Double? = null,
                         val time: Date? = null,
                         var distToSuccessor: Double? = null,
                         var avgSpeedToSuccessor: Double? = null)
