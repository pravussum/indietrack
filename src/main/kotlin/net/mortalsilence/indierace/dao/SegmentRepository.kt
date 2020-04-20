package net.mortalsilence.indierace.dao

import org.springframework.data.repository.CrudRepository

interface SegmentRepository : CrudRepository<Segment, Long>, CustomSegmentRepository