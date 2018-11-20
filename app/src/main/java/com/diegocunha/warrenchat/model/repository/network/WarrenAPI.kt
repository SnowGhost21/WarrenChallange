package com.diegocunha.warrenchat.model.repository.network

import com.diegocunha.warrenchat.model.data.Answer
import com.diegocunha.warrenchat.model.data.BodyMessage
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface WarrenAPI {


    @POST("conversation/message")
    fun sendMessage(@Body message: BodyMessage): Single<Answer>
}