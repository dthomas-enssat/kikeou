package fr.enssat.kikeou.thomas_bricaud.generate

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.enssat.kikeou.thomas_bricaud.MainActivity
import fr.enssat.kikeou.thomas_bricaud.R
import fr.enssat.kikeou.thomas_bricaud.database.*
import fr.enssat.kikeou.thomas_bricaud.databinding.FragmentGenerateQrCodeBinding
import net.glxn.qrgen.android.QRCode
import java.util.*

class GenerateQrCodeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    //binding information
    private lateinit var binding: FragmentGenerateQrCodeBinding
    private lateinit var viewModel: GenerateQrCodeModel
    private lateinit var viewModelFactory: GenerateQrCodeModelFactory

    // element in the fragment
    private lateinit var spinner: Spinner;
    private lateinit var adapter: ArrayAdapter<String>;

    // qr_code parameters
    private val NO_COMPRESSION = 100
    private val QR_CODE_FILE_NAME = "KikeOuQRCode"

    // database repository
    private lateinit var personRepository: PersonRepository
    private lateinit var weekRepository: WeekRepository

    // initialise database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context != null) {
            personRepository = (context as MainActivity).personRepository
            weekRepository = (context as MainActivity).weekRepository
        }
    }

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

        val num = binding.banner.spinner.selectedItem.toString().toInt()
        val name = binding.person.name.toString()
        val mail = binding.person.email.toString()
        val tel = binding.person.phone.toString()
        val day1 = binding.week.monday.toString()
        val day2 = binding.week.tuesday.toString()
        val day3 = binding.week.wednesday.toString()
        val day4 = binding.week.thursday.toString()
        val day5 = binding.week.friday.toString()
        val day6 = binding.week.saturday.toString()


        val persons = personRepository.getPerson(name)
        if (persons.isEmpty()) {
            val person = Person(name, null, Contact(mail, tel, null))
            personRepository.insert(person)
        }
        val weeks = weekRepository.getWeek(num, name)

        if (weeks.isEmpty()) {
            val week = Week(num, name, day1, day2, day3, day4, day5)
            weekRepository.insert(week)
        } else {
            val week = weeks[0]
            week.day1 = day1
            week.day2 = day2
            week.day3 = day3
            week.day4 = day4
            week.day5 = day5
            week.day6 = day6
            weekRepository.updateWeek(week)
        }

        var json = "{Test: Test}"
        val qrCodeView = v.findViewById<ImageView>(R.id.qr_code)
        val share = v.findViewById<FloatingActionButton>(R.id.sharing_qr_code)

        val bitmap = QRCode.from(json).withSize(500,500).withCharset("UTF-8").bitmap()
        qrCodeView.setImageBitmap(bitmap)
        qrCodeView.visibility = View.VISIBLE

        try {
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, QR_CODE_FILE_NAME)
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            values.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_DOCUMENTS.toString() + "/Kikeou/"
            )
            var uri = context!!.contentResolver.insert(MediaStore.Files.getContentUri("external"), values)
            val out = uri?.let { context!!.contentResolver.openOutputStream(it) }
            bitmap.compress(Bitmap.CompressFormat.PNG, NO_COMPRESSION, out)
            out?.flush()
            out?.close()
            share.visibility = View.VISIBLE

        } catch (e: Exception) {
            e.printStackTrace()
        }

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
