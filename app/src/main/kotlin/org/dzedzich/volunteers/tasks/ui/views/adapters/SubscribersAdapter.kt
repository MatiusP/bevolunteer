package org.dzedzich.volunteers.tasks.ui.views.adapters

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.github.siyamed.shapeimageview.RoundedImageView
import kotterknife.bindView
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.profile.ui.views.impl.VolunteerProfileActivity
import org.dzedzich.volunteers.root.openScreen
import org.dzedzich.volunteers.utils.Constants
import org.jetbrains.anko.onClick
import java.util.*

/**
 * Created by alexscrobot on 30.05.17.
 */
class SubscribersAdapter: RecyclerView.Adapter<SubscribersAdapter.ViewHolder>() {
    val participants: MutableList<User?> = ArrayList()
    private var context: Context? = null

    fun loadParticipants(list: List<User?>) {
        clearAdapter()
        participants.addAll(list.reversed())
        notifyDataSetChanged()
    }

    fun clearAdapter(): SubscribersAdapter {
        participants.clear()

        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_participant, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
                .load(participants[position]?.avatar)
                .asBitmap()
                .placeholder(R.drawable.comment_avatar)
                .animate(R.anim.fade_in)
                .into(holder.avatar)

        holder.avatar.onClick {
            val bundle = Bundle()
            bundle.putInt(Constants.BUNDLES.USER_ID, participants[position]?.id ?: 0)
            context?.openScreen(VolunteerProfileActivity::class.java, bundle)
        }
    }

    override fun getItemCount(): Int {
        return participants.size
    }

    class ViewHolder(val view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val avatar: RoundedImageView by bindView(R.id.avatar)
    }

}