package com.fefuproject.druzhbank.dirprofile.dircard

import com.github.javafaker.Faker
typealias CardsListener = (cards: List<Cards>) -> Unit

class CardService {
    private var cards = mutableListOf<Cards>()
    private var listeners = mutableListOf<CardsListener>()

    init {
        val faker = Faker.instance()
        cards = (5..25).map {
            Cards(
                numberCard = "1234****4321",
                valueCard = faker.number().numberBetween(125, 1000).toString() + " рублей",
                whatBank = "mir",
                typeCard = "Дебетовая"
                , isBlocked = false


            )
        }.toMutableList()

    }
    fun addListener(listener:CardsListener) {
        listeners.add(listener)
        listener.invoke(cards)
    }

    fun removeListener(listener: CardsListener) {
        listeners.remove(listener)
    }

}
