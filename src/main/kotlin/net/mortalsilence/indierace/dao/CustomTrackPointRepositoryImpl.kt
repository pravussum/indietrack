package net.mortalsilence.indierace.dao

import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.SqlResultSetMapping

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

    override fun getTrackInfo(): Any {
        val sql = """
            with tracks as (
                select track_id, cast(st_makeline(location order by time) as geography) as track
                from trackpoint
                group by track_id
            )
            select
                   track_id,
                   st_length(track),
                   st_asgeojson(cast(st_envelope(cast(track as geometry)) as geography)),
                   (select min(time) from trackpoint _tp where _tp.track_id = tracks.track_id) as start_time,
                   (select max(time) from trackpoint _tp where _tp.track_id = tracks.track_id) as end_time
            from tracks
            """
        return entityManager.createNativeQuery(sql)
                .resultList;
    }

}