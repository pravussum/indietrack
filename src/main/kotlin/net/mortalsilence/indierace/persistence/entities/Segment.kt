package net.mortalsilence.indierace.persistence.entities

import net.mortalsilence.indierace.dto.DtoAttempt
import net.mortalsilence.indierace.dto.DtoSegmentGeoJson
import org.locationtech.jts.geom.LineString
import javax.persistence.*

@Entity
@Table(name = "segment")
@SqlResultSetMappings(value = [
    SqlResultSetMapping(
        name = "AttemptMapping",
        classes = [
            ConstructorResult(
                    targetClass = DtoAttempt::class,
                    columns = [
                        ColumnResult(name="segmentId", type = Long::class),
                        ColumnResult(name="trackId", type = Long::class),
                        ColumnResult(name="length", type = String::class),
                        ColumnResult(name="durationInSec", type = Long::class),
                        ColumnResult(name="segmentName", type = String::class)
                    ]
            )
        ]
    ),
    SqlResultSetMapping(
        name = "SegmentAsGeoJsonMapping",
        classes = [
            ConstructorResult(
                    targetClass = DtoSegmentGeoJson::class,
                    columns = [
                        ColumnResult(name="segmentId", type = Long::class),
                        ColumnResult(name="geoJson", type = String::class)
                    ]
            )
        ]
    )
])
data class Segment (
    @Id @GeneratedValue val id: Long? = null,
    var name: String,
    var points: LineString
)