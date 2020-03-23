package net.mortalsilence.indierace.dao

import org.springframework.data.repository.CrudRepository
import java.util.*

interface TrackRepository : CrudRepository<Track, Long>, CustomTrackRepository {
    fun findByName(name: String): Optional<Track>
}