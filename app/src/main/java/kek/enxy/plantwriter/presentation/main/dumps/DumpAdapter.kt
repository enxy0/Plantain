package kek.enxy.plantwriter.presentation.main.dumps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.ItemDumpBinding

class DumpAdapter(
    private val listener: DumpListener
) : ListAdapter<Dump, DumpAdapter.DumpViewHolder>(diffCallback) {

    private companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Dump>() {
            override fun areItemsTheSame(oldItem: Dump, newItem: Dump) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Dump, newItem: Dump) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DumpViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDumpBinding.inflate(inflater, parent, false)
        return DumpViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: DumpViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DumpViewHolder(
        private val binding: ItemDumpBinding,
        private val listener: DumpListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dump: Dump) = with(binding) {
            val resources = itemView.resources
            root.setOnClickListener { listener.onDumpClicked(dump) }
            textName.text = dump.name
            chipBalance.text = resources.getString(
                R.string.dump_balance,
                dump.balance.value
            )
            chipUnderground.text = resources.getString(
                R.string.dump_underground,
                dump.undergroundTravelTotal.toString()
            )
            chipGround.text = resources.getString(
                R.string.dump_ground,
                dump.groundTravelTotal.toString()
            )
        }
    }

    fun interface DumpListener {
        fun onDumpClicked(dump: Dump)
    }
}
