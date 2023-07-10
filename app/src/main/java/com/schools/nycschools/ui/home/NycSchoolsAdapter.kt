package com.schools.nycschools.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.schools.nycschools.databinding.SchoolCardBinding
import com.schools.nycschools.domain.model.NYCSchoolResponseItem
import com.schools.nycschools.utils.setOnSafeClickListener
import java.util.Locale

/**
 * RecyclerView Adapter to display *School Card *.
 *
 * Nyc schools adapter
 *
 * @property list
 * @property onCardClick
 * @property callClick
 * @property emailClick
 * @property faxClick
 * @property linkClick
 * @property directionLink
 * @constructor Create empty Nyc schools adapter
 */

class NycSchoolsAdapter(
    private val list: MutableList<NYCSchoolResponseItem>,
    private val onCardClick: (nycSchool: NYCSchoolResponseItem) -> Unit,
    private val callClick: (number: String) -> Unit,
    private val emailClick: (email: String) -> Unit,
    private val faxClick: (number: String) -> Unit,
    private val linkClick: (link: String) -> Unit,
    private val directionLink: (lat: String, long: String) -> Unit
) : RecyclerView.Adapter<NycSchoolsAdapter.SchoolCardHolder>(), Filterable {

    var schoolsFilterList = ArrayList<NYCSchoolResponseItem>()

    init {
        schoolsFilterList = list as ArrayList<NYCSchoolResponseItem>
    }

    /**
     * RecyclerView ViewHolder to display a School Card.
     *
     * @property binding the binding class item layout.
     */
    inner class SchoolCardHolder(private val binding: SchoolCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Method to bind data to layout.
         */
        fun bind(item: NYCSchoolResponseItem) {
            binding.run {
                root.setOnSafeClickListener {
                    item.dbn?.let { onCardClick.invoke(item) }
                }

                tvSchoolName.text = item.school_name
                tvSchoolCode.text = item.dbn
                item.school_email?.let { email ->
                    incContact.ibEmail.visibility = View.VISIBLE
                    incContact.ibEmail.setOnSafeClickListener {
                        emailClick.invoke(email)
                    }
                } ?: run {
                    incContact.ibEmail.visibility = View.GONE
                }
                item.phone_number?.let { number ->
                    incContact.ibCall.visibility = View.VISIBLE
                    incContact.ibCall.setOnSafeClickListener {
                        callClick.invoke(number)
                    }
                } ?: run {
                    incContact.ibCall.visibility = View.GONE
                }

                item.fax_number?.let { number ->
                    incContact.ibFax.visibility = View.VISIBLE
                    incContact.ibFax.setOnSafeClickListener {
                        faxClick.invoke(number)
                    }
                } ?: run {
                    incContact.ibFax.visibility = View.GONE
                }

                item.latitude?.let { lat ->
                    item.longitude?.let { long ->
                        incContact.ibDirection.visibility = View.VISIBLE
                        incContact.ibDirection.setOnSafeClickListener {
                            directionLink.invoke(lat, long)
                        }
                    } ?: run {
                        incContact.ibDirection.visibility = View.GONE
                    }
                } ?: run {
                    incContact.ibDirection.visibility = View.GONE
                }

                item.website?.let { website ->
                    incContact.ibLink.visibility = View.VISIBLE
                    incContact.ibLink.setOnSafeClickListener {
                        linkClick.invoke(website)
                    }
                } ?: run {
                    incContact.ibLink.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolCardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SchoolCardBinding.inflate(inflater, parent, false)
        return SchoolCardHolder(binding)
    }

    override fun onBindViewHolder(holder: SchoolCardHolder, position: Int) {
        holder.bind(schoolsFilterList[position])
    }

    override fun getItemCount() = schoolsFilterList.size

    /**
     * Method to update the data set of adapter.
     */
    fun update(newList: List<NYCSchoolResponseItem>) {
        list.clear()
        list.addAll(newList)
        schoolsFilterList.clear()
        schoolsFilterList.addAll(newList)
        notifyItemRangeChanged(0, list.size)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    schoolsFilterList = list as ArrayList<NYCSchoolResponseItem>
                } else {
                    val resultList = ArrayList<NYCSchoolResponseItem>()
                    for (row in list) {
                        if (row.school_name?.lowercase(Locale.ROOT)?.contains(
                                constraint.toString()
                                    .lowercase(Locale.ROOT)
                            ) == true
                        ) {
                            resultList.add(row)
                        }
                    }
                    schoolsFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = schoolsFilterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged", "UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                schoolsFilterList = results?.values as ArrayList<NYCSchoolResponseItem>
                notifyDataSetChanged()
            }
        }
    }
}
