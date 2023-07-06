package com.nic.googlemapsearch.Fragement

import androidx.lifecycle.ViewModel
import com.nic.googlemapsearch.REalemDB.AddressBookinfo
import com.nic.googlemapsearch.REalemDB.RealamtaskUtill
import io.realm.Realm

class AddDataViewModel : ViewModel() {
   private var realm: Realm = Realm.getDefaultInstance()
   fun Addnewtask(realm: AddressBookinfo, addDataFragment: AddDataFragment) {

   }
}