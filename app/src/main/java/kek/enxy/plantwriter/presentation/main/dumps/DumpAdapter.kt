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
    private val dumpListener: DumpListener
) : ListAdapter<Dump, DumpAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Dump>() {
        override fun areItemsTheSame(oldItem: Dump, newItem: Dump): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Dump, newItem: Dump): Boolean = oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDumpBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, dumpListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemDumpBinding,
        private val listener: DumpListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dump: Dump) = with(binding) {
            val resources = itemView.resources
            root.setOnClickListener { listener.onDumpClicked(dump) }
            textName.text = dump.name
            textBalance.text = resources.getString(
                R.string.dump_balance,
                dump.balance.value
            )
            textUnderground.text = resources.getString(
                R.string.dump_underground,
                dump.undergroundTravelTotal.toString()
            )
            textGround.text = resources.getString(
                R.string.dump_ground,
                dump.groundTravelTotal.toString()
            )
            textUID.text = resources.getString(
                R.string.dump_uid,
                dump.uid
            )
        }
    }

    fun interface DumpListener {
        fun onDumpClicked(dump: Dump)
    }
}
