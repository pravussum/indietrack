package net.mortalsilence.indierace.dao

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table (
        uniqueConstraints = [UniqueConstraint(columnNames = ["name"])]
)
data class Track (
        @Id @GeneratedValue var id: Long? = null,
        var name: String,
        var externalId: String? = null,
        var startTime: ZonedDateTime? = null
)