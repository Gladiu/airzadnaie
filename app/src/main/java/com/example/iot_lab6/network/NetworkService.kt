package com.example.iot_lab6.network

import android.content.Context
import android.util.Log
import com.example.iot_lab6.definitions.toast
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.net.SocketTimeoutException
import kotlin.reflect.KSuspendFunction0
import kotlin.reflect.KSuspendFunction1


class NetworkService(
    var retrofit: Retrofit,
    var malinaAPI: MalinaAPI
) {
    companion object {
        private var instance: NetworkService? = null
        private val default_url = "http://192.168.37.18"

        @OptIn(ExperimentalSerializationApi::class)
        fun getInstance(BASE_URL: String?=null): NetworkService {
            if(instance == null) {
                var temp_url = BASE_URL
                if(temp_url == null) {
                    temp_url = default_url
                    Log.w("NetworkService","Setting NetworkService instance to default base url $temp_url")
                }
                val retrofit = Retrofit.Builder()
                    .baseUrl(temp_url)
                    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
                    .build()
                val malinaAPI = retrofit!!.create(MalinaAPI::class.java)
                instance = NetworkService(retrofit,malinaAPI)
            }
            return instance!!
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun changeBaseUrl(BASE_URL: String) {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
        malinaAPI = retrofit.create(MalinaAPI::class.java)
    }

    suspend inline fun <reified T,Y>launchRequest(req: T, f: KSuspendFunction1<T, Response<Y>>, context: Context): Y? {
        try {
            val response = f(req)
            if (response.isSuccessful) {
                return response.body()
            } else {
                context.toast("Error: ${response.code()}")
            }
        } catch (e: HttpException) {
            context.toast("HttpException ${e.message}")
            Log.e("NetworkService ${T::class.simpleName}", "HttpException ${e.message}")
        } catch (e: SocketTimeoutException) {
            context.toast("Timeout to ${retrofit.baseUrl()}:80")
            Log.e("NetworkService ${T::class.simpleName}", "Timeout to ${retrofit.baseUrl()}:80")
        } catch (e: Throwable) {
            context.toast("Throwable: ${e.message}")
            Log.e("NetworkService ${T::class.simpleName}", "Throwable: ${e.message}")
        } catch (e: Exception) {
            context.toast("Exception ${e.message}")
            Log.e("NetworkService ${T::class.simpleName}", "Exception ${e.message}")
        }

        //context.toast("IsNull")
        //Log.e("NetworkService ${T::class.simpleName}", "IsNull")
        return null
    }

    suspend inline fun <reified Y>launchRequest(f: KSuspendFunction0<Response<Y>>, context: Context): Y? {
        try {
            val response = f()
            if (response.isSuccessful) {
                return response.body()
            } else {
                context.toast("Error: ${response.code()}")
            }
        } catch (e: HttpException) {
            context.toast("HttpException ${e.message}")
            Log.e("NetworkService ", "HttpException ${e.message}")
        } catch (e: SocketTimeoutException) {
            context.toast("Timeout to ${retrofit.baseUrl()}:80")
            Log.e("NetworkService ", "Timeout to ${retrofit.baseUrl()}:80")
        } catch (e: Throwable) {
            context.toast("Throwable: ${e.message}")
            Log.e("NetworkService ", "Throwable: ${e.message}")
        } catch (e: Exception) {
            context.toast("Exception ${e.message}")
            Log.e("NetworkService ", "Exception ${e.message}")
        }

        //context.toast("IsNull")
        //Log.e("NetworkService ", "IsNull")
        return null
    }
}