package org.dzedzich.volunteers.tasks.ui.views.models

import android.app.AlertDialog
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.tasks.ui.views.ITaskView
import org.dzedzich.volunteers.tasks.ui.views.adapters.StarsAdapter
import org.dzedzich.volunteers.tasks.ui.views.adapters.VotingAdapter
import org.dzedzich.volunteers.utils.ItemClickSupport
import org.jetbrains.anko.onClick

/**
 * Created by aleksejskrobot on 31.05.17.
 */
class CompanyVotingItem(private val item: CompanyVotePosition): IAdapterVotingItem {

    override fun render(holder: VotingAdapter.ViewHolder?, context: Context?) {

        val dialog = buildDialog(context)

        holder?.avatar?.visibility = View.GONE
        holder?.question?.visibility = View.VISIBLE
        holder?.name?.text = item.title

        val list = holder?.starsList
        val adapter = StarsAdapter()
        list?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        list?.adapter = adapter

        holder?.question?.onClick { dialog?.show() }

        adapter.fillStars(item.position - 1)

        ItemClickSupport.addTo(list).setOnItemClickListener { recyclerView, position, v ->
            (context as ITaskView).voteForCompany(item.id, position+1)
            adapter.fillStars(position)
        }
    }

    private fun buildDialog(context: Context?): AlertDialog? {

        return AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle(item.title)
                .setMessage(item.question)
                .setPositiveButton(R.string.button_cancel, {dialog, which -> dialog.dismiss()})
                .create()

    }

}