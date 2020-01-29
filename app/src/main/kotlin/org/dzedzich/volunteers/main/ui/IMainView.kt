package org.dzedzich.volunteers.main.ui

import com.google.android.material.tabs.TabLayout
import org.dzedzich.volunteers.root.structure.IView

/**
 * Created by aleksejskrobot on 14.05.17.
 */
interface IMainView : IView {

    fun initPoint()
    fun provideTabLayout(): TabLayout
}