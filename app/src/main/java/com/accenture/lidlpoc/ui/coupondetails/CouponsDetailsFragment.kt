package com.accenture.lidlpoc.ui.coupondetails


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.accenture.lidlpoc.R
import com.accenture.lidlpoc.domain.api.LidlApi
import com.accenture.lidlpoc.domain.api.LidlClient
import com.accenture.lidlpoc.domain.repository.LidlRepository
import com.accenture.lidlpoc.domain.usecase.GetCouponDetailUseCase
import com.accenture.lidlpoc.ui.common.LidlViewResult
import com.accenture.lidlpoc.viewmodel.CouponsViewModel
import kotlinx.android.synthetic.main.details_fragment.*

class CouponsDetailsFragment : Fragment() {

    companion object {
    }

    val viewModel: CouponsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.detailCouponUseCase =
            GetCouponDetailUseCase(LidlRepository(LidlClient().createService(LidlApi::class.java)))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            activity?.onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val couponId = arguments?.getInt("couponId")
        if (couponId != null) {
            viewModel.getCouponDetail(couponId)
        } else {
            Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show()
        }
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = ""

        val appBarConfiguration =
            AppBarConfiguration((activity as AppCompatActivity).findNavController(R.id.nav_host_fragment).graph)
        NavigationUI.setupActionBarWithNavController(
            activity as AppCompatActivity,
            (activity as AppCompatActivity).findNavController(R.id.nav_host_fragment),
            appBarConfiguration
        )


        viewModel.couponDetailLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LidlViewResult.Loading -> {
                    loader_detail.visibility = View.VISIBLE
                    image_product.visibility = View.GONE
                }
                is LidlViewResult.Success -> {
                    loader_detail.visibility = View.GONE
                    image_product.visibility = View.VISIBLE
                    if (it.data!!.active) {
                        active_item_text.text = getString(R.string.active)
                        active_item_button.apply {
                            background = resources.getDrawable(R.drawable.button_background_active)
                            text = getString(R.string.activado)
                            setTextColor(resources.getColor(R.color.colorPrimary))
                        }
                    } else {
                        active_item_text.text = getString(R.string.unactive)
                        active_item_button.apply {
                            background =
                                resources.getDrawable(R.drawable.button_background_disabled)
                            setTextColor(resources.getColor(R.color.white))
                            text = getString(R.string.active_action)
                        }
                    }

                    brand.text = it.data.itemTitle
                    item_title.text = it.data.itemName
                    item_description_text.text = it.data.itemDesc
                }
                else -> {
                    loader_detail.visibility = View.VISIBLE
                    image_product.visibility = View.GONE
                    Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}
