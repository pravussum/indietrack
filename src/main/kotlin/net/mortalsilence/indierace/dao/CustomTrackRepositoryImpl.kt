package net.mortalsilence.indierace.dao

import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class CustomTrackRepositoryImpl(@Inject @PersistenceContext internal val entityManager: EntityManager) : CustomTrackRepository {
    override fun getExternalIds(): List<String> {
        @Suppress("UNCHECKED_CAST")
        return entityManager.createQuery("select externalId from Track")
                .resultList as List<String>
    }
}