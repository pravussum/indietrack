package net.mortalsilence.indierace.gpx

import io.jenetics.jpx.GPX
import io.jenetics.jpx.Track
import io.jenetics.jpx.TrackSegment
import io.jenetics.jpx.WayPoint
import java.io.InputStream
import javax.enterprise.context.ApplicationScoped
import kotlin.streams.toList

@ApplicationScoped
class GpxReader {

    fun <T> readGpx(inputStream: InputStream, mapFunc: (WayPoint) -> T) : List<T>{
        return GPX.reader(GPX.Version.V10, GPX.Reader.Mode.LENIENT)
                .read(inputStream)
                .tracks()
                .flatMap(Track::segments)
                .flatMap(TrackSegment::points)
                .map{ mapFunc(it) }
                .toList()
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
