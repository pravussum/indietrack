package net.mortalsilence.indierace.rest

import net.mortalsilence.indierace.dao.TrackPointRepository
import net.mortalsilence.indierace.dto.DtoTrackInfo
import net.mortalsilence.indierace.dto.LatLngTimeEle
import net.mortalsilence.indierace.dto.MultipartBody
import net.mortalsilence.indierace.gpx.GpxTrackPersistor
import net.mortalsilence.indierace.mapper.TrackPointMapper
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm
import org.postgis.LineString
import org.postgis.PGbox2d
import java.time.Instant
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/track")
@Produces(MediaType.APPLICATION_JSON)
class TrackRestController(@Inject internal val gpxTrackPersistor: GpxTrackPersistor,
                          @Inject internal val trackPointRepository: TrackPointRepository,
                          @Inject internal val trackPointMapper: TrackPointMapper) {

    @GET
    @Path("/")
    fun getTracks(): List<TrackInfoResult> {
        return trackPointRepository
                .getTrackInfo()
                .map(mapToLatLngBounds())
    }

    @GET
    @Path("/{id}")
    fun getTrack(@PathParam("id") id: Long): List<TrackInfoResult> {
        return trackPointRepository
                .getTrackInfo(id)
                .map(mapToLatLngBounds())
    }

    private fun mapToLatLngBounds(): (DtoTrackInfo) -> TrackInfoResult {
        return {
            val box2d = PGbox2d(it.boundaries)
            val boundaries = arrayOf(arrayOf(box2d.llb.getY(),box2d.llb.getX()),
                    arrayOf(box2d.urt.getY(),box2d.urt.getX()))
            TrackInfoResult(it.id, it.name, it.distance, boundaries, it.startTime, it.endTime)
        }
    }

    @GET
    @Path("/{id}/trackpoints")
    fun getTrackPoints(@PathParam("id") id: Long) : List<LatLngTimeEle> {
        return trackPointRepository
                .findByTrackId(id)
                .map(trackPointMapper::mapTrackpoint2LatLong)
    }

    @GET
    @Path("/{id}/trackpoints/simplified")
    fun getSimplifiedTrackPoints(@PathParam("id") id: Long) : List<LatLngTimeEle> {
        val wkt = trackPointRepository.getSimplifiedTrackPoints(id)
        val lineString = LineString(wkt)
        val simplifiedPoints = lineString.points
                .map {
                    LatLngTimeEle(
                            latitude = it.y,
                            longitude = it.x,
                            elevation = it.z,
                            time = Date.from(Instant.ofEpochSecond(it.m.toLong()))
                    )
                }
        return simplifiedPoints
    }

    @GET
    @Path("/{id}/geojson")
    fun getTrackPointsAsGeoJson(@PathParam("id") trackId: Long) : String {
        return trackPointRepository.getTrackPointsAsGeoJson(trackId)
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

    data class TrackInfoResult(val id: Long,
                               val name: String,
                               val distance: Long,
                               val boundaries: Array<Array<Double>>,
                               val startTime: Date,
                               val endTime: Date)
}