package net.mortalsilence.indierace.dao

import net.mortalsilence.indierace.dto.DtoTrackInfo
import org.locationtech.jts.geom.Point
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@SqlResultSetMapping (
        name = "TrackInfoMapping",
        classes = [
                ConstructorResult(
                        targetClass = DtoTrackInfo::class,
                        columns = [
                            ColumnResult(name="id", type = Long::class),
                            ColumnResult(name="trackName", type = String::class),
                            ColumnResult(name="distance", type = Long::class),
                            ColumnResult(name="boundaries", type = String::class),
                            ColumnResult(name="startTime", type = Date::class),
                            ColumnResult(name="endTime", type = Date::class)
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