package com.diegocunha.warrenchat.injection

import com.diegocunha.warrenchat.BuildConfig
import com.diegocunha.warrenchat.model.repository.MessageRepository
import com.diegocunha.warrenchat.model.repository.network.NetworkRepository
import com.diegocunha.warrenchat.model.repository.network.WarrenAPI
import com.diegocunha.warrenchat.view.home.HomeViewModel
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModules = module {

    factory { GsonBuilder().create() }

    factory {
        val logging = HttpLoggingInterceptor()
        logging.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    factory {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

    factory {
        val retrofit: Retrofit = get()
        retrofit.create(WarrenAPI::class.java)
    }

    single { NetworkRepository(get()) as MessageRepository }

    viewModel { HomeViewModel(get()) }
}