package net.mortalsilence.indierace.dao

import net.mortalsilence.indierace.dto.DtoTrackInfo
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Suppress("", "unused")
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

    override fun getTrackInfo(trackId: Long?): List<DtoTrackInfo> {
        val trackIdCondition = if(trackId != null) "where track.id = $trackId" else ""
        val sql = """
            select
                track_id as id,
                min(name) as trackName,
                st_length(cast(st_makeline(location order by time) as geography)) as distance,
                st_extent(location) as boundaries,
                min(time) as startTime,
                max(time) as endTime
            from trackpoint join track on track_id = track.id
            $trackIdCondition
            group by track_id
            order by min(time) desc
            """

        @Suppress("UNCHECKED_CAST")
        return entityManager.createNativeQuery(sql, "TrackInfoMapping")
                .resultList as List<DtoTrackInfo>
    }

    override fun getSimplifiedTrackInfo(trackId: Long?): List<DtoTrackInfo> {
        val trackIdCondition = if(trackId != null) "where track.id = $trackId" else ""
        val sql = """
            with line as (
                select track_id,
                       min(time) as startTime,
                       max(time) as endTime,
                       st_makeline(location order by time) as line,
                       st_extent(location) as boundaries
                from trackpoint
                group by track_id
            )
            select
                line.track_id as id,
                track.name as trackName,
                st_simplifypreservetopology(line.line, st_length(line) / 100),
                boundaries,
                line.startTime,
                endTime
            from line join track on line.track_id = track.id
            $trackIdCondition
            order by starttime desc
            """

        @Suppress("UNCHECKED_CAST")
        return entityManager.createNativeQuery(sql, "TrackInfoMapping")
                .resultList as List<DtoTrackInfo>
    }

}