//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.amirreza.osmiumproject.R
//
//data class CellLocation(val location: Pair<Double, Double>, val cellId: Int)
//
//class CellLocationAdapter(private val cellLocations: List<CellLocation>) : RecyclerView.Adapter<CellLocationAdapter.ViewHolder>() {
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val locationText: TextView = itemView.findViewById(R.id.locationText)
//        val cellIdText: TextView = itemView.findViewById(R.id.cellIdText)
//        val icon: ImageView = itemView.findViewById(R.id.icon)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cell_location, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val cellLocation = cellLocations[position]
//        holder.locationText.text = "Location: (${cellLocation.location.first}, ${cellLocation.location.second})"
//        holder.cellIdText.text = "Cell ID: ${cellLocation.cellId}"
//        holder.icon.setImageResource(R.drawable.ic_cell_tower)
//    }
//
//    override fun getItemCount(): Int {
//        return cellLocations.size
//    }
//}
