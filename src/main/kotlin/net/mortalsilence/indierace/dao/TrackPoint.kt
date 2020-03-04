package net.mortalsilence.indierace.dao

import org.locationtech.jts.geom.Point
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
data class TrackPoint(
        @Id @GeneratedValue var id: Long? = null,
        var location : Point,
        var time : ZonedDateTime,
        @ManyToOne(cascade = [])
        var track: Track? = null
)