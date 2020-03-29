package net.mortalsilence.indierace.dto

import java.util.*

data class DtoTrackInfo (
        var id: Long,
        var name: String,
        var distance: Long,
        var boundaries: String,
        var startTime: Date,
        var endTime: Date
)