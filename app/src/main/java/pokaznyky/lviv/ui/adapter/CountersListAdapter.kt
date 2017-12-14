package pokaznyky.lviv.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.counter_list_row.view.*
import pokaznyky.lviv.R
import pokaznyky.lviv.model.Counter
import pokaznyky.lviv.ui.adapter.holder.CounterListAddRowViewHolder
import pokaznyky.lviv.ui.adapter.holder.CounterListRowViewHolder
import pokaznyky.lviv.util.RowViewType

/**
 * Created by olehkozak on 12/13/17.
 */
class CountersListAdapter(private var countersList: MutableList<Counter>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == RowViewType.REGULAR_ROW.value) {
            (holder as CounterListRowViewHolder).configureRowHolder(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) RowViewType.ADD_ROW.value else RowViewType.REGULAR_ROW.value
    }

    override fun getItemCount(): Int {
        return countersList.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RowViewType.ADD_ROW.value -> CounterListAddRowViewHolder(this, countersList, LayoutInflater.from(parent.context).inflate(R.layout.counter_list_add_row, parent, false))
            else -> CounterListRowViewHolder(this, countersList, LayoutInflater.from(parent.context).inflate(R.layout.counter_list_row, parent, false))
        }
    }

}