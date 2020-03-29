package net.mortalsilence.indierace.dao

import net.mortalsilence.indierace.dto.DtoTrackInfo

interface CustomTrackPointRepository {
    fun getIntersectionAsGeoJson(segmentTrackId: Long, trackId: Long) : String
    fun getTrackPointsAsGeoJson(trackId: Long) : String
    fun getTrackInfo(trackId: Long? = null): List<DtoTrackInfo>
}