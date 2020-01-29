package org.dzedzich.volunteers.root.structure

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.dzedzich.volunteers.R

/**
 * Created by alexscrobot on 20.04.17.
 */
abstract class AbstractFragmentRouter(val layout: Int) {

    lateinit open var fragmentManager: androidx.fragment.app.FragmentManager

    fun invoke(view: androidx.fragment.app.Fragment?) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .replace(layout, view as androidx.fragment.app.Fragment)
                .commitAllowingStateLoss()
    }

    fun invoke(view: Fragment, tag: String) {
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                    .addToBackStack(tag)
                    .replace(layout, view, tag)
                    .commitAllowingStateLoss()
    }
    
}