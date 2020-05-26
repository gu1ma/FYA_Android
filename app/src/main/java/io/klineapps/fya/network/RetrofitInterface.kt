package br.com.firstdecision.cowip.util


import retrofit2.Response
import retrofit2.http.*
import rx.Observable
import io.klineapps.fya.model.SportResponse

interface RetrofitInterface {
    //Tela de Cadastro de Usuário


    @Headers("Content-Type: application/json", "Accept: application/json")
    //Tela de Login
    @GET("fya/getSports")
    fun getCoworkings(): Observable<Response<SportResponse>>


}