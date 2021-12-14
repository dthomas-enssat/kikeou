package fr.enssat.kikeou.thomas_bricaud.generate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import fr.enssat.kikeou.thomas_bricaud.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [GenerateQrCode.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenerateQrCode : Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var spinner: Spinner;
    lateinit var adapter: ArrayAdapter<String>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_generate_qr_code, container, false)
        val btnGenerate: Button = v.findViewById(R.id.generate)

        // spinner
        val weeklyArray = arrayListOf<String>();
        val week: Calendar = Calendar.getInstance()
        for (i in 0..3) {
            var weeks = (week.get(Calendar.WEEK_OF_YEAR) + i) % 52
            if (weeks == 0) weeks = 52;
            weeklyArray.add(weeks.toString())
        }
        adapter = ArrayAdapter(v.context, android.R.layout.simple_spinner_dropdown_item, weeklyArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner = v.findViewById(R.id.weekly_spinner)
        spinner.adapter = adapter


        btnGenerate.setOnClickListener {
            generate(v)
        }
        return v
    }

    private fun generate(v: View) {

        return
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // for spinner element
        val spinner: Spinner = parent.findViewById(R.id.weekly_spinner)
        spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // for spinner element
    }
}
