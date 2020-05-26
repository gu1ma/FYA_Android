package io.klineapps.fya

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Handler
import android.util.Log
import androidx.core.app.ActivityCompat

import com.facebook.stetho.Stetho
import com.google.android.gms.location.LocationRequest
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class App : Application() {


    private var handler = Handler()
    private var location: Location? = null

    // Called when the application is starting, before any other application objects have been created.
    override fun onCreate() {
        super.onCreate()

        instance = this
        Stetho.initializeWithDefaults(this)


    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d("testeApp", "onTerminate: ")
    }

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }

    companion object {

        var instance: App? = null
    }

    fun getLastLocation() {

        //Verifica se o App tem as permissões necessárias para obter a localização e em caso positivo sai do método
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val locationProvider = ReactiveLocationProvider(this)
        //Méotod para obter a última localização conhecida do usuário
        locationProvider.lastKnownLocation
            .subscribe { location1 ->
                if (location1 != null) {
                    val baseLocation =
                        "{\"latitude\":" + location1.latitude + ", \"longitude\":" + location1.longitude + "}"
                    Log.d("Location", "onCreate: $baseLocation")
                    location = location1
                }
            }

        val req = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setExpirationDuration(TimeUnit.SECONDS.toMillis(1000))
            .setInterval(1000*60)

        val goodEnoughQuicklyOrNothingObservable = locationProvider.getUpdatedLocation(req)
            .filter { location -> location.accuracy < 5 }
            .timeout(
                1000,
                TimeUnit.SECONDS,
                Observable.just<Location>(null as Location?),
                AndroidSchedulers.mainThread()
            )
            .first()
            .observeOn(AndroidSchedulers.mainThread())

        goodEnoughQuicklyOrNothingObservable.subscribe()

    }


    //Método para recuperar a localização do usuário em background a cada 60 segundos
    public fun locationUpdate() {


        val r = object : Runnable {
            override fun run() {
                getLastLocation()
                handler.postDelayed(this, (1000 * 60).toLong())
            }
        }

        handler.postDelayed(r, 100)
    }

    fun getLocation(): Location {
        return location!!
    }
}