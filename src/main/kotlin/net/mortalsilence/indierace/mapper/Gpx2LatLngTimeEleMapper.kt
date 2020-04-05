package net.mortalsilence.indierace.mapper

import io.jenetics.jpx.WayPoint
import net.mortalsilence.indierace.dto.DtoTrackPoint
import java.util.*


private fun mapWayPoint2LatLngTimeEle(it: WayPoint) =
        DtoTrackPoint(it.latitude.toDouble(), it.longitude.toDouble(), it.elevation.get().toDouble(),
                Date.from(it.time.get().toInstant()))