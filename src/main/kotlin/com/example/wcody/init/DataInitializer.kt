package com.example.wcody.init

import com.example.wcody.com.example.wcody.repository.RegionRepository
import com.example.wcody.model.Region
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityTransaction
import org.slf4j.LoggerFactory

class DataInitializer(private val regionRepository: RegionRepository) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun initialize() {
        if (regionRepository.count() == 0L) {
            log.info("기본 지역 데이터를 삽입합니다.")
            val inputStream = javaClass.classLoader.getResourceAsStream("data/regions.json")
            val regions: List<Region> = jacksonObjectMapper().readValue(inputStream)
            regionRepository.saveAll(regions)
        } else {
            log.info("이미 지역 데이터가 존재합니다. 초기화하지 않습니다.")
        }
    }
}
