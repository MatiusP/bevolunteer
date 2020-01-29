package org.dzedzich.volunteers.profile.di

import dagger.Module
import dagger.Provides
import org.dzedzich.volunteers.profile.bussiness.UserProfileInteractor
import org.dzedzich.volunteers.profile.data.repos.UserProfileRepository
import org.dzedzich.volunteers.profile.ui.presenters.UserProfilePresenter

/**
 * Created by aleksejskrobot on 14.05.17.
 */
@Module
class ProfileModule {

    @Provides
    @ProfileScope
    fun presenter(interactor: UserProfileInteractor): UserProfilePresenter {
        return UserProfilePresenter(interactor)
    }

    @ProfileScope
    @Provides
    fun userProfileRepo(): UserProfileRepository {
        return UserProfileRepository()
    }

    @ProfileScope
    @Provides
    fun userProfileInteractor(repository: UserProfileRepository): UserProfileInteractor {
        return UserProfileInteractor(repository)
    }

}