package net.mortalsilence.indierace.persistence.repositories.trackpoint

import net.mortalsilence.indierace.dto.DtoTrackInfo
import net.mortalsilence.indierace.dto.DtoTrackPoint
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

    override fun getSimplifiedTrackPoints(trackId: Long, resolution: Int): List<DtoTrackPoint> {
        val sql = """
            with line as (
                select st_makeline(location order by time) as line
                from trackpoint
                where track_id = $trackId
            )
            select st_x((dp).geom) as longitude,
                   st_y((dp).geom) as latitude,
                   st_z((dp).geom) as elevation,
                   to_timestamp(st_m((dp).geom)) as time,
                   st_distance(cast((dp).geom as geography), last_value(cast((dp).geom as geography)) over w) as distToSuccessor,
                   st_m(last_value((dp).geom) over w) - st_m((dp).geom) as durationToSuccessor                   
            from (
                select st_dumppoints(st_simplify(line.line, st_length(line) / $resolution)) as dp from line) as l
                window w as (order by st_m((dp).geom) rows between 0 preceding and 1 following)
            order by st_m((dp).geom)
            """

        @Suppress("UNCHECKED_CAST")
        return entityManager.createNativeQuery(sql, "TrackPointMapping")
                .resultList as List<DtoTrackPoint>
    }

}