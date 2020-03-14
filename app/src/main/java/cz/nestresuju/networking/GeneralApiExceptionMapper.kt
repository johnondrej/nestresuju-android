package cz.nestresuju.networking

import com.squareup.moshi.Moshi
import cz.nestresuju.model.errors.*
import cz.nestresuju.model.errors.constants.ErrorResponseCodes
import cz.nestresuju.model.errors.constants.HttpErrorCodes
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Exception mapper for API errors.
 */
class GeneralApiExceptionMapper(private val moshi: Moshi) : ApiExceptionMapper {

    override fun mapException(exception: Throwable) = when (exception) {
        is UnknownHostException, is SocketTimeoutException -> InternetConnectionException()
        is HttpException -> {
            val errorResponse = try {
                exception.response()?.errorBody()?.string()?.let {
                    val moshiAdapter = moshi.adapter(ErrorResponse::class.java)
                    moshiAdapter.fromJson(it)
                }
            } catch (e: Exception) {
                Timber.e(e)
                null
            }

            errorResponse?.let { error ->
                val e = when (exception.code()) {
                    HttpErrorCodes.FORBIDDEN -> when (error.errorCode) {
                        ErrorResponseCodes.RESOURCE_ALREADY_EXISTS -> ResourceAlreadyExistsException()
                        else -> null
                    }
                    else -> null
                }

                e ?: ServerException(exception.code(), error.errorCode, error.text)
            } ?: ServerException(exception.code(), errorCode = null, description = null)
        }
        else -> UnknownException(exception)
    }
}