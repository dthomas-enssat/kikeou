package fr.enssat.kikeou.thomas_bricaud.generate

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import fr.enssat.kikeou.thomas_bricaud.R
import fr.enssat.kikeou.thomas_bricaud.databinding.FragmentGenerateQrCodeBinding
import java.util.*

class GenerateQrCodeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    //binding information
    private lateinit var binding: FragmentGenerateQrCodeBinding
    private lateinit var viewModel: GenerateQrCodeModel
    private lateinit var viewModelFactory: GenerateQrCodeModelFactory

    // element in the fragment
    private lateinit var spinner: Spinner;
    private lateinit var adapter: ArrayAdapter<String>;

    // generate view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate the layout for this fragment
        binding = FragmentGenerateQrCodeBinding.inflate(inflater, container, false)

        // integrate viewmodel for databinding
        viewModelFactory = GenerateQrCodeModelFactory(
            binding.person.name.text.toString(),
            binding.person.email.text.toString(),
            binding.person.phone.text.toString(),
            binding.week.monday.text.toString(),
            binding.week.tuesday.text.toString(),
            binding.week.wednesday.text.toString(),
            binding.week.thursday.text.toString(),
            binding.week.friday.text.toString(),
            binding.week.saturday.text.toString(),
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(GenerateQrCodeModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner

        // setter on initialization
        binding.person.name.setText(viewModel.name)
        binding.person.email.setText(viewModel.email)
        binding.person.phone.setText(viewModel.phone)

        binding.week.monday.setText(viewModel.monday)
        binding.week.tuesday.setText(viewModel.tuesday)
        binding.week.wednesday.setText(viewModel.wednesday)
        binding.week.thursday.setText(viewModel.thursday)
        binding.week.friday.setText(viewModel.friday)
        binding.week.saturday.setText(viewModel.saturday)

        // binding when texts change
        binding.person.name.doAfterTextChanged { viewModel.name = it.toString() }
        binding.person.email.doAfterTextChanged { viewModel.email = it.toString() }
        binding.person.phone.doAfterTextChanged { viewModel.phone = it.toString() }

        binding.week.monday.doAfterTextChanged { viewModel.monday = it.toString() }
        binding.week.tuesday.doAfterTextChanged { viewModel.tuesday = it.toString() }
        binding.week.wednesday.doAfterTextChanged { viewModel.wednesday = it.toString() }
        binding.week.thursday.doAfterTextChanged { viewModel.thursday = it.toString() }
        binding.week.friday.doAfterTextChanged { viewModel.friday = it.toString() }
        binding.week.saturday.doAfterTextChanged { viewModel.saturday = it.toString() }

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

        // generate qr code button
        binding.generate.setOnClickListener {
            generate(binding.root)
        }

        return binding.root
    }

    // generate qr code element
    private fun generate(v: View) {
        return
    }

    // for spinner element
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val spinner: Spinner = parent.findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
    }
}
