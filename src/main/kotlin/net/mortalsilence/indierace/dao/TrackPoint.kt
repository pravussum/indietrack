package net.mortalsilence.indierace.dao

import net.mortalsilence.indierace.dto.DtoTrackPoint
import org.locationtech.jts.geom.Point
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "trackpoint")
@SqlResultSetMapping (
        name = "TrackPointMapping",
        classes = [
                ConstructorResult(
                        targetClass = DtoTrackPoint::class,
                        columns = [
                            ColumnResult(name="latitude", type = Double::class),
                            ColumnResult(name="longitude", type = Double::class),
                            ColumnResult(name="elevation", type = Double::class),
                            ColumnResult(name="time", type = Date::class),
                            ColumnResult(name="distToSuccessor", type = Double::class),
                            ColumnResult(name="durationToSuccessor", type = Double::class)
                        ]
                )
        ]
)
data class TrackPoint(
        @Id @GeneratedValue var id: Long? = null,
        var location : Point,
        var time : ZonedDateTime,
        @ManyToOne(cascade = [])
        var track: Track? = null
)