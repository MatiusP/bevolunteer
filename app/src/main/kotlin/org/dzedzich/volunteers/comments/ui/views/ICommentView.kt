package org.dzedzich.volunteers.comments.ui.views

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.dzedzich.volunteers.root.structure.IView
import org.dzedzich.volunteers.tasks.data.models.Comment

/**
 * Created by aleksejskrobot on 27.05.17.
 */
interface ICommentView: IView {

    val taskId: Int?

    fun renderComments(comments: List<Comment>)
    fun getRefreshLayout(): androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    fun addNewComment(comment: Comment)
    fun getMessageFieldText(): String?
    fun dissmissDialog()

}