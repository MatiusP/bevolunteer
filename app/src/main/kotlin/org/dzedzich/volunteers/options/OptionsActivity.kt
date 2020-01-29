package org.dzedzich.volunteers.options

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.view_settings.*
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.root.structure.BaseActivity

class OptionsActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener {
    private val repo = OptionsRepository()

    override fun toolbarPreference() {
        toolbar.title = resources.getString(R.string.settings_title)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_settings)

        toolbarPreference()

        if(!repo.isWasChecked("setting#push")) {
            repo.saveOption("setting#push", true)
        }

        if(!repo.isWasChecked("setting#vibrate")) {
            repo.saveOption("setting#vibrate", true)
        }

        push.isChecked = repo.getOption("setting#push")
        vibrate.isChecked = repo.getOption("setting#vibrate")

        push.setOnCheckedChangeListener(this)
        vibrate.setOnCheckedChangeListener(this)

        contacts_web_page.settings.javaScriptEnabled = true
        contacts_web_page.loadUrl("https://dzedzich.org/nashy-kantakty/#post-645")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView?.id) {
            R.id.push -> check("push", isChecked)
            R.id.vibrate -> check("vibrate", isChecked)
        }

    }

    private fun check(setting: String, isChecked: Boolean) {
        if(!repo.isWasChecked("setting#$setting")) {
            repo.check("setting#$setting", true)
        }
        repo.saveOption("setting#$setting", isChecked)
    }
}