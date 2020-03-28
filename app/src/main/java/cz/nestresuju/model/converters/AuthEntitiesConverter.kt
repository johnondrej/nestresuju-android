package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.auth.LoginChecklistResponse
import cz.nestresuju.model.entities.domain.auth.LoginChecklistCompletion

/**
 * Entities converter for auth related classes.
 */
interface AuthEntitiesConverter {

    fun apiLoginChecklistToDomain(apiLoginChecklist: LoginChecklistResponse): LoginChecklistCompletion
}

class AuthEntitiesConverterImpl : AuthEntitiesConverter {

    override fun apiLoginChecklistToDomain(apiLoginChecklist: LoginChecklistResponse): LoginChecklistCompletion {
        return LoginChecklistCompletion(
            apiLoginChecklist.constentGiven, apiLoginChecklist.inputTestSubmitted, apiLoginChecklist.screeningTestSubmitted
        )
    }
}