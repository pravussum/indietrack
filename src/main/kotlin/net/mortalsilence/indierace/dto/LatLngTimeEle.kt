package net.mortalsilence.indierace.dto

import java.time.ZonedDateTime

data class LatLngTimeEle(val latitude: Double,
                         val longitude: Double,
                         val elevation: Double? = null,
                         val time: ZonedDateTime? = null)
