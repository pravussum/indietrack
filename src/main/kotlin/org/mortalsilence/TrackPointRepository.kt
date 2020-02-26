package org.mortalsilence

import org.mortalsilence.entities.TrackPoint
import org.springframework.data.repository.CrudRepository

interface TrackPointRepository : CrudRepository<TrackPoint, Long>