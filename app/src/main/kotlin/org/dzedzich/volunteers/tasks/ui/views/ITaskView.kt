package org.dzedzich.volunteers.tasks.ui.views

import org.dzedzich.volunteers.root.structure.IView
import org.dzedzich.volunteers.tasks.data.models.Task
import org.dzedzich.volunteers.tasks.ui.views.models.CompanyVotingItem
import org.dzedzich.volunteers.tasks.ui.views.models.VolunterVotingItem

/**
 * Created by alexscrobot on 16.05.17.
 */

interface ITaskView : IView {

    var id: Int?
    fun loadTask(task: Task)
    fun getSharingData(): Map<String, String?>?
    fun getAddress(): String
    fun loadVolunteerVotePosition(it: List<VolunterVotingItem>)
    fun loadCompanyVotePosition(it: List<CompanyVotingItem>)
    fun voteForCompany(votePosition: String, vote: Int)
    fun vote(aimId: Int, vote: Int)
}