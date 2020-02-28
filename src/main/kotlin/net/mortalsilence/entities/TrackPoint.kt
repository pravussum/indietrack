package net.mortalsilence.entities

import com.vividsolutions.jts.geom.Point
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class TrackPoint(
        @Id @GeneratedValue val id: Long? = null,
        val location : Point
)