package com.example.wcody.repository

import com.example.wcody.com.example.wcody.dto.WeatherData
import com.example.wcody.model.User
import jakarta.persistence.EntityManager

class UserRepository(private val em: EntityManager) {

    fun save(user: User) {
        em.transaction.begin()
        em.persist(user)
        em.transaction.commit()
    }

    fun existsByUsername(username: String): Boolean {
        val query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username")
        query.setParameter("username", username)
        val count = query.singleResult as Long
        return count > 0
    }

    fun findByUsername(username: String): User? {
        val query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User::class.java)
        query.setParameter("username", username)
        return query.resultList.firstOrNull()
    }

    fun findById(userId: Long): User? {
        val query = em.createQuery("SELECT u FROM User u WHERE u.id = :userId", User::class.java)
        query.setParameter("userId", userId)
        return query.resultList.firstOrNull()
    }

    fun findByEmail(email: String): User? {
        return em.createQuery(
            "SELECT u FROM User u WHERE u.email = :email", User::class.java
        ).setParameter("email", email)
            .resultList
            .firstOrNull()
    }
}


