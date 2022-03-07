package net.mortalsilence.indierace

import net.mortalsilence.indierace.persistence.entities.TrackPoint
import net.mortalsilence.indierace.mapper.TrackPointMapper
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import kotlin.math.ceil

@ApplicationScoped
class TrackPointDownsampler(@Inject internal var trackPointMapper: TrackPointMapper) {

     fun <T> downSampleWithTargetSize(input : List<TrackPoint>, bucketCount: Int, aggFunc : (List<TrackPoint>) -> T): List<T> {
         val bucketSize = ceil(input.size.toDouble() / bucketCount.toDouble()).toInt()
         var i = 0
        input.first().location.userData
         return input
                 .asSequence()
                 .groupBy { ++i / bucketSize }
                 .flatMap { listOf(aggFunc.invoke(it.value)) }
    }

//    fun aggregateWithDistanceSum(trackPoints: List<TrackPoint>): LatLngTimeEle {
//        val result = trackPointMapper.mapTrackpoint2LatLong(trackPoints.first())
//        result.distToSuccessor =
//                trackPoints
//                .asSequence()
//                .map {  }
//                .sumByDouble { it.distToSuccessor ?: 0.0 }
//
//    }
}