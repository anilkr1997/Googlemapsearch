package com.nic.googlemapsearch

import android.location.GnssStatus
import android.location.Location
import android.location.LocationListener
import android.util.Log

class GNSSStatusListener:LocationListener, GnssStatus.Callback() {
    override fun onLocationChanged(location: Location) {
        Log.e("TAG", "onLocationChanged: ${location.latitude}", )
    }
}