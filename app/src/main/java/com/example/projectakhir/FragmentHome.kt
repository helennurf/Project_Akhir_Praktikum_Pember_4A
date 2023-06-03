package com.example.projectakhir

import ListMobil
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentHome : Fragment() {

    private val list = ArrayList<Mobil>()
    private lateinit var adapter: ListMobil
    private val originalList = ArrayList<Mobil>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        list.addAll(getDataMobil())
        originalList.addAll(list)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvMobil)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = ListMobil(list)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }
        })

        searchView.setOnCloseListener {
            filterList("")
            false
        }

        return view
    }

    private fun getDataMobil(): ArrayList<Mobil> {
        val dataNama = resources.getStringArray(R.array.mobil_nama)
        val dataKapasitas = resources.getStringArray(R.array.mobil_kapasitas)
        val dataFoto = resources.obtainTypedArray(R.array.mobil_foto)
        val listData = ArrayList<Mobil>()
        for (i in dataNama.indices) {
            val mobil = Mobil(dataNama[i], dataKapasitas[i], dataFoto.getResourceId(i, 0))
            listData.add(mobil)
        }

        dataFoto.recycle()
        return listData
    }

    private fun filterList(text: String) {
        val filteredList = mutableListOf<Mobil>()
        if (text.isEmpty()) {
            filteredList.addAll(originalList)
        } else {
            for (mobil in originalList) {
                if (mobil.nama.toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(mobil)
                }
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
        }

        adapter.updateList(filteredList)
        updateEmptyState(filteredList.isEmpty())
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rvMobil)
        val emptyStateTextView = view?.findViewById<TextView>(R.id.tvDataTidakDitemukan)

        if (isEmpty) {
            recyclerView?.isVisible = false
            emptyStateTextView?.isVisible = true
        } else {
            recyclerView?.isVisible = true
            emptyStateTextView?.isVisible = false
        }
    }
}
