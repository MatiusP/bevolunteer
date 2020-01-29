package org.dzedzich.volunteers.profile.ui.views.impl

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.widget.PopupMenu
import android.util.Log
import android.view.*
import android.widget.EditText
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.haipclick.app.root.VolunteersApp
import kotlinx.android.synthetic.main.fragment_profile.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.profile.di.DaggerProfileComponent
import org.dzedzich.volunteers.profile.di.ProfileModule
import org.dzedzich.volunteers.profile.ui.presenters.UserProfilePresenter
import org.dzedzich.volunteers.profile.ui.views.IUserProfileView
import org.dzedzich.volunteers.root.getPath
import org.dzedzich.volunteers.root.modifyOrientation
import org.dzedzich.volunteers.root.structure.BaseFragment
import org.jetbrains.anko.onClick
import java.io.IOException
import javax.inject.Inject
import android.graphics.Matrix
import android.webkit.URLUtil

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class UserProfileFragment: BaseFragment(), IUserProfileView {
    override var user: User? = null

    private val REQUEST_GALLERY: Int = 1001
    private lateinit var avatarBitmap: Bitmap

    override var layout: Int = R.layout.fragment_profile

    private object Holder { val INSTANCE = UserProfileFragment() }

    companion object {
        val instance: UserProfileFragment by lazy { Holder.INSTANCE }
        const val ROTATE_RIGHT = 90
        const val ROTATE_LEFT = -90
        const val ROTATE_NO = 0
    }

    @Inject lateinit var presenter: UserProfilePresenter

    init {
        DaggerProfileComponent.builder()
                .applicationComponent(VolunteersApp.component)
                .profileModule(ProfileModule())
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.bindView(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
    }

    override fun toolbarPreference() {
        activity.supportActionBar?.title = resources.getString(R.string.profile_title)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_check)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Log.d(TAG, activity.supportActionBar.toString())
    }

    override fun onResume() {
        super.onResume()
        presenter.bindView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_user_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return presenter.onOptionsItemSelected(item)
    }

    override fun renderProfile() {
        rating.text = "${user?.ratingYear}\n${user?.rating}"

        Glide.with(context).load(user?.avatar).asBitmap().into(object : SimpleTarget<Bitmap>(100,100){
            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                resource?.let {
                    avatarBitmap = it
                    avatar.setImageBitmap(avatarBitmap)
                }
            }
        })
        tasks.text = user?.completedTasksCount?.toString()
        place.text = "${user?.place} из ${user?.volunteersCount}"
        if((user?.completedTasksCount as Int) > 10) {
            tasks_count.visibility = View.VISIBLE
            showSnackBar()
        }
        avatar.onClick {
            avatarPopup()
        }
        take_picture.onClick {
            galleryIntent()
        }
        name.setText(user?.name)
        phone.setText(user?.phone)
        site.setText(user?.site)
        slogan?.setText(user?.slogan)
        about?.setText(user?.about)
        showPhoneNumber?.isChecked = user?.isViewPhoneNumber == 1
        save.onClick {
            if(getSite().isNullOrEmpty() or URLUtil.isValidUrl(getSite())) {
                siteLayout.error = null
                presenter.saveUserData()
            } else siteLayout.error = getString(R.string.url_is_not_valid)
        }
    }

    private fun showSnackBar() {
        add_task_layout.visibility = View.VISIBLE
        add_task_layout.onClick {

            val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_task, null)
            val taskBody = view.findViewById<EditText>(R.id.task_body)
            AlertDialog.Builder(context)
                    .setTitle(R.string.profile_add_task_describe)
                    .setView(view)
                    .setCancelable(false)
                    .setNegativeButton(R.string.button_cancel) { dialog, _ -> dialog.dismiss() }
                    .setPositiveButton(R.string.button_add) { dialog, _ -> presenter.sendTask(taskBody.text.toString()); dialog.dismiss() }
                    .create()
                    .show()

        }
    }

    fun avatarPopup() {
        val popup = PopupMenu(context, avatar)
        popup.menuInflater.inflate(R.menu.menu_avatar, popup.menu)

        popup.setOnMenuItemClickListener{
            when(it.itemId) {
                R.id.action_edit_avatar-> galleryIntent()
                R.id.action_rotate_left-> updateAvatar(avatarBitmap, ROTATE_LEFT)
                R.id.action_rotate_right-> updateAvatar(avatarBitmap, ROTATE_RIGHT)
            }
            true
        }

        popup.show()
    }

    fun galleryIntent() {
        val intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // Show only images, no videos or anything else
        intent.type = "image/*"
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, getString(R.string.profile_take_photo)), REQUEST_GALLERY)
    }

    private fun updateAvatar(btm: Bitmap, angle: Int) {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        avatarBitmap = Bitmap.createBitmap(btm, 0, 0, btm.width, btm.height, matrix, true)
        avatar.setImageBitmap(avatarBitmap)
        presenter.sendAvatar(avatarBitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_GALLERY -> {
                if (resultCode == RESULT_OK && data?.data != null) {
                    try {
                        Glide.with(context).load(data.data).asBitmap().into(object : SimpleTarget<Bitmap>(100,100){
                            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                                resource?.modifyOrientation(data?.data?.getPath(context))?.let {
                                    updateAvatar(it, ROTATE_NO)
                                }
                            }
                        })
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Glide.with(context).load(R.drawable.avatar).into(avatar)
                    }
                }
            }
        }
    }

    override fun getName(): String? {
        return name.text.toString()
    }

    override fun getPhone(): String? {
        return phone.text.toString()
    }

    override fun getSite(): String? {
        return site.text.toString()
    }

    override fun getSlogan(): String? {
        return slogan.text.toString()
    }

    override fun getAbout(): String? {
        return about.text.toString()
    }

    override fun isViewPhoneNumber(): Int? {
        return if(showPhoneNumber.isChecked) 1 else 0
    }
}