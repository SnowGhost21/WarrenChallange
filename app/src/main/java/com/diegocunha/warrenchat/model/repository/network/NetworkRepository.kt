package com.diegocunha.warrenchat.model.repository.network

import com.diegocunha.warrenchat.model.data.Answer
import com.diegocunha.warrenchat.model.data.BodyMessage
import com.diegocunha.warrenchat.model.repository.MessageRepository
import io.reactivex.Single

class NetworkRepository constructor(private val api: WarrenAPI) : MessageRepository {

    override fun sendMessage(body: BodyMessage): Single<Answer> {
        return api.sendMessage(body)
    }
}