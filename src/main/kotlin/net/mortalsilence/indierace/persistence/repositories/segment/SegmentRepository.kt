package net.mortalsilence.indierace.persistence.repositories.segment

import net.mortalsilence.indierace.persistence.entities.Segment
import org.springframework.data.repository.CrudRepository

interface SegmentRepository : CrudRepository<Segment, Long>, CustomSegmentRepository