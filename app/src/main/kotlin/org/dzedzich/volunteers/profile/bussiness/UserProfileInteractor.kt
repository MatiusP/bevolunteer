package org.dzedzich.volunteers.profile.bussiness

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.haipclick.app.root.VolunteersApp.Companion.context
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.profile.data.models.AvatarUploadResponse
import org.dzedzich.volunteers.profile.data.models.TaskQuery
import org.dzedzich.volunteers.profile.data.repos.UserProfileRepository
import rx.Observable
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


/**
 * Created by aleksejskrobot on 14.05.17.
 */
class UserProfileInteractor(private val repository: UserProfileRepository) {

    fun profile(): Observable<User> {
        return repository.user()
    }

    fun saveUser(name: String?, phone: String?, site: String?, slogan: String?, about: String?, viewPhoneNumber: Int?): Observable<User> {
        val map: MutableMap<String, String?> = HashMap()
        map.put("name", name)
        map.put("phone", phone)
        map.put("site", site)
        map.put("slogan", slogan)
        map.put("about", about)
        map.put("viewPhoneNumber", viewPhoneNumber.toString())

        return repository.saveUser(map)
    }

    private fun getImageUri(inContext: Context?, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        inImage?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext?.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun sendAvatar(context: Context?, bitmap: Bitmap?): Observable<AvatarUploadResponse> {

        val uri = getImageUri(context, bitmap)

        val file = File(getPath(uri))

        val requestFile = RequestBody.create(
                MediaType.parse(context?.contentResolver?.getType(uri)),
                file
        )

        val body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)

        return repository.sendImage(body)
    }

    private fun getPath(uri: Uri): String {
        val data = arrayOf(MediaStore.Images.Media.DATA)
        val loader = androidx.loader.content.CursorLoader(context, uri, data, null, null, null)
        loader.loadInBackground()?.let { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        }
        return ""
    }

    fun makeTask(task: String): Observable<TaskQuery> {
        return repository.makeTask(task)
    }

}