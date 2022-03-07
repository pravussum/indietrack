package net.mortalsilence.indierace.gpx

import net.mortalsilence.indierace.persistence.entities.Track
import net.mortalsilence.indierace.persistence.repositories.trackpoint.TrackPointRepository
import net.mortalsilence.indierace.persistence.repositories.track.TrackRepository
import net.mortalsilence.indierace.mapper.TrackPointMapper
import java.io.InputStream
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class GpxTrackPersistor(@Inject internal var trackPointRepository: TrackPointRepository,
                        @Inject internal var trackRepository: TrackRepository,
                        @Inject internal var gpxReader: GpxReader,
                        @Inject internal var trackPointMapper: TrackPointMapper) {

    @Transactional
    fun persistGpxTrack(inputStream: InputStream,
                        trackName: String? = null,
                        externalId: String? = null): Track {
        val trackPoints = gpxReader.readGpx(inputStream, trackPointMapper::mapWayPoint2TrackPoint)
        val track = Track(name = trackName ?: UUID.randomUUID().toString(), externalId = externalId)
        trackPoints.peek{
                    if(track.id == null) {
                        track.startTime = it.time
                        trackRepository.save(track)
                    }
                }
                .forEach {it.track = track; trackPointRepository.save(it)}
        // TODO handle empty trackpoints
        return track
    }

}

