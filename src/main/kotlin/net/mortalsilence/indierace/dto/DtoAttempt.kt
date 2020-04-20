package net.mortalsilence.indierace.dto

data class DtoAttempt (
        val segmentId: Long,
        val trackId: Long?,
        val length: String?,
        val durationInSec: Long,
        val segmentName: String?
)