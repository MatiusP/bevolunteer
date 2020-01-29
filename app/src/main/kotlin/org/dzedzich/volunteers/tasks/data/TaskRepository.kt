package org.dzedzich.volunteers.tasks.data

import com.github.vkguests.utils.rx.RxSchedulers
import com.haipclick.app.root.VolunteersApp
import com.haipclick.app.root.structure.ServiceRepository
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.tasks.data.models.*
import org.dzedzich.volunteers.tasks.ui.views.models.CompanyVotePosition
import org.dzedzich.volunteers.utils.Constants
import rx.Observable
import java.util.*

/**
 * Created by aleksejskrobot on 15.05.17.
 */
class TaskRepository : ServiceRepository<TaskRSI>(TaskRSI::class.java) {

    fun getAllTasks(): Observable<List<Task>> {
        return service.getTasks()
                      .subscribeOn(RxSchedulers().getIOScheduler())
                      .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun getMyTasks(): Observable<List<Task>> {
        return service.getMyTasks()
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun getMyCompletedTasks(): Observable<List<Task>> {
        return service.getMyCompletedTasks()
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun getTask(id: Int): Observable<Task> {
        return service.task(id)
    }

    fun loadComments(id: Int): Observable<List<Comment>> {
        return service.comments(id)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun sendMessage(id: Int, message: String): Observable<Comment> {
        return service.comments(id, message)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun subscribe(id: Int?): Observable<Response> {
        return service.subscribe(id)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun voteCompanyAtmosphere(taskId: Int, companyId: Int, stars: Int): Observable<CompanyVoteResponse> {
        return service.voteCompanyAtmosphere(taskId, companyId, stars)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())}

    fun voteCompanyConformity(taskId: Int, companyId: Int, stars: Int): Observable<CompanyVoteResponse> {
        return service.voteCompanyConformity(taskId, companyId, stars)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun voteCompanyOrganizationLevel(taskId: Int, companyId: Int, stars: Int): Observable<CompanyVoteResponse> {
        return service.voteCompanyOrganizationLevel(taskId, companyId, stars)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun vote(id: Int?, aimId: Int, vote: Int): Observable<VoteResponse> {
        return service.vote(id, aimId, vote)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun companyVotePositions(): List<CompanyVotePosition> {
        val list: MutableList<CompanyVotePosition> =  ArrayList()
        val context = VolunteersApp.context

        list.add(CompanyVotePosition(Constants.COMPANY_VOTING_ID.ATMOSPHERE, context.resources.getString(R.string.profile_company_atmosphere), "Ацаніце, наколькі прыемнай і душэўнай была атмасфера пры выкананні задання"))
        list.add(CompanyVotePosition(Constants.COMPANY_VOTING_ID.CONFORMITY, context.resources.getString(R.string.profile_company_conformity), "Ацаніце, наколькі дакладка арганізатар апісаў заданне, якое вы выконвалі?"))
        list.add(CompanyVotePosition(Constants.COMPANY_VOTING_ID.ORGANIZATION, context.resources.getString(R.string.profile_company_organization_level), "Ацаніце, наколькі добра арганізатар мерапрыемства падрыхтаваўся да яго?"))

        return list.toList()
    }




}