package com.nic.googlemapsearch.Fragement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nic.googlemapsearch.REalemDB.AddressBookinfo
import io.realm.Realm
import io.realm.RealmResults

class ListOfdataViewModel : ViewModel() {

val mutableList= MutableLiveData<Any>()
fun gettasklist(realm: Realm) : RealmResults<AddressBookinfo> {
    return realm.where(AddressBookinfo::class.java).findAll()
}

}