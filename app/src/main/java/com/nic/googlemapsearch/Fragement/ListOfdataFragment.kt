package com.nic.googlemapsearch.Fragement

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*

import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.nic.googlemapsearch.Adopter.Recycleadopter
import com.nic.googlemapsearch.R
import com.nic.googlemapsearch.REalemDB.AddressBookinfo
import com.nic.googlemapsearch.Utill
import com.nic.googlemapsearch.databinding.FragmentListOfdataBinding
import io.realm.Realm
import io.realm.RealmResults

class ListOfdataFragment() : Fragment(), View.OnClickListener {

    private var _binding: FragmentListOfdataBinding? = null
    private var ids = 0

    var offline: RealmResults<AddressBookinfo>? = null
    lateinit var realm: Realm
    private val binding get() = _binding!!


    private lateinit var viewModel: ListOfdataViewModel

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.deleteitom).setVisible(true)
        // menu.findItem(android.R.id.home).setVisible(true)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOfdataBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      requireActivity().actionBar?.setDisplayShowHomeEnabled(true)
        //actionBar?.setTitle("Task List")
        viewModel = ViewModelProvider(this).get(ListOfdataViewModel::class.java)

        realm = Realm.getDefaultInstance()
        binding.recycleview.layoutManager =
            LinearLayoutManager(context?.applicationContext, VERTICAL, true)
        binding.recycleview?.setHasTransientState(true)


        offline = viewModel.gettasklist(realm)


        if ((offline as RealmResults<AddressBookinfo>?)!!.size > 0) {

            binding.recycleview.adapter =
                (offline as RealmResults<AddressBookinfo>?)?.let { Recycleadopter(context, it) }

            binding.recycleview.adapter?.notifyDataSetChanged()



        }


    }

    override fun onClick(v: View?) {

    }





}