package net.mortalsilence.indierace.persistence.repositories.track

import net.mortalsilence.indierace.persistence.entities.Track
import org.springframework.data.repository.CrudRepository
import java.util.*

interface TrackRepository : CrudRepository<Track, Long>, CustomTrackRepository {
    fun findByName(name: String): Optional<Track>
}