
package org.dzedzich.volunteers.options

import org.dzedzich.volunteers.root.structure.BaseRepository

/**
 * Created by alexscrobot on 22.05.17.
 */
class OptionsRepository: BaseRepository() {

    fun saveOption(option: String, enabled: Boolean) {
        preferences.saveSwitcherSetting(option, enabled)
    }

    fun getOption(option: String): Boolean {
        return preferences.getSwitcherSetting(option)
    }

    fun isWasChecked(option: String): Boolean {
        return preferences.isSwitchWasChecked(option)
    }

    fun check(option: String, b: Boolean) {
        preferences.WasChecked(option)
    }
}