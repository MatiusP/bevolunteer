package org.dzedzich.volunteers.tasks.ui.presenters

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.comments.ui.views.CommentsActivity
import org.dzedzich.volunteers.root.openScreen
import org.dzedzich.volunteers.root.share
import org.dzedzich.volunteers.root.structure.Presenter
import org.dzedzich.volunteers.tasks.business.TaskInteractor
import org.dzedzich.volunteers.tasks.data.models.Task
import org.dzedzich.volunteers.tasks.ui.views.ITaskView
import org.dzedzich.volunteers.utils.Constants

/**
 * Created by alexscrobot on 16.05.17.
 */
class TaskPresenter(private val interactor: TaskInteractor) : Presenter<ITaskView>(), View.OnClickListener {

    override var view: ITaskView? = null

    override fun bindView(view: ITaskView) {
        super.bindView(view)

        Log.d("task_id", view.id.toString())

        loadTaskInfo()
    }

    fun loadTaskInfo() {
        subscriptions.add(interactor.task(view?.id).subscribe({ view?.loadTask(it) }, { error -> error.printStackTrace() }))
    }

    fun menuItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId) {
            R.id.action_comments -> openComments()
            R.id.action_share -> shareTask()
        }

        return false
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.address -> openMap()
            R.id.subscribe -> subscribeAction()
        }
    }

    private fun subscribeAction() {
        subscriptions
                .add(interactor
                        .takeAPart(view?.id)
                        .subscribe(
                                { view?.loadTask(it) },
                                { error -> error.printStackTrace() }
                        ))
    }

    private fun openMap() {
        val map = "http://maps.google.co.in/maps?q=" + view?.getAddress()
        view?.getContext()?.let { context ->
            startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(map)), null)
        }
    }

    private fun openComments() {
        val bundle = Bundle()
        bundle.putInt(Constants.BUNDLES.TASK_ID, view?.id ?: 0)

        view?.getContext()?.openScreen(CommentsActivity::class.java, bundle)
    }

    private fun shareTask() {
        val sharingData = view?.getSharingData()
        view?.getContext()?.share("Задание: ${sharingData?.get("title")}", "Задание: ${sharingData?.get("title")} \n ${sharingData?.get("body")}")
    }

    fun prepareVotingList(task: Task?) {
        subscriptions.add(interactor.makeVolunteersVotingList(task?.volunteers).subscribe({ view?.loadVolunteerVotePosition(it) }, Throwable::printStackTrace))
        subscriptions.add(interactor.makeCompanyVotingList(task?.companyVoting).subscribe({ view?.loadCompanyVotePosition(it) }, Throwable::printStackTrace))
    }

    fun voteForCompany(id: String, vote: Int, taskId: Int?, companyId: Int?) {
        subscriptions.add(interactor.voteCompany(id, vote, taskId, companyId).subscribe({  }, Throwable::printStackTrace))
    }

    fun vote(id: Int?, aimId: Int, vote: Int) {
        subscriptions.add(interactor.vote(id, aimId, vote).subscribe({  }, Throwable::printStackTrace))
    }

}