package kek.enxy.plantwriter.presentation.main.scan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kek.enxy.data.readwrite.model.Dump
import kek.enxy.plantwriter.databinding.ItemCurrentDumpBinding
import kek.enxy.plantwriter.presentation.main.model.DumpState

class CurrentDumpAdapter(
    private val onCurrentDumpClick: (dump: Dump) -> Unit
) : ListAdapter<DumpState, CurrentDumpAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<DumpState>() {
        override fun areItemsTheSame(oldItem: DumpState, newItem: DumpState): Boolean = true
        override fun areContentsTheSame(oldItem: DumpState, newItem: DumpState): Boolean = false
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrentDumpBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onCurrentDumpClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<DumpState>?) {
        super.submitList(list?.filterNot { it is DumpState.Initial })
    }

    class ViewHolder(
        private val binding: ItemCurrentDumpBinding,
        private val onCurrentDumpClick: (dump: Dump) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(state: DumpState) = with(binding) {
            viewCurrentDump.setOnClickListener {
                val dump = (state as? DumpState.Content)?.dump
                if (dump != null) {
                    onCurrentDumpClick(dump)
                }
            }
            when (state) {
                is DumpState.Content -> viewCurrentDump.setDetails(state.dump)
                is DumpState.Loading -> viewCurrentDump.setLoading()
                is DumpState.Error -> viewCurrentDump.setError(state.exception)
                else -> Unit
            }
        }
    }
}
