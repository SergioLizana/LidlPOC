package com.accenture.lidlpoc.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.accenture.lidlpoc.R
import com.accenture.lidlpoc.ui.common.GenericListAdapter
import com.accenture.lidlpoc.ui.common.OnClickButtonListListener
import com.accenture.lidlpoc.ui.common.OnClickListListener
import com.accenture.lidlpoc.ui.data.CouponResponse

class CouponAdapter(
    couponList: List<CouponResponse>?,
    listener: OnClickListListener<CouponResponse>,
    private val buttonListener: OnClickButtonListListener
) : GenericListAdapter<CouponResponse>(couponList, listener) {


    override fun getLayoutId(position: Int, obj: CouponResponse): Int = R.layout.cupon_row

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return CouponViewHolder(view, buttonListener)
    }


}