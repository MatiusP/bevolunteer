package org.dzedzich.volunteers.rating.ui.presenters

import org.dzedzich.volunteers.rating.ui.views.IRatingView
import org.dzedzich.volunteers.root.structure.Presenter

/**
 * Created by alexscrobot on 15.05.17.
 */
class RatingPresenter :Presenter<IRatingView>() {
    override var view: IRatingView? = null
}