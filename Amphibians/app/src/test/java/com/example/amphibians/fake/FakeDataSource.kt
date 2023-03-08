package com.example.amphibians.fake

import com.example.amphibians.model.Amphibian

object FakeDataSource {
    val amphibiansList = listOf(
        Amphibian(
            "toad1",
            "toad",
            "toad 1",
            "url 1"
        ),
        Amphibian(
            "toad2",
            "toad",
            "toad 2",
            "url 2"
        )
    )
}