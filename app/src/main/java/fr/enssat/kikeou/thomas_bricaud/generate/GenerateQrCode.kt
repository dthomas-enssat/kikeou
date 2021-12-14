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
import androidx.viewbinding.ViewBindings
import fr.enssat.kikeou.thomas_bricaud.R
import fr.enssat.kikeou.thomas_bricaud.scan.ScanQrCode

/**
 * A simple [Fragment] subclass.
 * Use the [GenerateQrCode.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenerateQrCode : Fragment(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_generate_qr_code, container, false)
        var btnGenerate: Button = v.findViewById(R.id.generate)
        var spinner: Spinner = v.findViewById(R.id.weekly_spinner)
        btnGenerate.setOnClickListener {
            generate(v)
        }
        ArrayAdapter.createFromResource(
            context!!,
            R.array.weekly_array,
            android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameter
         * @return A new instance of fragment ScanQrCode.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = GenerateQrCode().apply {}
    }
}
