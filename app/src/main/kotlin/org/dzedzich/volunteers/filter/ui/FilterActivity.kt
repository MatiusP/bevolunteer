package org.dzedzich.volunteers.filter.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.ArrayAdapter
import com.haipclick.app.root.VolunteersApp
import kotlinx.android.synthetic.main.view_filters.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.filter.di.DaggerFilterComponent
import org.dzedzich.volunteers.filter.di.FilterModule
import org.dzedzich.volunteers.profile.data.models.Activity
import org.dzedzich.volunteers.profile.data.models.Company
import org.dzedzich.volunteers.root.structure.BaseActivity
import java.util.*
import javax.inject.Inject

/**
 * Created by aleksejskrobot on 03.06.17.
 */
class FilterActivity: BaseActivity(), IFilterView {

    init {
        DaggerFilterComponent.builder()
                .applicationComponent(VolunteersApp.component)
                .filterModule(FilterModule(this))
                .build()
                .inject(this)
    }

    @Inject lateinit var activitiesAdapter: ActivitiesAdapter
    @Inject lateinit var companiesCheckboxAdapter: CompaniesCheckboxAdapter
    @Inject lateinit var presenter: FilterPresenter

    lateinit var regionsAdapter: ArrayAdapter<String?>

    override fun toolbarPreference() {
        toolbar.title = resources.getString(R.string.action_filter)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_filters)

        toolbarPreference()

        presenter.bindView(this)

        actvities_list.layoutManager = LinearLayoutManager(this)
        actvities_list.adapter = activitiesAdapter

        companies_list.layoutManager = LinearLayoutManager(this)
        companies_list.adapter = companiesCheckboxAdapter

        send.setOnClickListener {
            presenter.saveFilter(
                    activitiesAdapter.getChecked(),
                    city.selectedItemPosition,
                    companiesCheckboxAdapter.getChecked()
            )
        }

        cancel.setOnClickListener { presenter.abortFilter() }
    }

    override fun close() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun loadRegions(regions: Array<String?>) {
        this.regionsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, regions)
        this.regionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        city.adapter = this.regionsAdapter
        city.prompt = resources.getString(R.string.filter_region)
        city.setSelection(0)
    }

    override fun loadCompanies(companies: List<Company>) {
        companiesCheckboxAdapter.setList(companies)
    }

    override fun loadActivities(activities: List<Activity>?) {
        this.activitiesAdapter.setList(activities ?: ArrayList())
    }

    override fun checkCurrentCompanies(companies: List<Company>?) {
        companiesCheckboxAdapter.checkCompanies(companies)
    }

    override fun checkCurrentRegion(region: Int) {
        city.setSelection(region)
    }

    override fun checkCurrentActivities(activities: List<Activity>?) {
        activitiesAdapter.checkItems(activities)
    }

}