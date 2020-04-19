package net.mortalsilence.indierace.dao

import org.locationtech.jts.geom.LineString
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "segment")
data class Segment (
    @Id @GeneratedValue val id: Long? = null,
    var name: String,
    var points: LineString
)