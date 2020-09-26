package com.smarttoolfactory.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.smarttoolfactory.core.ui.fragment.DynamicNavigationFragment
import com.smarttoolfactory.dashboard.adapter.PropertyListAdapter
import com.smarttoolfactory.dashboard.databinding.FragmentDashboardSeeAllBinding
import com.smarttoolfactory.dashboard.model.PropertyListModel
import com.smarttoolfactory.domain.model.PropertyItem

class DashboardSeeAllFragment : DynamicNavigationFragment<FragmentDashboardSeeAllBinding>() {

    override fun getLayoutRes() = R.layout.fragment_dashboard_see_all

    override fun bindViews(view: View, savedInstanceState: Bundle?) {

        dataBinding.recyclerView.apply {

            // Set Layout manager
            this.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

            val onItemClick: ((PropertyItem) -> Unit) = { propertyItem ->

                val bundle = bundleOf("property" to propertyItem)

                navigateWithInstallMonitor(
                    findNavController(),
                    R.id.action_dashboardSeeAllFragment_to_nav_graph_property_detail,
                    bundle
                )
            }

            // Set RecyclerViewAdapter
            val itemListAdapter = PropertyListAdapter(R.layout.item_property_see_all, onItemClick)

            this.adapter = itemListAdapter

            // Add vertical dividers
            val verticalDivider = DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL)
            addItemDecoration(verticalDivider)

            // Set data
            val propertyItemListModel =
                arguments?.get("propertyListModel") as PropertyListModel

            itemListAdapter.submitList(propertyItemListModel.items)
        }
    }
}
