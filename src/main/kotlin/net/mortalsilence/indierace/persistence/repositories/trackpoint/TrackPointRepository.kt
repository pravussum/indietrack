package net.mortalsilence.indierace.persistence.repositories.trackpoint

import net.mortalsilence.indierace.persistence.entities.TrackPoint
import org.springframework.data.repository.CrudRepository

interface TrackPointRepository : CrudRepository<TrackPoint, Long>, CustomTrackPointRepository {
    fun findByTrackIdOrderByTime(id : Long) : List<TrackPoint>
    fun deleteTrackPointsByTrackId(id: Long)
}