package fr.enssat.kikeou.thomas_bricaud.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import fr.enssat.kikeou.thomas_bricaud.R
import fr.enssat.kikeou.thomas_bricaud.databinding.FragmentScanQrCodeBinding
import java.util.*

class ScanQrCodeFragment : Fragment() {
    //binding information
    private lateinit var binding: FragmentScanQrCodeBinding
    private lateinit var viewModel: ScanQrCodeModel
    private lateinit var viewModelFactory: ScanQrCodeModelFactory

    // element in the fragment
    private lateinit var spinner: Spinner;
    private lateinit var adapter: ArrayAdapter<String>;

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
        binding = FragmentScanQrCodeBinding.inflate(inflater, container, false)

        // integrate viewmodel for databinding
        viewModelFactory = ScanQrCodeModelFactory("", "", "", "", "", "", "", "", "")
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScanQrCodeModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner

        // setter on initialization
        binding.person.name.text = viewModel.name
        binding.person.email.text = viewModel.email
        binding.person.phone.text = viewModel.phone

        binding.week.monday.text = viewModel.monday
        binding.week.tuesday.text = viewModel.tuesday
        binding.week.wednesday.text = viewModel.wednesday
        binding.week.thursday.text = viewModel.thursday
        binding.week.friday.text = viewModel.friday
        binding.week.saturday.text = viewModel.saturday

        // spinner
        val weeklyArray = arrayListOf<String>();
        val week: Calendar = Calendar.getInstance()
        for (i in 0..3) {
            var weeks = (week.get(Calendar.WEEK_OF_YEAR) + i) % 52
            if (weeks == 0) weeks = 52;
            weeklyArray.add(weeks.toString())
        }
        adapter = ArrayAdapter(binding.root.context, android.R.layout.simple_spinner_dropdown_item, weeklyArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner = binding.banner.spinner
        spinner.adapter = adapter


        return binding.root
    }
}