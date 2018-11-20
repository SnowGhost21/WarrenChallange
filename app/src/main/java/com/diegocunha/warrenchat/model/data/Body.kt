package com.diegocunha.warrenchat.model.data

data class BodyMessage(
    var id: String?,
    var answers: HashMap<String, Any>?,
    var context: String? = "suitability"
)