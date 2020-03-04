package net.mortalsilence.indierace

import net.mortalsilence.indierace.dao.TrackPointRepository
import net.mortalsilence.indierace.dto.LatLngTimeEle
import net.mortalsilence.indierace.mapper.TrackPointMapper
import java.util.*
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/trackpoint")
@Produces(MediaType.APPLICATION_JSON)
class ExampleResource(@Inject internal val trackPointRepository: TrackPointRepository,
                      @Inject internal val trackPointMapper: TrackPointMapper) {

    @GET
    @Path("/{id}")
    fun getTrackPoint(@PathParam("id") id: Long) : Optional<LatLngTimeEle> {
        return trackPointRepository.findById(id)
                .map(trackPointMapper::mapTrackpoint2LatLong)
    }

    @GET
    @Path("/")
    fun getTrackPoints(): List<LatLngTimeEle> {
        return trackPointRepository
                .findAll()
                .map(trackPointMapper::mapTrackpoint2LatLong)
    }



    @GET
    @Path("/distance")
    fun getDistance(): Double {
        val trackPoints = trackPointRepository.findAll()
        val iterator = trackPoints.iterator()
        val first = iterator.next()
        val second = iterator.next()
        return first.location.distance(second.location)
    }
}