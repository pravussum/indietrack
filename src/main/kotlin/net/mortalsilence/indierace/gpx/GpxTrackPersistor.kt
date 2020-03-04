package net.mortalsilence.indierace.gpx

import net.mortalsilence.indierace.dao.Track
import net.mortalsilence.indierace.dao.TrackPointRepository
import net.mortalsilence.indierace.dao.TrackRepository
import net.mortalsilence.indierace.mapper.TrackPointMapper
import java.io.InputStream
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class GpxTrackPersistor(@Inject internal val trackPointRepository: TrackPointRepository,
                        @Inject internal val trackRepository: TrackRepository,
                        @Inject internal val gpxReader: GpxReader,
                        @Inject internal val trackPointMapper: TrackPointMapper) {

    fun persistGpxTrack(inputStream: InputStream): Track {
        val trackPoints = gpxReader.readGpx(inputStream, trackPointMapper::mapWayPoint2TrackPoint)
        val track = Track(name = UUID.randomUUID().toString())
        trackRepository.save(track)
        trackPoints.forEach {it.track = track; trackPointRepository.save(it)}
        return track
    }

}

