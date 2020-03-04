package net.mortalsilence.indierace.dao

import org.springframework.data.repository.CrudRepository

interface TrackPointRepository : CrudRepository<TrackPoint, Long>, CustomTrackPointRepository {
    fun findByTrackId(id : Long) : List<TrackPoint>
}