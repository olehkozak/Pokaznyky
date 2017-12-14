package pokaznyky.lviv.ui.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.counter_list_row.view.*
import pokaznyky.lviv.model.Counter
import pokaznyky.lviv.ui.adapter.CountersListAdapter
import pokaznyky.lviv.ui.textwatcher.CounterNumberTextWatcher
import pokaznyky.lviv.ui.textwatcher.CounterResultTextWatcher

/**
 * Created by olehkozak on 12/13/17.
 */
class CounterListRowViewHolder(val adapter: CountersListAdapter, val countersList: MutableList<Counter>, val rowView: View) : RecyclerView.ViewHolder(rowView) {

    lateinit var counter : Counter

    fun configureRowHolder(counterPosition: Int) {
        counter = countersList[counterPosition]
        fillValues()
        addTextWatcher()
    }

    private fun fillValues() {
        rowView.counter_number.setText(if (counter.number == -1) "" else counter.number.toString())
        rowView.counter_result.setText(if (counter.result == -1) "" else counter.result.toString())
    }

    private fun addTextWatcher() {
        rowView.counter_number.addTextChangedListener(CounterNumberTextWatcher(counter))
        rowView.counter_result.addTextChangedListener(CounterResultTextWatcher(counter))
        rowView.counter_delete.setOnClickListener { _ -> onDeleteRow(counter) }
    }

    private fun onDeleteRow(counter: Counter) {
        val index = countersList.indexOf(counter)
        countersList.remove(counter)
        adapter.notifyItemRemoved(index)
    }
}