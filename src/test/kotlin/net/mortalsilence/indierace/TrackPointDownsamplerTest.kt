package net.mortalsilence.indierace

import net.mortalsilence.indierace.dao.TrackPoint
import net.mortalsilence.indierace.mapper.TrackPointMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.GeometryFactory
import java.time.ZonedDateTime.now

internal class TrackPointDownsamplerTest {

    val geometryFactory = GeometryFactory()
    val trackPointDownsampler = TrackPointDownsampler(TrackPointMapper(geometryFactory))

    @Test
    fun downSampleByTargetSize() {
        val result = trackPointDownsampler.downSampleWithTargetSize(listOf(
                TrackPoint(id = 1, location = geometryFactory.createPoint(), time = now()),
                TrackPoint(id = 2, location = geometryFactory.createPoint(), time = now()),
                TrackPoint(id = 3, location = geometryFactory.createPoint(), time = now()),
                TrackPoint(id = 4, location = geometryFactory.createPoint(), time = now()),
                TrackPoint(id = 5, location = geometryFactory.createPoint(), time = now())
        ), 2, Collection<TrackPoint>::first)
        Assertions.assertEquals(2, result.size)
    }
}