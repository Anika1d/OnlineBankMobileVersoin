package com.fefuproject.shared.account.data.repository

import com.fefuproject.shared.account.domain.models.*
import com.fefuproject.shared.account.domain.repository.AccountRepository
import java.text.DecimalFormat
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class AccountRepositoryFakeImpl @Inject constructor() : AccountRepository {
    override suspend fun getCards(token: String): List<CardModel> {
        val cardSummaries = mutableListOf<CardModel>()
        val smallest = 1000000000000000L
        val biggest = 9999999999999999L
        repeat(5) {
            cardSummaries.add(
                CardModel(
                    0,
                    0,
                    "",
                    ThreadLocalRandom.current().nextLong(smallest, biggest + 1).toString().substringBefore('.'),
                    null,
                    ThreadLocalRandom.current().nextDouble(
                        0.toDouble(), 100000.toDouble()
                    ).toString().substringBefore('.')+'р',
                    false,
                    ""
                )
            )
        }
        return cardSummaries
    }

    override suspend fun getCheckHistory(
        number: String,
        token: String
    ): List<HistoryInstrumentModel> {
        val cardEvents = mutableListOf<HistoryInstrumentModel>()
        repeat(5) {
            val randomSecond = ThreadLocalRandom
                .current()
                .nextLong(
                    Instant.now().minus(Duration.ofDays(10)).epochSecond,
                    Instant.now().epochSecond
                )
            cardEvents.add(
                HistoryInstrumentModel(
                    0, 0, 0,
                    ThreadLocalRandom.current().nextDouble(0.toDouble(), 5000.toDouble())
                        .toString().substringBefore('.')+'р',
                    Date.from(Instant.ofEpochSecond(randomSecond)),
                )
            )
        }
        return cardEvents
    }

    override suspend fun getAllHistory(
        token: String,
        operationCount: Int
    ): List<HistoryInstrumentModel> {
        TODO("Not yet implemented")
    }
    override suspend fun getBankomats(): List<BankomatModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getValute(): ValuteModel {
        TODO("Not yet implemented")
    }

    override suspend fun getChecks(token: String): List<CheckModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getCredits(token: String): List<CreditModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getCardHistory(
        number: String,
        token: String
    ): List<HistoryInstrumentModel> {
        TODO("Not yet implemented")
    }

    override suspend fun blockCard(number: String, token: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun refillCard(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun PayCheck(
        cardSource: String,
        cardDest: String,
        sum: Double,
        token: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun GetCategory(): List<CategoryModel> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(name: String, username: String, password: String): String? {
        TODO("Not yet implemented")
    }

    override suspend fun logIn(username: String, password: String): UserModel {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(token: String): UserModel {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(
        old_password: String,
        new_password: String,
        token: String
    ): String? {
        TODO("Not yet implemented")
    }

    override suspend fun changeUsername(username: String, token: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getLoginHistory(token: String): List<LoginHistoryModel> {
        TODO("Not yet implemented")
    }
}