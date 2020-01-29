package org.dzedzich.volunteers.feed.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotterknife.bindView
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.feed.data.models.Feed
import org.dzedzich.volunteers.root.share
import org.jetbrains.anko.onClick
import java.util.*


/**
 * Created by alexscrobot on 15.05.17.
 */
class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var feed: MutableList<Feed?> = ArrayList()
    private var context: Context? = null

    fun addFeed(it: List<Feed?>) {
        feed = it.toMutableList()
        notifyDataSetChanged()
    }

    fun clear() {
        feed.clear()
    }

    fun setContext(context: Context?) {
        this.context = context
    }

    fun loadFeed(list: List<Feed?>) {
        feed.clear()
        feed.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return feed.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed = feed[position]

        Glide.with(context)
                .load(feed?.enclosure)
                .skipMemoryCache( false )
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.preview)

        holder.title.text = feed?.title
        holder.desc.text = feed?.body
        holder.datetime.text = feed?.date

        holder.title.onClick {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(feed?.permalink))
            context?.startActivity(browserIntent)
        }

        holder.preview.onClick {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(feed?.permalink))
            context?.startActivity(browserIntent)
        }

        holder.share.onClick {
            context?.share("ссылка: ${feed?.permalink}", "ссылка: ${feed?.permalink}\n ${feed?.title} \n ${feed?.body}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false))
    }


    class ViewHolder(item: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(item) {

        val title: TextView by bindView(R.id.title)
        val datetime: TextView by bindView(R.id.datetime)
        val desc: TextView by bindView(R.id.desc)
        val preview: ImageView by bindView(R.id.preview)
        val share: ImageButton by bindView(R.id.share)

    }
}