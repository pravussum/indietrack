package net.mortalsilence.indierace.rest

import net.mortalsilence.indierace.dao.Segment
import net.mortalsilence.indierace.dao.SegmentRepository
import net.mortalsilence.indierace.dto.DtoSegment
import net.mortalsilence.indierace.mapper.LatLng2LineStringMapper
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/segment")
@Produces(MediaType.APPLICATION_JSON)
class SegmentController(@Inject internal val segmentRepository: SegmentRepository,
                        @Inject internal val latLng2LineStringMapper: LatLng2LineStringMapper) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    fun createSegment(segment: DtoSegment) : Long {
        val segmentEntity = Segment(name = segment.name, points = latLng2LineStringMapper.mapLatLng2LineString(segment.points))
        return segmentRepository.save(segmentEntity).id!!
    }
}
