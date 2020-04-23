package net.mortalsilence.indierace.persistence.repositories.segment

import net.mortalsilence.indierace.dto.DtoAttempt
import net.mortalsilence.indierace.dto.DtoLatLng
import net.mortalsilence.indierace.dto.DtoSegmentGeoJson
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class CustomSegmentRepositoryImpl(@Inject @PersistenceContext internal val entityManager: EntityManager): CustomSegmentRepository {
    override fun getAttemptsForTrack(trackId: Long): List<DtoAttempt> {
        val sql = """
            with attempt as (select seg_id, track_id, (st_makeline(tp.location order by tp.time)) as points
                             from trackpoint tp
                                      join
                                  (select segment.id as seg_id, st_buffer(cast (points as geography), 20, 'endcap=square join=round') as points from segment group by segment.id) seg
                                  on st_intersects (cast(tp.location as geography), seg.points)
                             where tp.track_id = $trackId 
                             group by tp.track_id, seg.seg_id)
            select seg_id as segmentId,
                   track_id as trackId,
                   (select name from segment where id = seg_id) as segmentName,
                   st_length(cast (points as geography)) as length,
                   (st_m(st_pointn(points, -1)) - st_m(st_pointn(points, 1))) as durationInSec
            from attempt
        """.trimIndent()
        @Suppress("UNCHECKED_CAST")
        return entityManager.createNativeQuery(sql, "AttemptMapping")
                .resultList as List<DtoAttempt>
    }


    override fun getBufferedSegmentsInBoundingBoxAsGeoJson(lowerLeft: DtoLatLng, upperRight: DtoLatLng, buffered: Boolean?): List<DtoSegmentGeoJson> {
        val sql = """
            select id as segmentId, st_asgeojson(points) as geoJson from segment where points && 
                st_makeenvelope(${lowerLeft.longitude}, ${lowerLeft.latitude},${upperRight.longitude}, ${upperRight.latitude})
        """.trimIndent()
        @Suppress("UNCHECKED_CAST")
        return entityManager.createNativeQuery(sql, "SegmentAsGeoJsonMapping")
                .resultList as List<DtoSegmentGeoJson>
    }
}