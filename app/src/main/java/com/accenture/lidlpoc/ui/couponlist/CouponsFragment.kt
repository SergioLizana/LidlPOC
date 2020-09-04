package com.accenture.lidlpoc.ui.couponlist


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.accenture.lidlpoc.R
import com.accenture.lidlpoc.domain.api.LidlApi
import com.accenture.lidlpoc.domain.api.LidlClient
import com.accenture.lidlpoc.domain.repository.LidlRepository
import com.accenture.lidlpoc.domain.usecase.ActivateCouponUseCase
import com.accenture.lidlpoc.domain.usecase.CancelCouponUseCase
import com.accenture.lidlpoc.domain.usecase.GetCouponsUseCase
import com.accenture.lidlpoc.ui.adapter.CouponAdapter
import com.accenture.lidlpoc.ui.common.GenericListAdapter
import com.accenture.lidlpoc.ui.common.LidlViewResult
import com.accenture.lidlpoc.ui.common.OnClickButtonListListener
import com.accenture.lidlpoc.ui.common.OnClickListListener
import com.accenture.lidlpoc.ui.data.CouponResponse
import com.accenture.lidlpoc.viewmodel.CouponsViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.coupons_fragment.*


class CouponsFragment : Fragment(), OnClickListListener<CouponResponse>,
    OnClickButtonListListener {

    companion object {
    }

    val viewModel: CouponsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.coupons_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCouponsUseCase =
            GetCouponsUseCase(LidlRepository(LidlClient().createService(LidlApi::class.java)))
        viewModel.activateCouponsUseCase =
            ActivateCouponUseCase(LidlRepository(LidlClient().createService(LidlApi::class.java)))
        viewModel.cancelCouponUseCase =
            CancelCouponUseCase(LidlRepository(LidlClient().createService(LidlApi::class.java)))
        viewModel.getCoupons()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        manageToolbarTitle()

        coupon_list_recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                this@CouponsFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = CouponAdapter(null, this@CouponsFragment, this@CouponsFragment)
        }

        viewModel.couponLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LidlViewResult.Loading -> {
                    loader.visibility = View.VISIBLE
                }
                is LidlViewResult.Success -> {
                    loader.visibility = View.GONE
                    val adapter = coupon_list_recycler.adapter as GenericListAdapter<CouponResponse>
                    adapter.items = it.data!!
                }
                else -> {
                    loader.visibility = View.GONE
                    Log.d("ERROR", "ERROR")
                }
            }
        })
    }

    private fun manageToolbarTitle() {
        var isShow = true
        var scrollRange = -1
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                collapsingToolbar.title = "0 cupones activados"
                isShow = true
            } else if (isShow) {
                collapsingToolbar.title =
                    "Tus Cupones" //careful there should a space between double quote otherwise it wont work
                isShow = false
            }
        })
    }

    override fun onClickListener(data: CouponResponse, v: View) {
        if (!data.disabled) {
            val bundle = Bundle()
            bundle.putInt("couponId", data.id)
            Navigation.findNavController(v)
                .navigate(R.id.action_couponsFragment_to_couponsDetailsFragment, bundle)
        }
    }


    override fun onClickButtonListener(active: Boolean, data: CouponResponse) {
        if (active) {
            viewModel.cancelCoupon(data.id)
        } else {
            viewModel.activateCoupon(data.id)
        }
    }

}
