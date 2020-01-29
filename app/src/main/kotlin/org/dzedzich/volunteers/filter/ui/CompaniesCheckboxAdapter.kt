package org.dzedzich.volunteers.filter.ui

import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import kotterknife.bindView
import org.dzedzich.volunteers.R
import org.dzedzich.volunteers.profile.data.models.Company
import org.jetbrains.anko.onCheckedChange
import java.util.*

/**
 * Created by alexscrobot on 21.07.17.
 */
class CompaniesCheckboxAdapter(private val view: IFilterView): RecyclerView.Adapter<CompaniesCheckboxAdapter.ViewHolder>() {

    private val companies: MutableList<Company> = ArrayList()
    private val filterCompanies: MutableList<Company> = ArrayList()
    private val ids: MutableSet<Int> = HashSet()

    fun setList(list: List<Company>) {
        companies.clear()
        companies.addAll(list.filter { it.id > 0 })
        notifyDataSetChanged()
    }

    fun getChecked(): IntArray {
        val arr = IntArray(ids.size)

        ids.forEachIndexed { i, id -> arr[i] = id }

        return arr
    }

    fun checkCompanies(companies: List<Company>?) {
        if(companies != null) {
            Log.d("CompaniesCheckbox", companies.toString())
            filterCompanies.clear()
            filterCompanies.addAll(companies)
        }
    }

    private fun checkCompany(company: Company): Boolean {
        return filterCompanies.any { it.id == company.id }
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val company = companies[position]
        val checkbox = holder.checkbox

        checkbox.text = company.name

        if(filterCompanies.size == 0 || checkCompany(company)) {
            checkbox.isChecked = true
            ids.add(company.id)
        } else {
            checkbox.isChecked = false
        }


        checkbox.onCheckedChange { _, b ->
            if(b) ids.add(company.id) else ids.remove(company.id)
            Log.d("CompaniesCheckbox", getChecked().joinToString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(view.getContext()).inflate(R.layout.item_company_checkbox, parent, false))
    }


    class ViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val checkbox: CheckBox by bindView(R.id.checkBox)
    }

}