package com.fefuproject.shared.account.di

import com.fefuproject.shared.account.data.repository.AccountRepositoryFakeImpl
import com.fefuproject.shared.account.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AccountModule {
    @Binds
    abstract fun bindAccountsRepository(repository: AccountRepositoryFakeImpl): AccountRepository
}
