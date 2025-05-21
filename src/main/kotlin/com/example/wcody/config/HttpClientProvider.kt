package com.example.wcody.config

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import io.ktor.http.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object HttpClientProvider {
    val client: HttpClient by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                register(ContentType.Application.Xml, JacksonConverter(
                    XmlMapper()
                        .registerKotlinModule()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                ))
            }
        }
    }
}