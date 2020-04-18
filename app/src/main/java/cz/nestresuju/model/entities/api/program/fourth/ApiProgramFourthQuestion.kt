package cz.nestresuju.model.entities.api.program.fourth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ApiProgramFourthQuestion(
    val id: Long,
    val type: Int,
    val text: String
)