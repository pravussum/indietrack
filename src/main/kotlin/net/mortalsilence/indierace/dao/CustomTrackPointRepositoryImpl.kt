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
         where track_id = $trackId)
    ,
        (select st_buffer(cast(st_makeline(location order by time) as geography), 20)
                    as line
         from trackpoint
         where track_id = $segmentTrackId)
           ))"""
        return entityManager.createNativeQuery(sql)
                .singleResult
                .toString()
    }

    override fun getTrackPointsAsGeoJson(trackId: Long): String {
        val sql = """
select st_asgeojson(
    cast(
            st_makeline(location order by time) as geography)
    )
from trackpoint
where track_id = $trackId            
        """
        return entityManager.createNativeQuery(sql)
                .singleResult
                .toString()
    }


}