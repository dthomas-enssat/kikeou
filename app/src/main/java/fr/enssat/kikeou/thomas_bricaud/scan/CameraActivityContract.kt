package fr.enssat.kikeou.thomas_bricaud.scan

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class CameraActivityContract : ActivityResultContract<Void, String?>() {

    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(context, CameraActivity::class.java)
    }

    override fun parseResult(resultCode: Int, result: Intent?): String? {
        return when (resultCode) {
            Activity.RESULT_OK -> result?.getStringExtra("json")
            else -> null
        }
    }
}