package net.mortalsilence.indierace.mapper

import io.jenetics.jpx.WayPoint
import net.mortalsilence.indierace.dao.TrackPoint
import net.mortalsilence.indierace.dto.LatLngTimeEle
import org.locationtech.jts.geom.CoordinateXYZM
import org.locationtech.jts.geom.GeometryFactory
import java.io.InvalidObjectException
import java.time.Instant.ofEpochSecond
import java.time.ZonedDateTime
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class TrackPointMapper(@Inject internal val geometryFactory: GeometryFactory) {

    fun mapWayPoint2TrackPoint(wayPoint: WayPoint) : TrackPoint {
        val longitude = wayPoint.longitude.toDouble()
        val latitude = wayPoint.latitude.toDouble()
        val elevation = wayPoint.elevation.map { it.toDouble() }.orElseThrow { InvalidObjectException("Invalid elevation in WayPoint.")}
        // TODO handle empty value
        val time = wayPoint.time.orElseThrow{InvalidObjectException("Invalid timestamp in WayPoint.")}
        return mapLatLongEleTimeToTrackPoint(longitude, latitude, elevation, time)
    }

    fun mapLatLongEleTimeToTrackPoint(longitude: Double, latitude: Double, elevation: Double, time: ZonedDateTime): TrackPoint {
        val coordinate = CoordinateXYZM(longitude, latitude, elevation, time.toEpochSecond().toDouble())
        val point = geometryFactory.createPoint(coordinate)
        return TrackPoint(location = point, time = time)
    }

    fun mapTrackpoint2LatLong(it: TrackPoint) = LatLngTimeEle(
            latitude = it.location.y,
            longitude = it.location.x,
            elevation = it.location.coordinate.getZ(),
            time = Date.from(ofEpochSecond(it.location.coordinate.m.toLong()))
    )
}
