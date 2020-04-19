package net.mortalsilence.indierace.mapper

import net.mortalsilence.indierace.dto.DtoLatLng
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.CoordinateXY
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.LineString
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class LatLng2LineStringMapper(@Inject internal val geometryFactory: GeometryFactory) {

    fun mapLatLng2LineString(latLngPoints: List<DtoLatLng>) : LineString {
        val coordinates: Array<Coordinate> = latLngPoints
                .map { CoordinateXY(it.longitude, it.latitude) }
                .toTypedArray()
        return geometryFactory.createLineString(coordinates)
    }
}