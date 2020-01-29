package org.dzedzich.volunteers.tasks.ui.views.models

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.tasks.ui.views.ITaskView
import org.dzedzich.volunteers.tasks.ui.views.adapters.StarsAdapter
import org.dzedzich.volunteers.tasks.ui.views.adapters.VotingAdapter
import org.dzedzich.volunteers.utils.ItemClickSupport

/**
 * Created by aleksejskrobot on 31.05.17.
 */
class VolunterVotingItem(private val volunteer: User): IAdapterVotingItem {

    override fun render(holder: VotingAdapter.ViewHolder?, context: Context?) {

        Glide.with(context).load(volunteer.avatar).asBitmap().placeholder(R.drawable.comment_avatar).into(holder?.avatar)
        holder?.name?.text = volunteer.name
        holder?.question?.visibility = View.GONE

        val adapter = StarsAdapter()
        val list = holder?.starsList
        list?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        list?.adapter = adapter

        adapter.fillStars((volunteer.vote?.voteValue ?: 1) - 1)

        ItemClickSupport.addTo(list).setOnItemClickListener { recyclerView, position, v ->
            (context as ITaskView).vote(volunteer.id, position+1)
            adapter.fillStars(position)
        }
    }


}