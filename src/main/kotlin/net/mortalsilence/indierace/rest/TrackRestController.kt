package net.mortalsilence.indierace.rest

import net.mortalsilence.indierace.dao.TrackPointRepository
import net.mortalsilence.indierace.dao.TrackRepository
import net.mortalsilence.indierace.dto.LatLngTimeEle
import net.mortalsilence.indierace.dto.MultipartBody
import net.mortalsilence.indierace.gpx.GpxTrackPersistor
import net.mortalsilence.indierace.mapper.TrackPointMapper
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/track")
@Produces(MediaType.APPLICATION_JSON)
class TrackRestController(@Inject internal val gpxTrackPersistor: GpxTrackPersistor,
                          @Inject internal val trackPointRepository: TrackPointRepository,
                          @Inject internal val trackRepository: TrackRepository,
                          @Inject internal val trackPointMapper: TrackPointMapper) {

    @GET
    @Path("/")
    fun getTracks(): List<Long> {
        return trackRepository.findAll()
                .map { it.id!! }
    }

    @GET
    @Path("/{id}")
    fun getTrackPoint(@PathParam("id") id: Long) : List<LatLngTimeEle> {
        return trackPointRepository
                .findByTrackId(id)
                .map(trackPointMapper::mapTrackpoint2LatLong)
    }

    @POST
    @Path("gpx")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    fun uploadGpx(@MultipartForm multipartForm: MultipartBody) {
        if(multipartForm.file == null) {
            throw IllegalArgumentException()
        }
        gpxTrackPersistor.persistGpxTrack(multipartForm.file!!)
    }

    @GET
    @Path("/intersection")
    fun getIntersection() : String {
        val geoJson = trackPointRepository.getIntersectionAsGeoJson(1, 5900)
        return geoJson
        //return Response.ok(mapOf(Pair("intersection",geoJson))).build()
    }
}