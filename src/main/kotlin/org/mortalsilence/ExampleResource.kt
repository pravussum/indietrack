package org.mortalsilence

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import org.mortalsilence.entities.TrackPoint
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/trackpoint")
@Produces(MediaType.APPLICATION_JSON)
class ExampleResource(@Inject val em: EntityManager,
                      @Inject val geometryFactory: GeometryFactory,
                      @Inject val trackPointRepository: TrackPointRepository) {


    @GET
    @Path("/add")
    @Transactional
    fun createTrackPoint() {
        em.persist(TrackPoint(location = geometryFactory.createPoint(Coordinate(123.0, 345.0))))
        return em.persist(TrackPoint(location = geometryFactory.createPoint(Coordinate(124.0, 345.0))))
    }

    @GET
    @Path("/{id}")
    fun getTrackPoint(@PathParam("id") id: Long) : Optional<TrackPoint> {
        return trackPointRepository.findById(id)
    }

    @GET
    @Path("/distance")
    fun getDistance(): Double {
        val trackPoints = trackPointRepository.findAll()
        val iterator = trackPoints.iterator()
        val first = iterator.next()
        val second = iterator.next()
        return first.location.distance(second.location)
    }
}