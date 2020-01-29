package org.dzedzich.volunteers.rating.di

import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.rating.ui.views.adapters.CompaniesAdapter
import org.dzedzich.volunteers.feed.ui.VolunteersAdapter
import org.dzedzich.volunteers.rating.bussiness.RatingInteractor
import org.dzedzich.volunteers.rating.data.RatingRepository
import org.dzedzich.volunteers.rating.ui.presenters.CompanyRatingPresenter
import org.dzedzich.volunteers.rating.ui.presenters.VolunteersRatingPresenter

/**
 * Created by alexscrobot on 15.05.17.
 */
@Module
class FragmentRatingModule {

    @FragmentRatingScope
    @Provides
    fun volunteersPresenter(interactor: RatingInteractor): VolunteersRatingPresenter {
        return VolunteersRatingPresenter(interactor)
    }

    @FragmentRatingScope
    @Provides
    fun companiesPresenter(interactor: RatingInteractor): CompanyRatingPresenter {
        return CompanyRatingPresenter(interactor)
    }

    @FragmentRatingScope
    @Provides
    fun repo(): RatingRepository {
        return RatingRepository()
    }

    @FragmentRatingScope
    @Provides
    fun interactor(repository: RatingRepository): RatingInteractor {
        return RatingInteractor(repository)
    }

    @FragmentRatingScope
    @Provides
    fun volunteersAdapter(): VolunteersAdapter {
        return VolunteersAdapter()
    }

    @FragmentRatingScope
    @Provides
    fun companiesAdapter(): CompaniesAdapter {
        return CompaniesAdapter()
    }

}