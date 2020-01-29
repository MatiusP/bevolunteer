package org.dzedzich.volunteers.comments.ui.views

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.github.siyamed.shapeimageview.RoundedImageView
import kotterknife.bindView
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.tasks.data.models.Comment
import java.util.*

/**
 * Created by aleksejskrobot on 27.05.17.
 */
class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    var context: Context? = null
    private val comments: MutableList<Comment> = ArrayList()
    private val originalList: MutableList<Comment> = ArrayList()

    fun loadComments(list: List<Comment>) {
        originalList.clear()
        comments.clear()
        originalList.addAll(list)
        comments.addAll(originalList.reversed())
        notifyDataSetChanged()
    }

    fun addComment(comment: Comment) {
        originalList.add(comment)
        comments.clear()
        comments.addAll(originalList.reversed())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]

        Glide.with(context).load(comment.author?.avatar).asBitmap().placeholder(R.drawable.comment_avatar).into(holder.ava)

        holder.name.text = comment.author?.name ?: context?.resources?.getString(R.string.anonymus)
        holder.date.text = comment.formatedDate
        holder.message.text = comment.message
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false))
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class ViewHolder(item: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(item) {

        val date: TextView by bindView(R.id.datetime)
        val name: TextView by bindView(R.id.name)
        val message: TextView by bindView(R.id.message)
        val ava: RoundedImageView by bindView(R.id.avatar)

    }

}