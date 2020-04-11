package net.mortalsilence.indierace.dao

import net.mortalsilence.indierace.dto.DtoTrackInfo
import net.mortalsilence.indierace.dto.DtoTrackPoint

interface CustomTrackPointRepository {
    fun getIntersectionAsGeoJson(segmentTrackId: Long, trackId: Long) : String
    fun getTrackPointsAsGeoJson(trackId: Long) : String
    fun getTrackInfo(trackId: Long? = null): List<DtoTrackInfo>
    fun getSimplifiedTrackPoints(trackId: Long, resolution: Int = 1000): List<DtoTrackPoint>
}