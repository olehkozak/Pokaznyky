package pokaznyky.lviv.ui.textwatcher

import android.text.Editable
import android.text.TextWatcher
import pokaznyky.lviv.model.Counter

/**
 * Created by olehkozak on 12/13/17.
 */
class CounterNumberTextWatcher(private var counter: Counter) : TextWatcher{
    override fun afterTextChanged(editText: Editable) {
        counter.number = if (editText.isEmpty()) -1 else Integer.parseInt(editText.toString())
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}