package org.dzedzich.volunteers.profile.ui.views.impl

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.method.LinkMovementMethod
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_user_profile.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.profile.data.repos.UserProfileRepository
import org.dzedzich.volunteers.root.structure.BaseActivity
import org.dzedzich.volunteers.tasks.ui.views.adapters.TaskAdapter
import org.dzedzich.volunteers.utils.Constants
import rx.subscriptions.CompositeSubscription

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class VolunteerProfileActivity : BaseActivity() {

    val repo = UserProfileRepository()
    val taskAdapter = TaskAdapter()
    var id: Int? = 0
    val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun toolbarPreference() {
        toolbar.title = resources.getString(R.string.loading)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_user_profile)

        toolbarPreference()

        tasks_list.layoutManager = LinearLayoutManager(this)
        tasks_list.adapter = taskAdapter
        taskAdapter.context = this

        id = intent?.extras?.getInt(Constants.BUNDLES.USER_ID)

        site.movementMethod = LinkMovementMethod.getInstance()
        phone.movementMethod = LinkMovementMethod.getInstance()

        subscriptions.add(repo.profile(id).subscribe ({ profile ->

            if(profile?.site == null) {
                site.visibility = View.GONE
            }

            supportActionBar?.title = profile.name
            rating.text = "${profile?.ratingYear}\n${profile?.rating}"
            name.text = profile?.name
            Glide.with(this).load(profile?.avatar).asBitmap().placeholder(R.drawable.avatar).into(avatar)
            phone.visibility = if(profile?.phone.isNullOrEmpty()) View.GONE else View.VISIBLE
            phone.text = profile?.phone
            site.text = profile?.site()
            slogan.text = profile?.slogan
            tasks.text = (profile?.completedTasksCount as Int).toString()
            place.text = "${profile.place} з ${profile.volunteersCount}"
            taskAdapter.loadList(profile.tasks)
        }, Throwable::printStackTrace))
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.unsubscribe()
    }
}