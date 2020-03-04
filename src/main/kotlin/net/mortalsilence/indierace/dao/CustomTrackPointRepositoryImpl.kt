package net.mortalsilence.indierace.dao

import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class CustomTrackPointRepositoryImpl(@Inject @PersistenceContext internal val entityManager: EntityManager): CustomTrackPointRepository {
    override fun getIntersectionAsGeoJson(segmentTrackId: Long, trackId: Long) : String {
        val sql = """
select st_asgeojson(st_intersection(
        (select cast(st_makeline(location order by time) as geography)
                    as line
         from trackpoint
         where track_id = 1)
    ,
        (select st_buffer(cast(st_makeline(location order by time) as geography), 20)
                    as line
         from trackpoint
         where track_id = 5900)
           ))"""
        return entityManager.createNativeQuery(sql)
                .singleResult
                .toString()
    }
}