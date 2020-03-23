package net.mortalsilence.indierace.gpx

import net.mortalsilence.indierace.dao.Track
import net.mortalsilence.indierace.dao.TrackPointRepository
import net.mortalsilence.indierace.dao.TrackRepository
import net.mortalsilence.indierace.mapper.TrackPointMapper
import java.io.InputStream
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class GpxTrackPersistor(@Inject internal val trackPointRepository: TrackPointRepository,
                        @Inject internal val trackRepository: TrackRepository,
                        @Inject internal val gpxReader: GpxReader,
                        @Inject internal val trackPointMapper: TrackPointMapper) {

    @Transactional
    fun persistGpxTrack(inputStream: InputStream,
                        trackName: String? = null,
                        externalId: String? = null): Track {
        val trackPoints = gpxReader.readGpx(inputStream, trackPointMapper::mapWayPoint2TrackPoint)
        val track = Track(name = trackName?:UUID.randomUUID().toString(), externalId = externalId)
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

