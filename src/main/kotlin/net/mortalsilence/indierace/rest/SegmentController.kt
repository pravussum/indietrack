package net.mortalsilence.indierace.rest

import com.fasterxml.jackson.databind.ObjectMapper
import net.mortalsilence.indierace.persistence.entities.Segment
import net.mortalsilence.indierace.persistence.repositories.segment.SegmentRepository
import net.mortalsilence.indierace.dto.DtoAttempt
import net.mortalsilence.indierace.dto.DtoLatLng
import net.mortalsilence.indierace.dto.DtoSegment
import net.mortalsilence.indierace.mapper.LatLng2LineStringMapper
import org.geojson.GeoJsonObject
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/segment")
@Produces(MediaType.APPLICATION_JSON)
class SegmentController(@Inject internal var segmentRepository: SegmentRepository,
                        @Inject internal var latLng2LineStringMapper: LatLng2LineStringMapper) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    fun createSegment(segment: DtoSegment) : Long {
        val segmentEntity = Segment(name = segment.name, points = latLng2LineStringMapper.mapLatLng2LineString(segment.points))
        return segmentRepository.save(segmentEntity).id!!
    }

    @GET
    @Path("/attempts")
    fun getAttempts(@QueryParam("trackId") trackId: Long): List<DtoAttempt> {
        return segmentRepository.getAttemptsForTrack(trackId)
    }

    @POST
    @Path("/geojson")
    @Consumes(MediaType.APPLICATION_JSON)
    fun getSegmentsInBoundingBox(params: GetSegmentsInBoundingBoxParams): List<GetSegmentInBoundingBoxResult> {
        return segmentRepository.getBufferedSegmentsInBoundingBoxAsGeoJson(
                params.boundingBoxLowerLeft, params.boundingBoxUpperRight,params.buffered)
                .map {GetSegmentInBoundingBoxResult(it.segmentId, ObjectMapper().readValue(it.geoJson, GeoJsonObject::class.java)) }
    }

    data class GetSegmentsInBoundingBoxParams(
            val boundingBoxLowerLeft: DtoLatLng,
            val boundingBoxUpperRight: DtoLatLng,
            val buffered: Boolean? = true
    )

    data class GetSegmentInBoundingBoxResult(val segmentId: Long, val geoJson: GeoJsonObject)

}
