package com.nic.googlemapsearch

import com.google.android.libraries.places.api.Places
import io.realm.Realm
import io.realm.RealmConfiguration

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val  config: RealmConfiguration = RealmConfiguration.Builder().name("${R.string.app_name}") .schemaVersion(1)
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        Places.createClient(this)
//
//
     //   val backgroundThreadRealm : Realm = Realm.getInstance(config)

    }



}