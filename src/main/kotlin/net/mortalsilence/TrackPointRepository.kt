package net.mortalsilence

import net.mortalsilence.entities.TrackPoint
import org.springframework.data.repository.CrudRepository

interface TrackPointRepository : CrudRepository<TrackPoint, Long>