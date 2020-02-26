package org.mortalsilence

import com.vividsolutions.jts.geom.GeometryFactory
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

@ApplicationScoped
class BeanProducer {

    @Produces
    fun getGeometryFactory(): GeometryFactory {
        return GeometryFactory()
    }
}