package com.nic.googlemapsearch.Fragement

import android.location.Location
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.nic.googlemapsearch.R
import com.nic.googlemapsearch.REalemDB.AddressBookinfo
import com.nic.googlemapsearch.Utill
import com.nic.googlemapsearch.databinding.FragmentAddDataBinding
import io.realm.Realm
import io.realm.Realm.getDefaultInstance

class AddDataFragment() : Fragment(), View.OnClickListener {
//    var actionBar=supportActionBar
//    var location=mCurrentLocation;


    private var nextids=0
private lateinit var latLng: LatLng
    private lateinit var realm: Realm
    private lateinit var viewModel: AddDataViewModel
private lateinit var addDataBinding: FragmentAddDataBinding
    private val binding get() = addDataBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){
             latLng= requireArguments().get("latlng") as LatLng
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        addDataBinding= FragmentAddDataBinding.inflate(layoutInflater,container,false)
        return addDataBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddDataViewModel::class.java)
      realm  = getDefaultInstance();
        binding.btnAdd.setOnClickListener(this)
        Log.e("TAG", "onViewCreated: $latLng", )
    }
    override fun onClick(v: View?) {
        if (v?.id?.equals(R.id.btn_add)!!) {
            if (validation()) {
                realm.executeTransactionAsync ({

                    var ids = it.where(AddressBookinfo::class.java).max("id")


                    nextids = ids?.toInt()?.plus(1) ?: 1

                    var addressBookinfo= AddressBookinfo(nextids,binding.etName.text.toString(),binding.etPhonenumber.text.toString(),
                        0,binding.etTitle.text.toString(),binding.etDescreptiuon.text.toString(),
                        latLng?.latitude,latLng?.longitude
                    )

                    it.insert(addressBookinfo)

                },Realm.Transaction.OnSuccess {
                    Utill(context?.applicationContext).showmassage(context,binding.btnAdd,"Data save successfully.")
                        binding.etName.text?.clear()
                        binding.etDescreptiuon.text?.clear()
                        binding.etTitle.text?.clear()
                        binding.etPhonenumber.text?.clear()

                }
                    ,
                    {
                        Utill(context?.applicationContext).showmassage(context,binding.btnAdd,it.message.toString())

                })


            }









        }
    }

    private fun validation(): Boolean {

        if (TextUtils.isEmpty(binding.etName.text)) {
            showerrror("Please Enter Name")
            return false

        } else if (TextUtils.isEmpty(binding.etPhonenumber.text)) {
            showerrror("Please Enter Phone Number")
            return false

        } else if (TextUtils.isEmpty(binding.etTitle.text)) {
            showerrror("Please Enter Title")
            return false

        } else if (TextUtils.isEmpty(binding.etDescreptiuon.text)) {
            showerrror("Please Enter Description")
            return false
        }
        return true

    }

    private fun showerrror(massag:String) {
        Toast.makeText(requireContext(),massag, Toast.LENGTH_LONG).show()

    }

    override fun onDestroy() {
        super.onDestroy()

    }

}