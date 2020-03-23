package net.mortalsilence.indierace.gpx

import io.jenetics.jpx.GPX
import io.jenetics.jpx.Track
import io.jenetics.jpx.TrackSegment
import io.jenetics.jpx.WayPoint
import java.io.InputStream
import java.util.stream.Stream
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GpxReader {

    fun <T> readGpx(inputStream: InputStream, mapFunc: (WayPoint) -> T) : Stream<T> {
        return GPX.reader(GPX.Version.V10, GPX.Reader.Mode.LENIENT)
                .read(inputStream)
                .tracks()
                .flatMap(Track::segments)
                .flatMap(TrackSegment::points)
                .map{ mapFunc(it) }

    }
}
