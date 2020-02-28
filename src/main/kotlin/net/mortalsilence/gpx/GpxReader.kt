package net.mortalsilence.indieraceserver

import com.vividsolutions.jts.geom.*
import io.jenetics.jpx.GPX
import io.jenetics.jpx.Track
import io.jenetics.jpx.TrackSegment
import io.jenetics.jpx.WayPoint
import net.mortalsilence.indieraceserver.entities.TrackPoint
import net.mortalsilence.indieraceserver.repository.TrackPointRepository
import net.mortalsilence.indieraceserver.repository.TrackRepository
import net.mortalsilence.indieraceserver.repository.TrackSegmentRepository
import org.springframework.stereotype.Service
import javax.inject.Inject
import kotlin.streams.toList

@Service
class GpxReader (@Inject val geometryFactory: GeometryFactory){

    fun readGpx(path: String) : List<LatLngTime>{
        val points = List<Point>()
        GPX.read( path)
                .tracks()
                .flatMap(Track::segments)
                .flatMap(TrackSegment::points)
                .map{ geometryFactory.createPoint(Coordinate(it.latitude.toDouble(), it.longitude.toDouble(), it.elevation.get().toDouble()))}
                .toList()
        return geometryFactory.createMultiPoint();
    }
/*

    fun saveTrack(paths: List<String>) {
        return paths.forEach {
            GPX.read(it).tracks.forEach {
                val track = net.mortalsilence.indieraceserver.entities.Track(name = it.name.get())
                trackRepository.save(track)
                it.segments.forEach {
                    saveSegment(track, it)
                }
            }
        }
    }

    private fun saveSegment(track: net.mortalsilence.indieraceserver.entities.Track, segment: TrackSegment) {
        val trackSegment = net.mortalsilence.indieraceserver.entities.TrackSegment(track = track)
        trackSegmentRepository.save(trackSegment)
        saveTrackPoints(trackSegment, segment.points)
    }

    private fun saveTrackPoints(segment: net.mortalsilence.indieraceserver.entities.TrackSegment, points: List<WayPoint>) {
        points.forEach {
            val trackPoint = TrackPoint(latitude = it.latitude.toDouble(),
                        longitude = it.longitude.toDouble(),
                        time = it.time.get(),
                        elevation = it.elevation.get().toDouble(),
                        trackSegment = segment
                )
            trackPointRepository.save(trackPoint)
        }
    }*/
}