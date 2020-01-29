package org.dzedzich.volunteers.root

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.StringRes
import android.widget.Toast
import com.haipclick.app.root.VolunteersApp
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.utils.PreferenceManager
import java.io.ByteArrayOutputStream
import java.io.IOException




fun user(): User? {
    return PreferenceManager(VolunteersApp.context).user()
}

fun Context.openScreenInNewTask(screen: Class<*>) {

    if(this is Activity) {
        val intent = Intent(this, screen)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_ANIMATION
        this.startActivity(intent)
        finish()
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    } else {
        Toast.makeText(this, "Couldn't open new screen", Toast.LENGTH_SHORT).show()
    }

}

fun Bitmap.resize(maxSize: Int): Bitmap {
    var width = this.width
    var height = this.height

    val bitmapRatio = width.toFloat() / height.toFloat()
    if (bitmapRatio > 1) {
        width = maxSize
        height = (width / bitmapRatio).toInt()
    } else {
        height = maxSize
        width = (height * bitmapRatio).toInt()
    }

    return Bitmap.createScaledBitmap(this, width, height, true)
}

fun Bitmap.toByte(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    val byteArray = stream.toByteArray()
    return byteArray
}

fun Context.openScreen(screen: Class<*>) {

    if(this is Activity) {
        this.startActivity(Intent(this, screen))
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    } else {
        Toast.makeText(this, "Couldn't open new screen", Toast.LENGTH_SHORT).show()
    }

}

fun Context.openScreen(screen: Class<*>, bundle: Bundle) {

    if(this is Activity) {
        val intent = Intent(this, screen)
        intent.putExtras(bundle)
        this.startActivity(intent)
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    } else {
        Toast.makeText(this, "Couldn't open new screen", Toast.LENGTH_SHORT).show()
    }

}


fun Context.openScreenInNewTask(screen: Class<*>, bundle: Bundle) {

    if(this is Activity) {
        val intent = Intent(this, screen)
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_ANIMATION
        this.startActivity(intent)
        finish()
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    } else {
        Toast.makeText(this, "Couldn't open new screen", Toast.LENGTH_SHORT).show()
    }

}

fun Context.share(title: String?, body: String?) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title)
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "$body \n Источник: https://play.google.com/store/apps/details?id=org.dzedzich.volunteers")

    startActivity(Intent.createChooser(sharingIntent, VolunteersApp.context.resources.getString(R.string.action_share)))
}

@Throws(IOException::class)
fun Bitmap.modifyOrientation(image_absolute_path: String?): Bitmap {
    val ei = ExifInterface(image_absolute_path)
    val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> return this.rotate(90f)

        ExifInterface.ORIENTATION_ROTATE_180 -> return this.rotate(180f)

        ExifInterface.ORIENTATION_ROTATE_270 -> return this.rotate(270f)

        ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> return this.flip(true, false)

        ExifInterface.ORIENTATION_FLIP_VERTICAL -> return this.flip(false, true)

        else -> return this
    }
}

fun Uri.getPath(context: Context): String {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(this, projection, null, null, null)
    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor.moveToFirst()
    return cursor.getString(column_index)
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degrees)
    return createBitmap(this, 0, 0, this.width, this.height, matrix, true)
}

fun Bitmap.flip(horizontal: Boolean, vertical: Boolean): Bitmap {
    val matrix = Matrix()
    matrix.preScale(if (horizontal) -1.0F else 1.0F, if (vertical) -1.0F else 1.0F)
    return createBitmap(this, 0, 0, this.width, this.height, matrix, true)
}

fun text(message: String?) {
    Toast.makeText(VolunteersApp.context, message, Toast.LENGTH_SHORT).show()
}

fun text(@StringRes id: Int) {
    Toast.makeText(VolunteersApp.context, VolunteersApp.context.resources.getString(id), Toast.LENGTH_SHORT).show()
}