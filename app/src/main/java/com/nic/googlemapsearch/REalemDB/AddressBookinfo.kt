package com.nic.googlemapsearch.REalemDB

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


//245416

open class AddressBookinfo (
    @PrimaryKey
     var id: Int = 0,
     var name: String? = null,
     var phonNumber: String? =null,
     var prority: Int = 0,
     var Subject: String? = null,
     var TaskDescription: String? = null,
     var lat :Double ? =0.0,
     var lon :Double ? =0.0,
) :RealmObject()