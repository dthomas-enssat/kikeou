package fr.enssat.kikeou.thomas_bricaud.generate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewbinding.ViewBindings
import fr.enssat.kikeou.thomas_bricaud.R
import fr.enssat.kikeou.thomas_bricaud.scan.ScanQrCode

/**
 * A simple [Fragment] subclass.
 * Use the [GenerateQrCode.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenerateQrCode : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_generate_qr_code, container, false)
        var btnGenerate = v.findViewById<Button>(R.id.generate)
        btnGenerate.setOnClickListener {
            generate(v)
        }
        return v
    }

    private fun generate(v: View) {

        return
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
