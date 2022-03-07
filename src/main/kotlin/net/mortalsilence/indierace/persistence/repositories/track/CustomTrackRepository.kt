package net.mortalsilence.indierace.persistence.repositories.track

import net.mortalsilence.indierace.dto.DtoExternalIdName

interface CustomTrackRepository {
    fun getExternalIds(): List<DtoExternalIdName>
    fun getNames(): List<String>
}