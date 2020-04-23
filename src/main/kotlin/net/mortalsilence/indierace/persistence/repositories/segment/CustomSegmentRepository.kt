package net.mortalsilence.indierace.persistence.repositories.segment

import net.mortalsilence.indierace.dto.DtoAttempt
import net.mortalsilence.indierace.dto.DtoLatLng
import net.mortalsilence.indierace.dto.DtoSegmentGeoJson

interface CustomSegmentRepository {
    fun getAttemptsForTrack(trackId: Long): List<DtoAttempt>
    fun getBufferedSegmentsInBoundingBoxAsGeoJson(lowerLeft: DtoLatLng, upperRight: DtoLatLng, buffered: Boolean?): List<DtoSegmentGeoJson>
}