package com.fefuproject.shared.account.di

import com.fefuproject.shared.account.data.api.AccountApi
import com.fefuproject.shared.account.data.repository.AccountRepositoryFakeImpl
import com.fefuproject.shared.account.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountModule {
    @Binds
    abstract fun bindAccountsRepository(repository: AccountRepositoryFakeImpl): AccountRepository
}


@Module
@InstallIn(SingletonComponent::class)
interface AccountApiModule {
    @Provides
    @Singleton
    fun provideApiModule(retrofit: Retrofit): AccountApi = retrofit.create(AccountApi::class.java)
}