package com.diegocunha.warrenchat.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegocunha.warrenchat.model.data.Answer
import com.diegocunha.warrenchat.model.data.BodyMessage
import com.diegocunha.warrenchat.model.data.Message
import com.diegocunha.warrenchat.model.repository.MessageRepository
import iwsbrazil.io.artmuseum.extensions.asLiveData

class HomeViewModel constructor(private val repository: MessageRepository) : ViewModel() {

    private val _userAnswer = MutableLiveData<Answer>()
    val userAnswer: LiveData<Answer> = _userAnswer
    private val answers = HashMap<String, Any>()
    val initialMessage = repository.getInitialMessage().asLiveData()

    fun sendMessage(message: String): LiveData<Answer> {
        updateUserAnswer(message)

        answers["question_name"] = message
        val bodyMessage = BodyMessage("question_name", answers)
        return repository.sendMessage(bodyMessage).asLiveData()
    }

    private fun updateUserAnswer(message: String) {
        val answer = Answer("user", arrayListOf(Message(message, "String")))
        _userAnswer.postValue(answer)
    }
}