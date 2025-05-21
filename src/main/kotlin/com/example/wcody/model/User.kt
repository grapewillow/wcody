package com.example.wcody.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val nickname: String,

    @Column(nullable = true)
    val email: String,

    @Column(nullable = false)
    val birthYear: String,

    @Column(nullable = false)
    val birthMonth: String,

    @Column(nullable = false)
    val birthDay: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    val region: Region,

    @Column(nullable = false)
    val gender: String,

    @Column(nullable = false)
    val sensitivity: Int
)
