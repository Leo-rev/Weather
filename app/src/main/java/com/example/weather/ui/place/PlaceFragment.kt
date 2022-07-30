package com.example.weather.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class PlaceFragment:Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter:PlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager=LinearLayoutManager(activity)
        adapter= PlaceAdapter(this,viewModel.placeList)
        val recycleView: RecyclerView? = view?.findViewById(R.id.recycleView)
        val bgImageView:ImageView?=view?.findViewById(R.id.bgImaageView)
        val searchPlaceEdit: EditText? =view?.findViewById(R.id.searchPlaceEdit)
        recycleView?.layoutManager=layoutManager
        recycleView?.adapter=adapter
        searchPlaceEdit?.addTextChangedListener { editable->
            val content=editable.toString()
            if(content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                recycleView?.visibility=View.GONE
                bgImageView?.visibility=View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(this, Observer { result ->
            val places=result.getOrNull()
            if(places!=null){
                recycleView?.visibility=View.VISIBLE
                bgImageView?.visibility=View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}