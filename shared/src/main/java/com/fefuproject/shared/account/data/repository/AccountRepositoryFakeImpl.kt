package com.fefuproject.shared.account.data.repository

import com.fefuproject.shared.account.domain.entity.CardEvent
import com.fefuproject.shared.account.domain.entity.CardSummary
import com.fefuproject.shared.account.domain.repository.AccountRepository
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class AccountRepositoryFakeImpl @Inject constructor() : AccountRepository {
    override suspend fun getCardsSummary(): List<CardSummary> {
        val cardSummaries = mutableListOf<CardSummary>()
        val smallest = 1000000000000000L
        val biggest = 9999999999999999L
        repeat(5) {
            cardSummaries.add(
                CardSummary(
                    ThreadLocalRandom.current().nextLong(smallest, biggest + 1).toString(),
                    ThreadLocalRandom.current().nextDouble(0.toDouble(), 100000.toDouble())
                )
            )
        }
        return cardSummaries
    }

    override suspend fun getCardEvents(cardNumber : String): List<CardEvent> {
        val cardEvents = mutableListOf<CardEvent>()
        repeat(5) {
            val randomSecond = ThreadLocalRandom
                .current()
                .nextLong(
                    Instant.now().minus(Duration.ofDays(10)).epochSecond,
                    Instant.now().epochSecond
                )
            cardEvents.add(
                CardEvent(
                    "Перевод на карту",
                    ThreadLocalRandom.current().nextDouble(0.toDouble(), 5000.toDouble()),
                    Date.from(Instant.ofEpochSecond(randomSecond)),
                )
            )
        }
        return cardEvents
    }
}