package ru.android.dogbreedsapplication.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import ru.bey_sviatoslav.android.dogbreedsapplication.R
import ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

const val TAGUtils = "Utils"
fun getSubbreedsSuffix(count: Int): String{
    if(count == 1){
        return "subbreed"
    }else{
        return "subbreeds"
    }
}

fun getPhotosSuffix(count: Int): String{
    if(count == 1){
        return "photo"
    }else{
        return "photos"
    }
}

fun sharePhoto(url: String?, context: Context){
    Picasso.with(context.applicationContext).load(url)
        .into(object : Target {
            override fun onBitmapFailed() {
                Log.d(TAGUtils, "Ошибка в загрузке")
                MaterialAlertDialogBuilder(context)
                    .setTitle(context.resources.getString(R.string.error_title))
                    .setMessage(context.resources.getString(R.string.error_text))
                    .setPositiveButton(context.resources.getString(R.string.ok)) { dialog, which -> }
                    .show()
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                Log.d(TAGUtils, "Загрузилось")
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap!!, context))
                    type = "image/*"
                }
                context.startActivity(
                    Intent.createChooser(shareIntent, context.resources.getText(
                        R.string.send_to)))
            }
        })
}

fun getLocalBitmapUri(bmp: Bitmap, context: Context): Uri? {
    var bmpUri: Uri? = null
    try {
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "share_image_" + System.currentTimeMillis() + ".png"
        )
        val out = FileOutputStream(file)
        bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.close()
        bmpUri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmpUri
}

fun getFavoriteDogImageFromLink(dogImageLink: String): _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage {
    val parts = dogImageLink.split('/')
    val breed = parts[parts.size - 2]
    return _root_ide_package_.ru.android.dogbreedsapplication.favorite_dogs_db.FavoriteDogImage(
        dogImageLink,
        breed
    )
}