package org.dzedzich.volunteers.comments.ui.presenters

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.dzedzich.volunteers.comments.business.CommentsInteractor
import org.dzedzich.volunteers.comments.ui.views.ICommentView
import org.dzedzich.volunteers.root.structure.Presenter

/**
 * Created by aleksejskrobot on 27.05.17.
 */
class CommentsPresenter(private val interactor: CommentsInteractor): Presenter<ICommentView>(), androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {

    override var view: ICommentView? = null

    override fun bindView(view: ICommentView) {
        super.bindView(view)

        subscribe {  }
    }

    override fun onRefresh() {
        val refreshLayout = view?.getRefreshLayout()

        refreshLayout?.isRefreshing = true

        subscribe { refreshLayout?.isRefreshing = false }
    }

    fun subscribe(func: () -> Unit) {
        subscriptions
                .add(interactor
                        .loadComments(view?.taskId)
                        .subscribe(
                                {
                                    view?.renderComments(it)
                                    func.invoke()
                                },
                                { error -> error.printStackTrace() }
                        )
                )
    }

    fun sendMessage() {
        subscriptions
                .add(interactor
                        .sendComment(view?.taskId, view?.getMessageFieldText())
                        ?.subscribe(
                                {
                                    view?.addNewComment(it)
                                },
                                {
                                    error -> error.printStackTrace()
                                },
                                {
                                    view?.dissmissDialog()
                                }
                        )
                )
    }
}