package cz.nestresuju.networking

import cz.ackee.retrofitadapter.chain.CallChain
import cz.ackee.retrofitadapter.interceptor.CallFactoryInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * Retrofit interceptor for API error mapping.
 */
class ErrorMappingInterceptor(private val exceptionMapper: ApiExceptionMapper) : CallFactoryInterceptor {

    @Suppress("UNCHECKED_CAST")
    override fun intercept(chain: CallChain): Call<*> {
        return chain.proceed(CallWithErrorHandling(chain.call as Call<Any>, exceptionMapper))
    }
}

class CallWithErrorHandling(
    private val delegate: Call<Any>,
    private val exceptionMapper: ApiExceptionMapper
) : Call<Any> by delegate {

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    callback.onFailure(call, exceptionMapper.mapException(HttpException(response)))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onFailure(call, exceptionMapper.mapException(t))
            }
        })
    }

    override fun clone() = CallWithErrorHandling(delegate.clone(), exceptionMapper)
}