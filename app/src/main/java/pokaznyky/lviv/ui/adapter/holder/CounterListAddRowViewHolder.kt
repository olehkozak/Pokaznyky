package pokaznyky.lviv.ui.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.counter_list_add_row.view.*
import pokaznyky.lviv.model.Counter
import pokaznyky.lviv.ui.adapter.CountersListAdapter

/**
 * Created by olehkozak on 12/13/17.
 */
class CounterListAddRowViewHolder(val adapter: CountersListAdapter, val countersList: MutableList<Counter>, rowView: View) : RecyclerView.ViewHolder(rowView) {

    init {
        rowView.add_row_iv.setOnClickListener { _ ->  onRowClick()}
        rowView.add_row_tv.setOnClickListener { _ ->  onRowClick()}
    }

    private fun onRowClick() {
        val index: Int = if (countersList.isEmpty()) 0 else countersList.size
        countersList.add(Counter())
        adapter.notifyItemInserted(index)
    }
}