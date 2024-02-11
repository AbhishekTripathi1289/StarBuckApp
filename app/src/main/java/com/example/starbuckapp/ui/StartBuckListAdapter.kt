package com.example.starbuckapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.starbuckapp.databinding.ItemStarBuckBinding
import com.example.starbuckapp.models.StarbuckData
import java.text.DecimalFormat

class StartBuckListAdapter(var startBuckList: ArrayList<StarbuckData>, var callback: (StarbuckData) -> Unit) : RecyclerView.Adapter<StartBuckListAdapter.StarBuckViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarBuckViewHolder
    {
        return StarBuckViewHolder(ItemStarBuckBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StarBuckViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int
    {
        return startBuckList.size
    }
    inner class StarBuckViewHolder(private var itemViewLayout: ItemStarBuckBinding) : ViewHolder(itemViewLayout.root)
    {
        init {
            itemViewLayout.root.setOnClickListener{
                callback.invoke(startBuckList.get(adapterPosition))
            }
        }
        fun bind(position: Int)
        {
            itemViewLayout.textViewStarBuckTitle.text = startBuckList.get(position).store_name
            itemViewLayout.textViewAddress.text = startBuckList.get(position).address.toString()
            itemViewLayout.textViewCity.text = startBuckList.get(position).City
            val decimalFormat = DecimalFormat("#.##")
            val formattedValue: String = decimalFormat.format(startBuckList.get(position).distance)
            itemViewLayout.textviewDistanceValue.text = "$formattedValue Km"
        }
    }

    fun updateList(it: ArrayList<StarbuckData>) {
        startBuckList = it
    }
}