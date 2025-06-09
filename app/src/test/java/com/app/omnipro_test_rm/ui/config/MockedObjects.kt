package com.app.omnipro_test_rm.ui.config

import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.domain.models.CharacterStatus
import com.app.omnipro_test_rm.domain.models.Episode
import com.app.omnipro_test_rm.domain.models.Location

object MockedObjects {

    val mockCharacters = listOf(
        CharacterRickAndMorty(
            id = "1",
            name = "Rick Sanchez",
            status = CharacterStatus.ALIVE,
            species = "Human",
            type = "",
            gender = "Male",
            origin = Location(
                id = "1",
                name = "Earth (C-137)",
                type = "Planet",
                dimension = "Dimension C-137"
            ),
            location = Location(
                id = "3",
                name = "Citadel of Ricks",
                type = "Space station",
                dimension = "unknown"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episodes = listOf(
                Episode(id = "1", name = "Pilot", episode = "S01E01", airDate = "December 2, 2013"),
                Episode(id = "2", name = "Lawnmower Dog", episode = "S01E02", airDate = "December 9, 2013")
            ),
            episodeCount = 2,
            created = "2017-11-04T18:48:46.250Z",
            isFavorite = false
        ),
        CharacterRickAndMorty(
            id = "2",
            name = "Morty Smith",
            status = CharacterStatus.ALIVE,
            species = "Human",
            type = "",
            gender = "Male",
            origin = Location(
                id = "1",
                name = "Earth (C-137)",
                type = "Planet",
                dimension = "Dimension C-137"
            ),
            location = Location(
                id = "3",
                name = "Citadel of Ricks",
                type = "Space station",
                dimension = "unknown"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            episodes = listOf(
                Episode(id = "1", name = "Pilot", episode = "S01E01", airDate = "December 2, 2013"),
                Episode(id = "2", name = "Lawnmower Dog", episode = "S01E02", airDate = "December 9, 2013")
            ),
            episodeCount = 2,
            created = "2017-11-04T18:50:21.651Z",
            isFavorite = true
        ),
        CharacterRickAndMorty(
            id = "3",
            name = "Summer Smith",
            status = CharacterStatus.ALIVE,
            species = "Human",
            type = "",
            gender = "Female",
            origin = Location(
                id = "20",
                name = "Earth (Replacement Dimension)",
                type = "Planet",
                dimension = "Replacement Dimension"
            ),
            location = Location(
                id = "20",
                name = "Earth (Replacement Dimension)",
                type = "Planet",
                dimension = "Replacement Dimension"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            episodes = listOf(
                Episode(id = "6", name = "Rick Potion #9", episode = "S01E06", airDate = "January 27, 2014")
            ),
            episodeCount = 1,
            created = "2017-11-04T19:09:56.428Z",
            isFavorite = false
        ),
        CharacterRickAndMorty(
            id = "4",
            name = "Birdperson",
            status = CharacterStatus.DEAD,
            species = "Bird-Person",
            type = "",
            gender = "Male",
            origin = Location(
                id = "15",
                name = "Bird World",
                type = "Planet",
                dimension = "unknown"
            ),
            location = Location(
                id = "15",
                name = "Bird World",
                type = "Planet",
                dimension = "unknown"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/47.jpeg",
            episodes = listOf(
                Episode(id = "11", name = "Ricksy Business", episode = "S01E11", airDate = "April 14, 2014")
            ),
            episodeCount = 1,
            created = "2017-11-05T08:48:30.776Z",
            isFavorite = false
        )
    )
}