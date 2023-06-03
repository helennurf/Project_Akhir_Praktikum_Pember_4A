package com.example.projectakhir

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentPayments : Fragment() {

    private val list = ArrayList<Payments>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_payments, container, false)

        list.addAll(getDataTransaksi())

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvTransaksi)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = ListTransaksi(list)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        return view
    }

    private fun getDataTransaksi(): ArrayList<Payments> {
        val dataJudul = resources.getStringArray(R.array.transaksi)
        val listData = ArrayList<Payments>()
        for (i in dataJudul.indices) {
            val payments = Payments  (dataJudul[i])
            listData.add(payments)
        }
        return listData

    }
}