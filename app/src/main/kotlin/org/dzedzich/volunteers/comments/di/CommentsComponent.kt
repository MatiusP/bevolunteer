package org.dzedzich.volunteers.comments.di

import dagger.Component
import org.dzedzich.volunteers.comments.ui.views.CommentsActivity
import org.dzedzich.volunteers.root.di.ApplicationComponent

/**
 * Created by aleksejskrobot on 27.05.17.
 */
@CommentsScope
@Component(modules = arrayOf(CommentsModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface CommentsComponent {

    fun inject(view: CommentsActivity)

}