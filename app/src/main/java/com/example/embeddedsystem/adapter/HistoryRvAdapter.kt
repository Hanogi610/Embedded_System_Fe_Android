import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.embeddedsystem.R
import com.example.embeddedsystem.model.History
import java.text.SimpleDateFormat
import java.util.*

class HistoryRvAdapter(private var histories: List<History>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || getHeaderDate(position) != getHeaderDate(position - 1)) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    private fun getHeaderDate(position: Int): String {
        val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
        return sdf.format(Date(histories[position].time))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(getHeaderDate(position))
        } else if (holder is ItemViewHolder) {
            holder.bind(histories[position])
        }
    }

    fun updateHistories(newHistories: List<History>) {
        histories = newHistories
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerTextView: TextView = itemView.findViewById(R.id.tvHeaderDate)

        fun bind(headerDate: String) {
            headerTextView.text = headerDate
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        private val statusTextView: TextView = itemView.findViewById(R.id.tvStatus)
        private val timeTextView: TextView = itemView.findViewById(R.id.tvTime)

        fun bind(history: History) {
            nameTextView.text = "Device: ${history.deviceName}"
            statusTextView.text = if (history.status) "Status: ON" else "Status: OFF"
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
            timeTextView.text = "Time: ${sdf.format(Date(history.time))}"
        }
    }
}
