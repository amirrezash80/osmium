//package com.amirreza.osmiumproject
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class CellLocationAdapter(private val cellLocations: List<CellLocation>) :
//    RecyclerView.Adapter<CellLocationAdapter.CellLocationViewHolder>() {
//
//    class CellLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val locationTextView: TextView = itemView.findViewById(R.id.locationText)
//        val cellIdTextView: TextView = itemView.findViewById(R.id.cellIdText)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellLocationViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_cell_location, parent, false)
//        return CellLocationViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: CellLocationViewHolder, position: Int) {
//        val currentItem = cellLocations[position]
//        holder.locationTextView.text = "Location: ${currentItem.location}"
//        holder.cellIdTextView.text = "Cell ID: ${currentItem.cellId}"
//    }
//
//    override fun getItemCount() = cellLocations.size
//}
