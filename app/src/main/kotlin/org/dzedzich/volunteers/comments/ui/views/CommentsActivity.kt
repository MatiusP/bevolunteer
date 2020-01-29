package org.dzedzich.volunteers.comments.ui.views

import android.app.ProgressDialog
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.haipclick.app.root.VolunteersApp
import kotlinx.android.synthetic.main.view_comments.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.comments.di.DaggerCommentsComponent
import org.dzedzich.volunteers.comments.ui.presenters.CommentsPresenter
import org.dzedzich.volunteers.root.structure.BaseActivity
import org.dzedzich.volunteers.tasks.data.models.Comment
import org.dzedzich.volunteers.utils.Constants
import org.jetbrains.anko.onClick
import javax.inject.Inject

/**
 * Created by aleksejskrobot on 27.05.17.
 */
class CommentsActivity: BaseActivity(), ICommentView {

    override var taskId: Int? = 0
    lateinit var progress: ProgressDialog

    init {
        DaggerCommentsComponent.builder()
                .applicationComponent(VolunteersApp.component)
                .build()
                .inject(this)
    }

    @Inject lateinit var presenter: CommentsPresenter
    @Inject lateinit var adapter: CommentsAdapter

    override fun toolbarPreference() {
        toolbar.title = resources.getString(R.string.comments_title)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_comments)

        progress = ProgressDialog(this)
        progress.setMessage("Waiting, please...")

        comments.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        comments.adapter = adapter

        toolbarPreference()

        taskId = intent?.extras?.getInt(Constants.BUNDLES.TASK_ID)

        presenter.bindView(this)

        refresh.setColorScheme(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark)
        refresh.setOnRefreshListener(presenter)

        send.onClick {
            progress.show()
            presenter.sendMessage()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun renderComments(comments: List<Comment>) {
        adapter.context = baseContext
        adapter.loadComments(comments)
        no_results.visibility = if(comments.isNotEmpty()) View.GONE else View.VISIBLE
    }

    override fun getRefreshLayout(): androidx.swiperefreshlayout.widget.SwipeRefreshLayout {
        return findViewById(R.id.refresh) as androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    }

    override fun addNewComment(comment: Comment) {
        this.comment.setText("")
        adapter.addComment(comment)
    }

    override fun getMessageFieldText(): String? {
        return comment.text.toString()
    }

    override fun dissmissDialog() {
        progress.dismiss()
    }
}