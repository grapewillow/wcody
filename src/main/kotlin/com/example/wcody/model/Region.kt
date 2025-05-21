package com.example.wcody.model

import jakarta.persistence.*

@Entity
@Table(name = "regions")
data class Region(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val sido: String,
    val sigungu: String?,
    val dong: String?,

    val nx: Int,
    val ny: Int
)