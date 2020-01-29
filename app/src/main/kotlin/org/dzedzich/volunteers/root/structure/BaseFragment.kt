package org.dzedzich.volunteers.root.structure

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by aleksejskrobot on 14.05.17.
 */
abstract class BaseFragment : Fragment(), IView {

    val TAG = javaClass.simpleName

    abstract var layout: Int
    lateinit var activity: AppCompatActivity

    override fun getContext(): Context {
        return super.getContext()!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.activity = getActivity() as AppCompatActivity
        return inflater.inflate(layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onViewCreated(view, savedInstanceState)
        toolbarPreference()
    }
}