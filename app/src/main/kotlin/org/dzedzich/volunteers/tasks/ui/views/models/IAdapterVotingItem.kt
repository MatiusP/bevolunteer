package org.dzedzich.volunteers.tasks.ui.views.models

import android.content.Context
import org.dzedzich.volunteers.tasks.ui.views.adapters.VotingAdapter

/**
 * Created by aleksejskrobot on 31.05.17.
 */
interface IAdapterVotingItem {

    fun render(holder: VotingAdapter.ViewHolder?, context: Context?)

}