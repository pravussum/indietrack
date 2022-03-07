package net.mortalsilence.indierace.persistence.repositories.track

import net.mortalsilence.indierace.dto.DtoExternalIdName
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class CustomTrackRepositoryImpl(@Inject @PersistenceContext internal var entityManager: EntityManager) : CustomTrackRepository {
    override fun getExternalIds(): List<DtoExternalIdName> {
        @Suppress("UNCHECKED_CAST")
        return entityManager.createQuery("select externalId, name from Track")
                .resultList as List<DtoExternalIdName>
    }

    override fun getNames(): List<String>  {
        @Suppress("UNCHECKED_CAST")
        return entityManager.createQuery("select name from Track")
            .resultList as List<String>
    }
}