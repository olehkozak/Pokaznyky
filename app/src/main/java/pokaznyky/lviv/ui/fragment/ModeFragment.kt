package pokaznyky.lviv.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_mode.*
import pokaznyky.lviv.ui.activity.MainActivity
import pokaznyky.lviv.R

/**
 * Created by olehkozak on 12/1/17.
 */
class ModeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manual_input.setOnClickListener {
            (activity as? MainActivity)?.showFragment(InputFragment())
        }
        auto_input.setOnClickListener {
            Toast.makeText(context, "Очікуйте незабаром", Toast.LENGTH_SHORT).show()
        }
    }
}