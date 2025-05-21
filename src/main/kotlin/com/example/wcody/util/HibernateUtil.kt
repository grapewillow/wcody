package com.example.wcody.util

import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence

object HibernateUtil {
    val entityManagerFactory: EntityManagerFactory =
        Persistence.createEntityManagerFactory("default") // persistence.xml 기반
}