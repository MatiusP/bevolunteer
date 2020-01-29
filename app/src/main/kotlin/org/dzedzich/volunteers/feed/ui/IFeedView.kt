
package org.dzedzich.volunteers.feed.ui

import org.dzedzich.volunteers.feed.data.models.Feed
import org.dzedzich.volunteers.root.structure.IView

/**
 * Created by alexscrobot on 15.05.17.
 */
interface IFeedView : IView {

    fun loadDataInFeedAdapter(data: List<Feed>)
    fun renderAdapter(list: List<Feed>)
}