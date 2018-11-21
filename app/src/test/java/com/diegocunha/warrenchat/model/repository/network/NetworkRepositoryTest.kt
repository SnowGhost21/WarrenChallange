package com.diegocunha.warrenchat.model.repository.network

import com.diegocunha.warrenchat.model.fixture.createBody
import com.diegocunha.warrenchat.model.fixture.createBotAnswer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkRepositoryTest {

    private val api = mock<WarrenAPI>()

    @Before
    fun setup() {
        whenever(api.sendMessage(any())).thenReturn(Single.just(createBotAnswer))
    }

    @Test
    fun shouldGetAnswerFromApi() {
        val repository = NetworkRepository(api)
        repository.sendMessage(createBody)

        verify(api).sendMessage(createBody)
    }
}