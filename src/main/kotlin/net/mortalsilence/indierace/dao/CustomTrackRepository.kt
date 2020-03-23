package net.mortalsilence.indierace.dao

interface CustomTrackRepository {
    fun getExternalIds(): List<String>
}