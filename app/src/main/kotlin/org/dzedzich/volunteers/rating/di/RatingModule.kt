package org.dzedzich.volunteers.rating.di

import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.rating.ui.presenters.RatingPresenter
import org.dzedzich.volunteers.rating.ui.views.adapters.RatingPagerAdapter
import org.dzedzich.volunteers.rating.ui.views.impl.RatingFragment

/**
 * Created by aleksejskrobot on 14.05.17.
 */
@Module
class RatingModule(val view: RatingFragment) {

    @Provides
    @RatingScope
    fun presenter(): RatingPresenter {
        return RatingPresenter()
    }

    @RatingScope
    @Provides
    fun pagerAdapter(): RatingPagerAdapter {
        return RatingPagerAdapter(view.context, view.childFragmentManager)
    }

}