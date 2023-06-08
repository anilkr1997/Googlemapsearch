package com.nic.googlemapsearch

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.database.Observable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.nic.googlemapsearch.REalemDB.AddressBookinfo
import io.realm.Realm


class Utill {

     var context: Context? =null
    constructor(context: Context? ) {
        this.context = context

    }
    fun setFragment(
        fragment: Fragment?,
        removeStack: Boolean,
        activity: FragmentActivity,
        mContainer: Int
    ) {
        val fragmentManager = activity.supportFragmentManager
        val ftTransaction = fragmentManager.beginTransaction()
        if (removeStack) {
            val size = fragmentManager.backStackEntryCount
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            ftTransaction.replace(mContainer, fragment!!)
        } else {
            ftTransaction.replace(mContainer, fragment!!)
            ftTransaction.addToBackStack(null)
        }
        ftTransaction.commit()
    }
    fun showmassage(context: Context?,view: View,massage:String){
        val snackbar = Snackbar.make(view, massage, Snackbar.LENGTH_LONG)

        snackbar.show()

    }

     fun Deletaitem(context: Context?,realm: Realm)  {

        AlertDialog.Builder(context)
            .setTitle("Delete")
            .setMessage("Are you sure you want to Delete All Task?")
            .setPositiveButton(R.string.ok) { dialog, which ->

                realm.beginTransaction()
                realm.deleteAll()
                realm.commitTransaction()
                realm.isAutoRefresh

                Toast.makeText(context, "Data Delete Successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()



         })
            .setIcon(R.drawable.ic_delete)
            .show()

    }


}