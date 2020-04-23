package net.mortalsilence.indierace.persistence.repositories.track

interface CustomTrackRepository {
    fun getExternalIds(): List<String>
}