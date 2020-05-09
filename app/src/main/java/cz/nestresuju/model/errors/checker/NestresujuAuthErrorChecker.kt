package cz.nestresuju.model.errors.checker

import cz.ackee.ackroutine.core.ErrorChecker
import cz.nestresuju.model.errors.ServerException
import retrofit2.HttpException
import java.net.HttpURLConnection

/**
 * Error checker used for handling of API auth errors.
 */
class NestresujuAuthErrorChecker : ErrorChecker {

    override fun invalidAccessToken(t: Throwable): Boolean {
        if (t is HttpException) {
            if (t.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                return true
            }
        }
        return false
    }

    override fun invalidRefreshToken(t: Throwable): Boolean {
        val httpCode = (t as? HttpException)?.code() ?: (t as? ServerException)?.httpCode

        return when (httpCode) {
            HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_UNAUTHORIZED -> true
            else -> false
        }
    }
}