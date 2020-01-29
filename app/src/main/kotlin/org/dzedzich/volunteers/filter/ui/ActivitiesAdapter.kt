package org.dzedzich.volunteers.filter.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import kotterknife.bindView
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.profile.data.models.Activity
import org.jetbrains.anko.onCheckedChange
import java.util.*

/**
 * Created by aleksejskrobot on 03.06.17.
 */
class ActivitiesAdapter(private val view: IFilterView): RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {

    private val activities: MutableList<Activity> = ArrayList()

    fun setList(list: List<Activity>) {
        activities.clear()
        activities.addAll(list)
        notifyDataSetChanged()
    }

    fun getChecked(): List<Activity> {
        return activities.filter { it.checked }
    }

    fun checkItems(activities: List<Activity>?) {
        val arr: MutableList<String?> = ArrayList()

        activities?.forEach { arr.add(it.id) }

        this.activities
                .filter {
                    arr.contains(it.id)
                }
                .forEach {
                    it.checked = true
                }

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val act = activities[position]
        val switch = holder.switch

        switch.text = act.label
        switch.isChecked = act.checked

        switch.onCheckedChange { _, b ->
            act.checked = b
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(view.getContext()).inflate(R.layout.item_activity, parent, false))
    }


    class ViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val switch: Switch by bindView(R.id.activity)
    }

}