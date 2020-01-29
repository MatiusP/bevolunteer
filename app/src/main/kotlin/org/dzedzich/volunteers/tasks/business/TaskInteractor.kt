package org.dzedzich.volunteers.tasks.business

import android.util.Log
import com.github.vkguests.utils.rx.RxSchedulers
import com.haipclick.app.root.VolunteersApp.Companion.context
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.auth.data.models.User
import org.dzedzich.volunteers.root.text
import org.dzedzich.volunteers.root.user
import org.dzedzich.volunteers.tasks.data.TaskRepository
import org.dzedzich.volunteers.tasks.data.models.CompanyVoteResponse
import org.dzedzich.volunteers.tasks.data.models.Task
import org.dzedzich.volunteers.tasks.data.models.VoteResponse
import org.dzedzich.volunteers.tasks.ui.views.models.CompanyVotePosition
import org.dzedzich.volunteers.tasks.ui.views.models.CompanyVotingItem
import org.dzedzich.volunteers.tasks.ui.views.models.VolunterVotingItem
import org.dzedzich.volunteers.utils.Constants
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by alexscrobot on 16.05.17.
 */
class TaskInteractor(private val repository: TaskRepository) {

    fun tasks(): Observable<List<Task>> {
        return repository.getAllTasks()
    }

    fun tasks(query: String): Observable<List<Task?>> {
        return repository
                .getAllTasks()
                .flatMap {
                    val list: MutableList<Task?> = ArrayList()

                    Log.d("search:filter:bef:size", "${it.size}\n")

                    it?.forEach { task ->

                        if(isContains(task.title, query)) {
                            task.filtered = true
                        } else if(isContains(task.address, query)) {
                            task.filtered = true
                        } else if(isContains(task.company, query)) {
                            task.filtered = true
                        } else if(isContains(task.description, query)) {
                            task.filtered = true
                        } else task.filtered = isContains(task.formatedDate, query)

                        //TODO: я был в отчаянии.. хуйня ниже не работает... и итерация всегда выдает true

                        /*val arr = arrayOf()
                        arr.forEach breaker@ {
                            val contains = isContains(it, query)
                            Log.d("searching:arr", "field: $it, query: $query -> $contains")
                            if(contains) {
                                task.filtered = true
                                return@breaker
                            }
                        }*/

                        //06-01 11:54:32.343 7684-7684/org.dzedzich.volunteers D/searching:arr: field: 230 Bessie Island New Lorenza, ND 78918-8550, query: gist -> false
//                                06-01 11:54:32.343 7684-7684/org.dzedzich.volunteers D/searching:arr: field: von.com, query: gist -> false
//                                06-01 11:54:32.343 7684-7684/org.dzedzich.volunteers D/searching:arr: field: Sit sit illo qui eaque quam voluptatem. Minima nemo suscipit aut fugit. Illum in distinctio sunt., query: gist -> false
//                                06-01 11:54:32.343 7684-7684/org.dzedzich.volunteers D/searching:arr: field: 8 Мая в 00:00, query: gist -> false
//                                06-01 11:54:32.343 7684-7684/org.dzedzich.volunteers D/searching:arr: field: Lifeguard, query: gist -> false
//                                06-01 11:54:32.343 7684-7684/org.dzedzich.volunteers D/searching: task.filtered -> true
                        //TODO: ДАЖЕ БЛЯ СЕЙЧАС!!!

                        Log.d("searching", "task.filtered -> ${task.filtered}")

                        if(task.filtered) {
                            list.add(task)
                        }

                    }

                    Log.d("search:filter:aft:size", "${list.size}")

                    Observable.just(list.toList())
                }
    }

    private fun isContains(haystack: String?, needle: String): Boolean {
        return haystack?.toLowerCase()?.contains(needle.toLowerCase()) ?: false
    }

    fun activeTasks(): Observable<List<Task>> {
        return repository.getMyTasks()
    }

    fun completedTasks(): Observable<List<Task>> {
        return repository.getMyCompletedTasks()
    }

    fun task(id: Int?): Observable<Task> {
        return repository.getTask(id ?: 0)
                .delay(700, TimeUnit.MILLISECONDS)
                .subscribeOn(RxSchedulers().getIOScheduler())
                .observeOn(RxSchedulers().getMainThreadScheduler())
    }

    fun takeAPart(id: Int?): Observable<Task> {
        return repository.subscribe(id)
                .flatMap {
                    if(it.status == "subscribed") {
                        text(R.string.action_subscription_successfully)
                    } else {
                        text(R.string.action_unsubscription_successfully)
                    }
                    repository.getTask(id?: 0)
                }
    }

    fun voteCompany(id: String, vote: Int, taskId: Int?, companyId: Int?): Observable<CompanyVoteResponse> {
        return when(id) {
            Constants.COMPANY_VOTING_ID.ATMOSPHERE -> repository.voteCompanyAtmosphere(taskId ?: 0, companyId ?: 0, vote)
            Constants.COMPANY_VOTING_ID.CONFORMITY -> repository.voteCompanyConformity(taskId ?: 0, companyId ?: 0, vote)
            Constants.COMPANY_VOTING_ID.ORGANIZATION -> repository.voteCompanyOrganizationLevel(taskId ?: 0, companyId ?: 0, vote)
            else -> Observable.just(CompanyVoteResponse()).subscribeOn(RxSchedulers().getIOScheduler()).observeOn(RxSchedulers().getMainThreadScheduler())
        }
    }

    fun makeVolunteersVotingList(volunteers: List<User>?): Observable<List<VolunterVotingItem>> {
        val items: MutableList<VolunterVotingItem> = ArrayList()

        volunteers
                ?.filterNot {
                    it.id == user()?.id ?: 0
                }
                ?.forEach {
                    items.add(VolunterVotingItem(it))
                }

        return Observable.just(items.toList())
    }

    fun makeCompanyVotingList(voting: CompanyVoteResponse?): Observable<List<CompanyVotingItem>> {
        val items: MutableList<CompanyVotingItem> = ArrayList()

        if(voting == null) {
            repository.companyVotePositions().forEach {
                items.add(CompanyVotingItem(it))
            }
        } else {
            items.add(CompanyVotingItem(CompanyVotePosition(Constants.COMPANY_VOTING_ID.ATMOSPHERE, context.resources.getString(R.string.profile_company_atmosphere), "example", voting.atmosphereStars)))
            items.add(CompanyVotingItem(CompanyVotePosition(Constants.COMPANY_VOTING_ID.CONFORMITY, context.resources.getString(R.string.profile_company_conformity), "example", voting.conformityStars)))
            items.add(CompanyVotingItem(CompanyVotePosition(Constants.COMPANY_VOTING_ID.ORGANIZATION, context.resources.getString(R.string.profile_company_organization_level), "example", voting.organizationLevelStars)))
        }

        return Observable.just(items.toList())
    }

    fun vote(id: Int?, aimId: Int, vote: Int): Observable<VoteResponse> {
        return repository.vote(id, aimId, vote)
    }

}