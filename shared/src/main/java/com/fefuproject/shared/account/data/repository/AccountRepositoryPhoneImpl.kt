package com.fefuproject.shared.account.data.repository

import com.fefuproject.shared.account.domain.entity.CardEvent
import com.fefuproject.shared.account.domain.entity.CardSummary
import com.fefuproject.shared.account.domain.repository.AccountRepository

class AccountRepositoryPhoneImpl : AccountRepository {
    override suspend fun getCardsSummary(): List<CardSummary> {
        TODO("Not yet implemented")
    }

    override suspend fun getCardEvents(cardNumber : String): List<CardEvent> {
        TODO("Not yet implemented")
    }
}