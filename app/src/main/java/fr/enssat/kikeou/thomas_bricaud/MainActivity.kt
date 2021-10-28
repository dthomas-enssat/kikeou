package fr.enssat.kikeou.thomas_bricaud

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.glxn.qrgen.android.QRCode

class MainActivity : AppCompatActivity() {

    private val NO_COMPRESSION = 100
    private val QR_CODE_FILE_NAME = "myqrcode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        var button = this.findViewById(R.id.bt_generate_qr_code) as Button
        var qrCodeView = this.findViewById(R.id.qr_code_view) as ImageView
        var json = " { id: string\n" +
                "  name: string\n" +
                " photo: Url?\n" +
                " contact: [ \n" +
                "     { key:  \"email\" , value : string },\n" +
                "     { key: \"tel\", value: string },\n" +
                "     { key: \"face de  book\", value: string},\n" +
                "  ]\n" +
                " week : 43" +
                "  loc: [ \n" +
                "     { day: 1, value: \"teletravail\" },\n" +
                "     { day : 3, value: \"Off\" },\n" +
                "     { day : 5: value: \"WF 036\" } \n" +
                "  ]\n" +
                "}"
        var share = this.findViewById(R.id.share) as FloatingActionButton

        var uri: Uri? = null
        button.setOnClickListener { _ ->
            val bitmap: Bitmap = QRCode.from(json).bitmap()
            qrCodeView.setImageBitmap(bitmap)

            try {
                val values = ContentValues()
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, QR_CODE_FILE_NAME)
                values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                values.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DOCUMENTS.toString() + "/Kikeou/"
                )

                uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), values)
                val out = uri?.let { contentResolver.openOutputStream(it) }
                bitmap.compress(Bitmap.CompressFormat.PNG, NO_COMPRESSION, out)
                out?.flush()
                out?.close()
                share.visibility = View.VISIBLE

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        share.setOnClickListener { _ ->
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/png"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(intent, "Share Image"))
        }
    }
}