package kek.enxy.plantwriter.presentation.main.dumps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.ItemDumpBinding
import kek.enxy.plantwriter.presentation.common.extensions.resources

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
            imgPlantain.load(R.drawable.plantain_card)
            root.setOnClickListener { listener.onDumpDetailsClicked(dump) }
            imgRemove.setOnClickListener { listener.onDumpRemoveClicked(dump) }
            textName.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    dump.name,
                    TextViewCompat.getTextMetricsParams(textName),
                    null
                )
            )
            val textChipParams = TextViewCompat.getTextMetricsParams(textBalance)
            textBalance.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    resources.getString(R.string.dump_balance, dump.balance.value),
                    textChipParams,
                    null
                )
            )
            val lastUseDate = resources.getString(
                R.string.dump_last_use,
                if (dump.lastUseDate.raw == 0) {
                    resources.getString(R.string.dump_last_use_no)
                } else {
                    dump.lastUseDate.getFormattedDate("dd.MM.yy")
                }
            )
            textLastUseDate.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    lastUseDate,
                    textChipParams,
                    null
                )
            )
        }
    }

    interface DumpListener {
        fun onDumpDetailsClicked(dump: Dump)
        fun onDumpRemoveClicked(dump: Dump)
    }
}
