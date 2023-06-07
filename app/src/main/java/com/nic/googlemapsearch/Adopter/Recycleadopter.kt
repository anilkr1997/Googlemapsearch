package com.nic.googlemapsearch.Adopter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.nic.googlemapsearch.R
import com.nic.googlemapsearch.REalemDB.AddressBookinfo
import io.realm.RealmResults

class Recycleadopter(context: Context?, offline: RealmResults<AddressBookinfo>) :
    RecyclerView.Adapter<Recycleadopter.MyViewHolder>() {

    val context = context
    val offline = offline

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val tvname: AppCompatTextView = item.findViewById(R.id.tv_name)
        val phonenum: AppCompatTextView = item.findViewById(R.id.tv_pnumber)
        val title: AppCompatTextView = item.findViewById(R.id.tv_title)
        val discrepthion: AppCompatTextView = item.findViewById(R.id.tv_descrepthin)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklistdata, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return offline.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvname.setText(offline[position]?.name)
        holder.phonenum.setText(offline[position]?.phonNumber)
        holder.title.setText(offline[position]?.Subject)
        holder.discrepthion.setText(offline[position]?.TaskDescription)
    }
}