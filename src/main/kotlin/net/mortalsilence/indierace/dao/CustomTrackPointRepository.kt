package net.mortalsilence.indierace.dao

interface CustomTrackPointRepository {
    fun getIntersectionAsGeoJson(segmentTrackId: Long, trackId: Long) : String
    fun getTrackPointsAsGeoJson(trackId: Long) : String
}