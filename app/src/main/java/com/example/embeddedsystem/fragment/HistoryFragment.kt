package com.example.embeddedsystem.fragment

import HistoryRvAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.embeddedsystem.R
import com.example.embeddedsystem.model.History
import com.example.embeddedsystem.service.HistoryApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    var houseId = 0
    lateinit var histories : MutableList<History>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: HistoryRvAdapter

    companion object {
        fun newInstance(param : Int) : HistoryFragment{
            val fragment = HistoryFragment()
            val args = Bundle()
            args.putInt("param_key", param)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments?.getInt("param_key")
        if (args != null) {
            houseId = args
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        histories = mutableListOf()
        adapter = HistoryRvAdapter(histories)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(),1)
        lifecycleScope.launch {
            HistoryApiHelper.getHistories(houseId)
                .flowOn(Dispatchers.IO)
                .collect {
                    Log.d("HistoryFragment", it.toString())
                    histories = it.toMutableList()
                    adapter.updateHistories(histories.sortedByDescending { it.time })
                }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchHistories()
    }

    private fun fetchHistories() {
        lifecycleScope.launch {
            while (true) {
                Log.d("HistoryFragment", "fetchHistories")
                HistoryApiHelper.getHistories(houseId)
                    .flowOn(Dispatchers.IO)
                    .collect {
                        Log.d("HistoryFragment", it.toString())
                        histories = it.toMutableList()
                        adapter.updateHistories(histories.sortedByDescending { it.time })
                    }
                delay(1000)
            }
        }
    }
}