package com.example.baeji

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import io.cloudevents.CloudEvent
import io.cloudevents.core.builder.CloudEventBuilder
import io.cloudevents.jackson.PojoCloudEventDataMapper
import java.net.URI


fun main() {
    println("Hello World!")

    val event = CloudEventBuilder.v1()
        .withId("A234-1234-1234")
        .withType("com.example.someevent")
        .withSource(URI.create("http://example.com"))
        .withDataContentType("application/json")
        .withDataSchema(URI.create("http://example.com/schema.json"))
        .withData("{\"id\":\"test-id\", \"type\": \"test-type\"}".toByteArray())
        .build()

    println(event)

    consumerCloudEvents(event)
}

data class MyEvent @JsonCreator constructor(
    @JsonProperty("id") val id: String,
    @JsonProperty("type") val type: String
)

val objectMapper = ObjectMapper()

fun consumerCloudEvents(events: CloudEvent) {
    val cloudEventsData =
        events.data?.let { PojoCloudEventDataMapper.from(objectMapper, MyEvent::class.java).map(it) }?.value

    when (events.type) {
        "com.example.someevent" -> {
            // do something
        }
        // add more cases here
        else -> {
            error("Unknown event type: ${events.type}")
        }
    }

    when (events.dataSchema) {
        URI.create("http://example.com/schema.json") -> {
            // do something
        }
        // add more cases here
        else -> {
            error("Unknown schema: ${events.dataSchema}")
        }
    }

    println(cloudEventsData)

    // do something ...
    println("do something...")
}
