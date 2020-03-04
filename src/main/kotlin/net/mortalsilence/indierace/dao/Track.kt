package net.mortalsilence.indierace.dao

import javax.persistence.*

@Entity
@Table (
        uniqueConstraints = [UniqueConstraint(columnNames = ["name"])]
)
data class Track (
        @Id @GeneratedValue var id: Long? = null,
        var name: String
)