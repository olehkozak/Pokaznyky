package pokaznyky.lviv.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.address_layout.*
import kotlinx.android.synthetic.main.fragment_input.*
import pokaznyky.lviv.R
import pokaznyky.lviv.model.Counter
import pokaznyky.lviv.ui.adapter.CountersListAdapter

/**
 * Created by olehkozak on 12/1/17.
 */
class InputFragment : Fragment() {

    private lateinit var selectedRegion: String
    private var countersList: MutableList<Counter> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedRegion = resources.getString(R.string.galych_mail)

        val spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.region_mail, android.R.layout.simple_spinner_dropdown_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        regions_spinner.adapter = spinnerAdapter
        regions_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                spinnerItemSelected(parent, position)
            }
        }

        counter_list.layoutManager = LinearLayoutManager(context)
        counter_list.itemAnimator = DefaultItemAnimator()
        counter_list.adapter = CountersListAdapter(countersList)

        send_email.setOnClickListener { _ -> sendEmail() }
    }

    private fun EditText.textString(): String {
        return text.toString()
    }

    private fun EditText.isTextStringEmpty(): Boolean {
        return text.toString().isEmpty()
    }

    private fun validateEmailData(): Boolean {
        val viewsList: List<EditText> = listOf(identity_number, street, building)
        for (view in viewsList) {
            if (!view.validateEditTextView()) {
                return false
            }
        }

        if (countersList.isEmpty()) {
            Toast.makeText(context, "Лічильники не вказані", Toast.LENGTH_SHORT).show()
            return false
        }

        var emptyCounters = 0
        for (counter in countersList) {
            if (counter.isEmpty()) {
                emptyCounters++
                continue
            }
            if (counter.number == -1 || counter.result == -1) {
                Toast.makeText(context, "Дані лічильника некоректні", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        if (emptyCounters == countersList.size) {
            Toast.makeText(context, "Показники відсутні", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun EditText.validateEditTextView(): Boolean {
        if (this.isTextStringEmpty()) {
            val toastMsg: String = "Потрібно Заповнити " + this.hint.toString()
            Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun sendEmail() {

        if (!validateEmailData()) {
            return
        }

        var msgIdentity: String = (
                identity_number.textString() + "\n"
                        + street.textString() + " "
                        + building.textString()
                )

        if (apartment.text.isNotEmpty()) {
            msgIdentity += "/" + apartment.textString()
        }

        var msgCountersResults = "\n"
        for (counter in countersList) {
            if (counter.number == -1 && counter.result == -1) {
                continue
            }
            msgCountersResults += counter.number.toString() + " - " + counter.result + "\n"
        }

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(selectedRegion))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
        emailIntent.putExtra(Intent.EXTRA_TEXT, msgIdentity + msgCountersResults)

        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Select Email Sending App :"))
    }

    private fun spinnerItemSelected(parent: AdapterView<*>, position: kotlin.Int) {
        val selectedItem = parent.getItemAtPosition(position).toString()
        when (selectedItem) {
            resources.getString(R.string.galych_region) -> selectedRegion = resources.getString(R.string.galych_mail)
            resources.getString(R.string.franko_region) -> selectedRegion = resources.getString(R.string.franko_mail)
            resources.getString(R.string.lychakiv_region) -> selectedRegion = resources.getString(R.string.lychakiv_mail)
            resources.getString(R.string.syhiv_region) -> selectedRegion = resources.getString(R.string.syhiv_mail)
            resources.getString(R.string.zalizn_region) -> selectedRegion = resources.getString(R.string.zalizn_mail)
            resources.getString(R.string.shevchenko_region) -> selectedRegion = resources.getString(R.string.shevchenko_mail)
        }
    }
}