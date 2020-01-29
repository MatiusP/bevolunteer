package org.dzedzich.volunteers.tasks.ui.views.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotterknife.bindView
import org.dzedzich.volunteers.R
import java.util.*

/**
 * Created by aleksejskrobot on 31.05.17.
 */
class StarsAdapter : RecyclerView.Adapter<StarsAdapter.ViewHolder>(){

    private var context: Context? = null
    private val map: MutableMap<Int, Boolean> = Hashtable()

    init {
        map.put(0, false)
        map.put(1, false)
        map.put(2, false)
        map.put(3, false)
        map.put(4, false)
    }

    fun fillStars(position: Int) {
        map
           .entries
           .forEach {
               map.put(it.key, it.key <= position)
           }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voted = map[position]

        if(voted == true) {
            holder.voted.visibility = View.VISIBLE
            holder.notVoted.visibility = View.GONE
        } else {
            holder.voted.visibility = View.GONE
            holder.notVoted.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        return map.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_vote, parent, false))
    }

    class ViewHolder(val item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item)  {
        val voted: ImageView by bindView(R.id.voted)
        val notVoted: ImageView by bindView(R.id.not_voted)
    }
}