package com.nic.googlemapsearch.Adopter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.nic.googlemapsearch.R
import com.nic.googlemapsearch.REalemDB.AddressBookinfo
import com.nic.googlemapsearch.databinding.TasklistdataBinding
import io.realm.Realm
import io.realm.RealmResults

class Recycleadopter(private val context: Context?, private val offline: RealmResults<AddressBookinfo>) :
    RecyclerView.Adapter<Recycleadopter.MyViewHolder>() {
val realm= Realm.getDefaultInstance()!!

    class MyViewHolder(val binding: TasklistdataBinding) : RecyclerView.ViewHolder(binding.root) {



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(TasklistdataBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return offline.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.tvName.setText(offline[position]?.name)
        holder.binding.tvPnumber.setText(offline[position]?.phonNumber)
        holder.binding.tvTitle.setText(offline[position]?.Subject)
        holder.binding.tvDescrepthin.setText(offline[position]?.TaskDescription)
//        holder.binding.btnMap.setOnClickListener{
//
//        }
//        holder.binding.btnDelete.setOnClickListener{
//            notifyItemRemoved(position)
//
//
//        }
    }
}