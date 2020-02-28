package net.mortalsilence.entities

import com.vividsolutions.jts.geom.MultiPoint
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class Track (
        @Id @GeneratedValue val id: Long,
        val track: MultiPoint
)