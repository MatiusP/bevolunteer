package org.dzedzich.volunteers.comments.business

import android.util.Log
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.root.text
import org.dzedzich.volunteers.tasks.data.TaskRepository
import org.dzedzich.volunteers.tasks.data.models.Comment
import rx.Observable

/**
 * Created by aleksejskrobot on 27.05.17.
 */
class CommentsInteractor(private val repository: TaskRepository) {

    fun loadComments(id: Int?): Observable<List<Comment>> {
        return repository.loadComments(id ?: 0)
    }

    fun  sendComment(id: Int?, message: String?): Observable<Comment>? {
        Log.d("sendComment", "id: $id, message: $message")

        if(id != null && id > 0 && message != null && message.isNotEmpty()) {
            return repository.sendMessage(id, message)
        } else {
            text(R.string.comments_error)
            return null
        }

    }

}