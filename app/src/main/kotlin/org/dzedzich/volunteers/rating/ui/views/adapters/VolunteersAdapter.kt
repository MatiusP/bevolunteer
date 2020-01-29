package org.dzedzich.volunteers.feed.ui

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import de.hdodenhof.circleimageview.CircleImageView
import kotterknife.bindView
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.profile.ui.views.impl.VolunteerProfileActivity
import org.dzedzich.volunteers.rating.data.models.VolunteerShort
import org.dzedzich.volunteers.root.openScreen
import org.dzedzich.volunteers.utils.Constants
import org.jetbrains.anko.onClick
import java.util.*


/**
 * Created by alexscrobot on 15.05.17.
 */
class VolunteersAdapter : RecyclerView.Adapter<VolunteersAdapter.ViewHolder>() {

    private var list: MutableList<VolunteerShort?> = ArrayList()
    private var context: Context? = null

    fun setContext(context: Context?) {
        this.context = context
    }

    fun loadList(list: List<VolunteerShort?>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    fun addList(list: List<VolunteerShort?>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val volunteer = list[position]

        Glide.with(context)
                .load(volunteer?.avatar)
                .asBitmap()
                .placeholder(R.drawable.avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.avatar)

        holder.name.text = volunteer?.name
        holder.slogan.text = volunteer?.slogan
        holder.position.text = "${position+1}."
        holder.rating.text = "${volunteer?.ratingYear?.toInt()}/${volunteer?.rating?.toInt()}"
        holder.tasks.text = "${context?.resources?.getString(R.string.word_tasks)}: ${volunteer?.completedTasksCount}"

        holder.card.onClick {
            val bundle = Bundle()
            bundle.putInt(Constants.BUNDLES.USER_ID, volunteer?.id as Int)
            bundle.putInt(Constants.BUNDLES.USER_POSITION, position+1)
            context?.openScreen(VolunteerProfileActivity::class.java, bundle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rating, parent, false))
    }


    class ViewHolder(item: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(item) {

        val card: androidx.cardview.widget.CardView by bindView(R.id.rating_card)
        val name: TextView by bindView(R.id.name)
        val position: TextView by bindView(R.id.position)
        val rating: TextView by bindView(R.id.rating)
        val tasks: TextView by bindView(R.id.tasks)
        val slogan: TextView by bindView(R.id.slogan)
        val avatar: CircleImageView by bindView(R.id.avatar)

    }
}