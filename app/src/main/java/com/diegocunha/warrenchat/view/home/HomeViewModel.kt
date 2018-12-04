package com.diegocunha.warrenchat.view.home

import android.text.InputType
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegocunha.warrenchat.extensions.mutableLiveDataOf
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
    private val messagesToSent = ArrayList<Message>()
    private val _inputType =
        mutableLiveDataOf(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE or InputType.TYPE_TEXT_FLAG_AUTO_CORRECT)
    val inputyType: LiveData<Int> = _inputType

    override fun onCleared() {
        super.onCleared()
        initialMessageDisposable?.dispose()
        sendMessageDisposable?.dispose()
    }

    init {
        initChat()
    }

    fun sendMessage(message: String) {
        updateHistoric(message)
        val userName = answersHistoric["question_name"].toString()
        val userMessage = Message("string", message, Message.USER, null, userName)
        addMessage(userMessage)
        val bodyMessage = BodyMessage(lastReceivedMessageId, answersHistoric)

        sendMessageDisposable = repository.sendMessage(bodyMessage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess { getMessage(true) }
            .subscribe({ configureMessage(it, Message.BOT) }, { _error.postValue(it.message) })
    }

    fun selectedOption(button: Button) {
        updateHistoric(button.value)
        val bodyMessage = BodyMessage(lastReceivedMessageId, answersHistoric)

        sendMessageDisposable = repository.sendMessage(bodyMessage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess { getMessage(true) }
            .subscribe({ configureMessage(it, Message.BOT) }, { _error.postValue(it.message) })

        val userName = answersHistoric["question_name"].toString()
        val userMessage =
            Message("string", button.label.title, Message.USER, null, userName)
        addMessage(userMessage)
    }

    fun getMessage(isFinished: Boolean) {
        if (messagesToSent.isEmpty()) {
            return
        }

        val messageToSent = messagesToSent[0]
        _answers.postValue(listOf(messageToSent))
        messagesToSent.removeAt(0)

    }

    private fun initChat() {
        initialMessageDisposable = repository.sendMessage(BodyMessage(null, null))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess { getMessage(true) }
            .subscribe({ configureMessage(it, Message.BOT) }, { _error.postValue(it.message) })
    }


    private fun configureMessage(answer: Answer, sent: String) {
        setLastReceivedId(answer.id)
        val messages = ArrayList<Message>()

        if (answer.inputs.isNotEmpty()) {
            val input = answer.inputs[0]

            //textShortMessage|textAutoCorrect|textCapSentences|textMultiLine"
            _inputType.postValue(
                when (input.type) {
                    "number" -> if (input.mask == "currency") InputType.TYPE_NUMBER_FLAG_DECIMAL else InputType.TYPE_CLASS_NUMBER
                    "string" -> InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE or InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
                    else -> InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE or InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
                }
            )
        } else {
            _inputType.postValue(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE or InputType.TYPE_TEXT_FLAG_AUTO_CORRECT)
        }

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
        messagesToSent.addAll(message)
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

