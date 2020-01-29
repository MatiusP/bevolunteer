package org.dzedzich.volunteers.filter.di

import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.filter.business.FilterInteractor
import org.dzedzich.volunteers.filter.data.FilterRepository
import org.dzedzich.volunteers.filter.ui.ActivitiesAdapter
import org.dzedzich.volunteers.filter.ui.CompaniesCheckboxAdapter
import org.dzedzich.volunteers.filter.ui.FilterActivity
import org.dzedzich.volunteers.filter.ui.FilterPresenter

/**
 * Created by aleksejskrobot on 03.06.17.
 */
@Module
class FilterModule(private val view: FilterActivity) {

    @Provides
    @FilterScope
    fun presenter(interactor: FilterInteractor): FilterPresenter {
        return FilterPresenter(view, interactor)
    }

    @Provides
    @FilterScope
    fun repository(): FilterRepository {
        return FilterRepository()
    }

    @Provides
    @FilterScope
    fun interactor(repository: FilterRepository): FilterInteractor {
        return FilterInteractor(repository)
    }

    @Provides
    @FilterScope
    fun adapter(): ActivitiesAdapter {
        return ActivitiesAdapter(view)
    }

    @Provides
    @FilterScope
    fun companiesCheckboxAdapter(): CompaniesCheckboxAdapter {
        return CompaniesCheckboxAdapter(view)
    }

}