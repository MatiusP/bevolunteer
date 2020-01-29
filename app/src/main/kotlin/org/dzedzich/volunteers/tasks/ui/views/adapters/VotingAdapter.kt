package org.dzedzich.volunteers.tasks.ui.views.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.github.siyamed.shapeimageview.RoundedImageView
import kotterknife.bindView
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.tasks.ui.views.adapters.VotingAdapter.ViewHolder
import org.dzedzich.volunteers.tasks.ui.views.models.IAdapterVotingItem
import java.util.*

/**
 * Created by aleksejskrobot on 31.05.17.
 */
class VotingAdapter<T: IAdapterVotingItem> : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {

    private var context: Context? = null
    private val list: MutableList<T?> = ArrayList()

    fun loadItem(item: T?) {
        this.list.add(item)
        notifyDataSetChanged()
    }

    fun loadList(list: List<T>) {
        this.clearList()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList(): VotingAdapter<T> {
        this.list.clear()

        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent?.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_voting, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position]?.render(holder, context)
    }

    class ViewHolder(val item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item)  {
        val avatar: RoundedImageView by bindView(R.id.avatar)
        val name: TextView by bindView(R.id.name)
        val question: ImageButton by bindView(R.id.question)
        val starsList: androidx.recyclerview.widget.RecyclerView by bindView(R.id.vote_list)
    }
}