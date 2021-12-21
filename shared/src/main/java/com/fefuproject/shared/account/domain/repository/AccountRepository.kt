package com.fefuproject.shared.account.domain.repository

import com.fefuproject.shared.account.domain.entity.CardEvent
import com.fefuproject.shared.account.domain.entity.CardSummary

interface AccountRepository {
    suspend fun getCardsSummary(
    ): List<CardSummary>

    suspend fun getCardEvents(
        cardNumber: String
    ): List<CardEvent>
}