package net.mortalsilence.indierace.rest

import net.mortalsilence.indierace.dao.Track
import net.mortalsilence.indierace.dao.TrackPointRepository
import net.mortalsilence.indierace.dao.TrackRepository
import net.mortalsilence.indierace.mapper.TrackPointMapper
import java.time.ZonedDateTime
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/track")
@Produces(MediaType.APPLICATION_JSON)
class TrackPointRestController (@Inject internal val trackRepository: TrackRepository,
                                @Inject internal val trackPointRepository: TrackPointRepository,
                                @Inject internal val trackPointMapper: TrackPointMapper) {

    @POST
    @Path("/trackpoints/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    fun addTrackPointToTrackByName(trackPointInfo: TrackPointInfo) {
        var track : Optional<Track> = trackRepository.findByName(trackPointInfo.track);
        if(!track.isPresent) {
            track = Optional.of(createTrack(trackPointInfo.track))
        }
        val trackPoint = trackPointMapper.mapLatLongEleTimeToTrackPoint(
                trackPointInfo.longitude,
                trackPointInfo.lat,
                trackPointInfo.elevation,
                trackPointInfo.time)
        trackPoint.track = track.get()
        trackPointRepository.save(trackPoint)
    }

    private fun createTrack(trackName: String): Track {
        val track = Track(name = trackName)
        trackRepository.save(track)
        return track
    }

    data class TrackPointInfo(
            val lat: Double,
            val longitude: Double,
            val elevation: Double,
            val time: ZonedDateTime,
            val track: String
    )
}