package net.mortalsilence.indierace.mapper

import io.jenetics.jpx.WayPoint
import net.mortalsilence.indierace.dto.LatLngTimeEle


private fun mapWayPoint2LatLngTimeEle(it: WayPoint) =
        LatLngTimeEle(it.latitude.toDouble(), it.longitude.toDouble(), it.elevation.get().toDouble(), it.time.get())