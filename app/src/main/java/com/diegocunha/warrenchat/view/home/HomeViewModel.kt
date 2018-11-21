package com.diegocunha.warrenchat.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegocunha.warrenchat.model.data.Answer
import com.diegocunha.warrenchat.model.data.BodyMessage
import com.diegocunha.warrenchat.model.data.Button
import com.diegocunha.warrenchat.model.data.Message
import com.diegocunha.warrenchat.model.repository.MessageRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel constructor(private val repository: MessageRepository) : ViewModel() {

    private val _answers = MutableLiveData<List<Message>>()
    val answers: LiveData<List<Message>> = _answers
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val answersHistoric = HashMap<String, Any>()
    private var lastReceivedMessageId: String = ""
    private var initialMessageDisposable: Disposable? = null
    private var sendMessageDisposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        initialMessageDisposable?.dispose()
        sendMessageDisposable?.dispose()
    }

    init {
        initChat()
    }

    private fun initChat() {
        initialMessageDisposable = repository.sendMessage(BodyMessage(null, null))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ configureMessage(it, Message.BOT) }, { _error.postValue(it.message) })
    }

    fun sendMessage(message: String) {
        val userMessage = Message("string", message, Message.USER)
        addMessage(userMessage)
        updateHistoric(message)
        val bodyMessage = BodyMessage(lastReceivedMessageId, answersHistoric)

        sendMessageDisposable = repository.sendMessage(bodyMessage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ configureMessage(it, Message.BOT) }, { _error.postValue(it.message) })
    }

    fun selectedOption(button: Button) {
        updateHistoric(button.value)
        val bodyMessage = BodyMessage(lastReceivedMessageId, answersHistoric)

        sendMessageDisposable = repository.sendMessage(bodyMessage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ configureMessage(it, Message.BOT) }, { _error.postValue(it.message) })

        val userMessage = Message("string", button.label.title, Message.USER)
        addMessage(userMessage)
    }

    private fun configureMessage(answer: Answer, sent: String) {
        setLastReceivedId(answer.id)
        val messages = ArrayList<Message>()

        answer.messages.forEachIndexed { position, message ->
            message.sent = sent

            if (position == answer.messages.lastIndex) {
                message.buttons = answer.buttons
            }

            messages.add(cleanMessage(message))
        }

        addMessage(messages)
    }

    private fun setLastReceivedId(id: String) {
        lastReceivedMessageId = id
    }

    private fun updateHistoric(message: String) {
        answersHistoric[lastReceivedMessageId] = message
    }

    private fun addMessage(message: List<Message>) {
        _answers.postValue(message)
    }

    private fun addMessage(message: Message) {
        _answers.postValue(listOf(message))
    }

    private fun cleanMessage(message: Message): Message {
        val messageArray = message.message.split(" ")
        messageArray.forEach { value ->
            if (value.contains("^")) {
                message.message = message.message.replace(value, "").replace("\\s+".toRegex(), " ")
            }
        }

        return message

    }
}

