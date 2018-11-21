package com.diegocunha.warrenchat.view.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.diegocunha.warrenchat.RxImmediateSchedulerRule
import com.diegocunha.warrenchat.assertLiveDataEquals
import com.diegocunha.warrenchat.model.data.Message
import com.diegocunha.warrenchat.model.fixture.*
import com.diegocunha.warrenchat.model.repository.network.NetworkRepository
import com.diegocunha.warrenchat.model.repository.network.WarrenAPI
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner.Silent::class)
class HomeViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }


    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val api = mock<WarrenAPI>()


    @Before
    fun setup() {
    }

    @Test
    fun shouldStartConversation() {
        val repository = NetworkRepository(api)
        whenever(repository.sendMessage(createInitialBodyMessage)).thenReturn(Single.just(createBotAnswer))
        whenever(api.sendMessage(any())).thenReturn(Single.just(createBotAnswer))
        val answer = PublishSubject.create<List<Message>>()
        val viewModel = HomeViewModel(repository)

        assertLiveDataEquals(listOf(createMessageBot), viewModel.answers, doAfterSubscribe = {
            answer.onNext(listOf(createMessageBot))
        })
    }

    @Test
    fun shouldSendMessageToBot() {
        val repository = NetworkRepository(api)
        whenever(repository.sendMessage(createUserBodyMessage)).thenReturn(Single.just(createBotAnswer))
        whenever(api.sendMessage(any())).thenReturn(Single.just(createBotAnswer))
        val answer = PublishSubject.create<List<Message>>()
        val viewModel = HomeViewModel(repository)

        viewModel.sendMessage("Fulano")

        assertLiveDataEquals(listOf(createMessageBot), viewModel.answers, doAfterSubscribe = {
            answer.onNext(listOf(createMessageBot))
        })
    }
}