package br.edu.utfpr.apppizzaria.data.network

import br.edu.utfpr.apppizzaria.MainActivity
import br.edu.utfpr.apppizzaria.data.user.local.UserLoggedDatasource
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authenticatedRequest = originalRequest.newBuilder()

        runBlocking {
            delay(500)
            val userLogged = UserLoggedDatasource(MainActivity.appContext).getUserLogged()
            if (userLogged != null) {
                authenticatedRequest.header("PizzeriaId", "${userLogged.id}")
            }
        }
        return chain.proceed(authenticatedRequest.build())
    }
}