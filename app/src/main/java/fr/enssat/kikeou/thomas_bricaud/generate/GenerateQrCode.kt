package fr.enssat.kikeou.thomas_bricaud.generate

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.enssat.kikeou.thomas_bricaud.MainActivity
import fr.enssat.kikeou.thomas_bricaud.R
import fr.enssat.kikeou.thomas_bricaud.database.*
import net.glxn.qrgen.android.QRCode

/**
 * A simple [Fragment] subclass.
 * Use the [GenerateQrCode.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenerateQrCode : Fragment() {

    private lateinit var personRepository: PersonRepository
    private lateinit var weekRepository: WeekRepository
    private val NO_COMPRESSION = 100
    private val QR_CODE_FILE_NAME = "KikeOuQRCode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context != null) {
            personRepository = (context as MainActivity).personRepository
            weekRepository = (context as MainActivity).weekRepository
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_generate_qr_code, container, false)
        val btnGenerate = v.findViewById<Button>(R.id.generate)
        btnGenerate.setOnClickListener {
            generate(v)
        }
        return v
    }

    private fun generate(v: View) {
        val num = 1
        val name = v.findViewById<EditText>(R.id.name).text.toString()
        val mail = v.findViewById<EditText>(R.id.email).text.toString()
        val tel = v.findViewById<EditText>(R.id.phone).text.toString()
        val day1 = v.findViewById<EditText>(R.id.monday).text.toString()
        val day2 = v.findViewById<EditText>(R.id.thursday).text.toString()
        val day3 = v.findViewById<EditText>(R.id.wednesday).text.toString()
        val day4 = v.findViewById<EditText>(R.id.tuesday).text.toString()
        val day5 = v.findViewById<EditText>(R.id.friday).text.toString()


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
            weekRepository.updateWeek(week)
        }

        var json = "{Test: Test}"
        val qrCodeView = v.findViewById<ImageView>(R.id.qrCode)
        val share = v.findViewById<FloatingActionButton>(R.id.share)

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
