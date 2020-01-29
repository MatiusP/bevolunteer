package org.dzedzich.volunteers.profile.ui.views.impl

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_company_profile.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.profile.data.repos.CompanyProfileRepository
import org.dzedzich.volunteers.root.structure.BaseActivity
import org.dzedzich.volunteers.tasks.ui.views.adapters.TaskAdapter
import org.dzedzich.volunteers.utils.Constants
import rx.subscriptions.CompositeSubscription

/**
 * Created by aleksejskrobot on 14.05.17.
 */
class CompanyProfileActivity : BaseActivity() {

    val repo = CompanyProfileRepository()
    var id: Int? = 0
    val taskAdapter = TaskAdapter()
    val subscriptions: CompositeSubscription = CompositeSubscription()

    override fun toolbarPreference() {
        toolbar.title = resources.getString(R.string.loading)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_company_profile)

        toolbarPreference()

        id = intent?.extras?.getInt(Constants.BUNDLES.COMPANY_ID)
        company_tasks.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        company_tasks.adapter = taskAdapter
        taskAdapter.context = this

        site.movementMethod = LinkMovementMethod.getInstance()
        slogan.movementMethod = LinkMovementMethod.getInstance()
        email.movementMethod = LinkMovementMethod.getInstance()
        phone.movementMethod = LinkMovementMethod.getInstance()

        subscriptions.add(repo.getCompanyProfile(id).subscribe ({ company ->
            if(company?.site == null) {
                site.visibility = View.GONE
            }

            supportActionBar?.title = company.name
            rating.text = company.rating.toString()
            place.text = "${company.place} з ${company.companiesCount}"
            Glide.with(this).load(company.logo).asBitmap().placeholder(R.drawable.company_logo).into(logo)
            company_name.text = company.name
            name.text = company.owner?.name
            email.text = company.owner?.email
            phone.text = company.owner?.phone
            site.text = company.site()
            slogan.text = Html.fromHtml(company.about)
            activities.text = company.activitiesName
            tasks.text = company.tasksCount.toString()

            taskAdapter.loadList(company.tasks)
        }, Throwable::printStackTrace))
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.unsubscribe()
    }

}