package org.dzedzich.volunteers.tasks.ui.views.adapters

import android.content.Context
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotterknife.bindView
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.root.openScreen
import org.dzedzich.volunteers.tasks.data.models.Task
import org.dzedzich.volunteers.tasks.ui.views.impl.TaskActivity
import org.dzedzich.volunteers.utils.Constants
import org.jetbrains.anko.onClick
import java.util.*


/**
 * Created by alexscrobot on 15.05.17.
 */
class TaskAdapter : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    var list: MutableList<Task?> = ArrayList()
    var context: Context? = null

    fun loadList(list: List<Task?>?) {
        clear()
        this.list.addAll(list ?: ArrayList())
        notifyDataSetChanged()
    }

    fun addTask(it: Task?) {
        this.list.add(it)
        notifyDataSetChanged()
    }

    fun clear() {
        this.list.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = list[position]

        Log.d("TaskAdapter", "size: ${list.size}, $position, ${task?.title}")

        holder.title.text = task?.title
        holder.started.text = "${context?.resources?.getString(R.string.action_when)}: ${task?.formatedDate}"
        holder.company.text = task?.company
        holder.address.text = task?.address
        holder.duration.text = "${context?.resources?.getString(R.string.words_hour)}: ${task?.duration}"
        holder.participants.text = "${task?.membersCount} / ${task?.participantsCount}"
        holder.comments.text = task?.comments.toString()

        holder.card.onClick {
            val bundle = Bundle()
            bundle.putInt(Constants.BUNDLES.TASK_ID, task?.id as Int)
            context?.openScreen(TaskActivity::class.java, bundle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false))
    }

    class ViewHolder(item: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(item) {

        val card: androidx.cardview.widget.CardView by bindView(R.id.task_card)
        val started: TextView by bindView(R.id.started_at)
        val title: TextView by bindView(R.id.title)
        val company: TextView by bindView(R.id.company)
        val address: TextView by bindView(R.id.address)
        val duration: TextView by bindView(R.id.duration)
        val participants: TextView by bindView(R.id.participants)
        val comments: TextView by bindView(R.id.comments)

    }
}