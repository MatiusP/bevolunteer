package org.dzedzich.volunteers.tasks.ui.views.impl

import android.os.Build
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.haipclick.app.root.VolunteersApp
import com.mikepenz.actionitembadge.library.ActionItemBadge
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import kotlinx.android.synthetic.main.view_task.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.root.structure.BaseActivity
import org.dzedzich.volunteers.tasks.data.models.Task
import org.dzedzich.volunteers.tasks.di.DaggerTaskComponent
import org.dzedzich.volunteers.tasks.ui.presenters.TaskPresenter
import org.dzedzich.volunteers.tasks.ui.views.ITaskView
import org.dzedzich.volunteers.tasks.ui.views.adapters.SubscribersAdapter
import org.dzedzich.volunteers.tasks.ui.views.adapters.VotingAdapter
import org.dzedzich.volunteers.tasks.ui.views.models.CompanyVotingItem
import org.dzedzich.volunteers.tasks.ui.views.models.VolunterVotingItem
import org.dzedzich.volunteers.utils.Constants
import org.jetbrains.anko.find
import java.util.*
import javax.inject.Inject

/**
 * Created by alexscrobot on 16.05.17.
 */
class TaskActivity: BaseActivity(), ITaskView {

    override var id: Int? = 0
    var menu: Menu? = null
    var task: Task? = null
    private lateinit var wordParticipantsTitle: String
    private lateinit var wordAttendedParticipantsTitle: String
    private lateinit var wordFrom: String
    private lateinit var wordHour: String
    private lateinit var actionSubscribe: String
    private lateinit var actionUnsubscribe: String

    init {
        DaggerTaskComponent.builder()
                .applicationComponent(VolunteersApp.component)
                .build()
                .inject(this)
    }

    @Inject lateinit var presenter: TaskPresenter
    @Inject lateinit var subscribersAdapter: SubscribersAdapter
    @Inject lateinit var votingAdapter: VotingAdapter<VolunterVotingItem>
    @Inject lateinit var companyVotingAdapter: VotingAdapter<CompanyVotingItem>

    override fun toolbarPreference() {
        val mainToolbar = find<Toolbar>(R.id.main_toolbar)
        mainToolbar.title = "Загрузка..."
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_task)

        initTextVars()

        id = intent?.extras?.getInt(Constants.BUNDLES.TASK_ID)

        participants_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        participants_list.adapter = subscribersAdapter

        presenter.bindView(this)

        initOnClickActions()

        toolbarPreference()
    }

    fun initTextVars() {
        wordAttendedParticipantsTitle = resources.getString(R.string.words_participants_attended)
        wordParticipantsTitle = resources.getString(R.string.words_participants)
        wordFrom = resources.getString(R.string.words_from)
        wordHour = resources.getString(R.string.words_hour)
        actionSubscribe = resources.getString(R.string.button_subscribe)
        actionUnsubscribe = resources.getString(R.string.button_unsubscribe)
    }

    private fun initOnClickActions() {
        address.setOnClickListener(presenter)
        subscribe.setOnClickListener(presenter)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadTaskInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun loadTask(task: Task) {
        this.task = task

        loader.visibility = View.GONE

        address.visibility = View.VISIBLE
        datetime.visibility = View.VISIBLE
        participants.visibility = View.VISIBLE
        duration.visibility = View.VISIBLE
        subscribe.visibility = View.VISIBLE

        supportActionBar?.title = task.title

        val preview = find<ImageView>(R.id.main_backdrop)
        val title = find<TextView>(R.id.title)
        Glide.with(this).load(task.preview).asBitmap().placeholder(R.drawable.default_task_bg).into(preview)
        title.text = task.title
        datetime.text = task.formatedDate
        company.text = task.company
        address.text = task.address
        duration.text = "$wordHour: ${task.duration}"

        val spannedDescription = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(task.description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(task.description)
        }

        description.text = spannedDescription
        description.movementMethod = LinkMovementMethod.getInstance()
        participants.text = "$wordParticipantsTitle ${task.volunteers?.size} $wordFrom ${task.participantsCount}"

        subscribe.visibility = if(!task.isMy && task.participantsCount == task.volunteers?.size) View.GONE else View.VISIBLE
        subscribe.text = if(!task.isMy) actionSubscribe else actionUnsubscribe

        participants_list.visibility = if((task.volunteers?.size ?: 0) >= 1) View.VISIBLE else View.GONE

        subscribersAdapter.loadParticipants(task.volunteers ?: ArrayList())

        ActionItemBadge.update(this, menu?.findItem(R.id.action_comments), MaterialDesignIconic.Icon.gmi_comment, ActionItemBadge.BadgeStyles.YELLOW, task.comments)

        if(task.isCompleted) {
            loadVotingFunctions()
        }
    }

    private fun loadVotingFunctions() {
        subscribe.visibility = View.GONE
        participants.text = "$wordAttendedParticipantsTitle ${task?.volunteers?.size} $wordFrom ${task?.participantsCount}"

        if(task?.isMy ?: false) {
            evaluate_title.visibility = View.VISIBLE

            voting_list.visibility = View.VISIBLE
            voting_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
            voting_list.adapter = votingAdapter

            company_voting_list.visibility = View.VISIBLE
            company_voting_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
            company_voting_list.adapter = companyVotingAdapter

            presenter.prepareVotingList(task)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_task, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return presenter.menuItemSelected(item)
    }

    override fun getSharingData(): MutableMap<String, String?> {
        val data: MutableMap<String, String?> = HashMap()
        data.put("title", task?.title)
        data.put("body", task?.description)
        data.put("img", task?.preview)

        return data
    }

    override fun getAddress(): String {
        return address.text.toString()
    }

    override fun loadVolunteerVotePosition(it: List<VolunterVotingItem>) {
        votingAdapter.loadList(it)
    }

    override fun loadCompanyVotePosition(it: List<CompanyVotingItem>) {
        companyVotingAdapter.loadList(it)
    }

    override fun voteForCompany(votePosition: String, vote: Int) {
        if(task?.isMy ?: false) {
            presenter.voteForCompany(votePosition, vote, task?.id, task?.companyEntity?.id)
        }
    }

    override fun vote(aimId: Int, vote: Int) {
        if(task?.isMy ?: false) {
            presenter.vote(id, aimId, vote)
        }
    }
}