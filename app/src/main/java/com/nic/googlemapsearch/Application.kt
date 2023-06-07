package com.nic.googlemapsearch

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
//
//
     //   val backgroundThreadRealm : Realm = Realm.getInstance(config)

    }



}