package com.example.uioperate.picture_selection

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class PictureMultiple :  ActivityResultContract<Void?, List<Uri>?>() {
    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(Intent.ACTION_GET_CONTENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            .setType("image/*")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<Uri>? {
        if (resultCode == Activity.RESULT_OK) {
            intent?.clipData?.apply {
                val uriList= mutableListOf<Uri>()
                for (i in 0 until itemCount) {
                    val imageUri = getItemAt(i).uri
                    uriList.add(imageUri)
                }
                return uriList
            }
        }
        return null
    }
}