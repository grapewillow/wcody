package com.example.wcody.com.example.wcody.repository

import com.example.wcody.model.Region
import jakarta.persistence.EntityManager

class RegionRepository(private val em: EntityManager) {
    fun save(region: Region) {
        em.transaction.begin()
        em.persist(region)
        em.transaction.commit()
    }

    fun findById(id: Long?): Region? {
        return em.find(Region::class.java, id)
    }

    fun findAll(): List<Region> {
        val query = em.createQuery("SELECT r FROM Region r", Region::class.java)
        return query.resultList
    }

    fun delete(region: Region) {
        em.transaction.begin()
        em.remove(region)
        em.transaction.commit()
    }

    fun findByLocation(sido: String, sigungu: String?, dong: String?): Region? {
        val query = em.createQuery(
            "SELECT r FROM Region r WHERE r.sido = :sido AND (:sigungu IS NULL OR r.sigungu = :sigungu) AND (:dong IS NULL OR r.dong = :dong)",
            Region::class.java
        )
        query.setParameter("sido", sido)
        query.setParameter("sigungu", sigungu)
        query.setParameter("dong", dong)
        return query.resultList.firstOrNull()
    }

    fun count(): Long {
        return em.createQuery("SELECT COUNT(r) FROM Region r", Long::class.java).singleResult
    }

    fun saveAll(regions: List<Region>) {
        em.transaction.begin()
        regions.forEach { em.persist(it) }
        em.transaction.commit()
    }
}