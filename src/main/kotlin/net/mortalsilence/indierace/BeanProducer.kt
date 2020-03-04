package net.mortalsilence.indierace

import org.locationtech.jts.geom.GeometryFactory
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

@ApplicationScoped
class BeanProducer {

    @Produces
    fun getGeometryFactory(): GeometryFactory {
        return GeometryFactory()
    }
}