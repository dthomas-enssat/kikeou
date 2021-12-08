package fr.enssat.kikeou.thomas_bricaud.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.enssat.kikeou.thomas_bricaud.R

/**
 * A simple [Fragment] subclass.
 * Use the [ScanQrCode.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScanQrCode : Fragment() {

    private val contract = CameraActivityContract()
    private val getQrCode = registerForActivityResult(contract) { json: String? ->
        if(json != null) {
            print(json)
            // Save in db the json
        } else {
            print("No Json detected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getQrCode.launch(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_qr_code, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameter
         * @return A new instance of fragment ScanQrCode.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ScanQrCode().apply {}
    }
}