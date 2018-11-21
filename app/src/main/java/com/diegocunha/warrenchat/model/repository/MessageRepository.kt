package com.diegocunha.warrenchat.model.repository

import com.diegocunha.warrenchat.model.data.Answer
import com.diegocunha.warrenchat.model.data.BodyMessage
import io.reactivex.Single

interface MessageRepository {

    fun sendMessage(body: BodyMessage): Single<Answer>
}