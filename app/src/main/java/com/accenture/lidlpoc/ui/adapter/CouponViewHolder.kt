package com.accenture.lidlpoc.ui.adapter

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.accenture.lidlpoc.R
import com.accenture.lidlpoc.ui.common.GenericBinder
import com.accenture.lidlpoc.ui.common.OnClickButtonListListener
import com.accenture.lidlpoc.ui.common.OnClickListListener
import com.accenture.lidlpoc.ui.data.CouponResponse

class CouponViewHolder(itemView: View, private val buttonListener: OnClickButtonListListener) :
    RecyclerView.ViewHolder(itemView), GenericBinder<CouponResponse> {
    override fun bind(data: CouponResponse, listener: OnClickListListener<CouponResponse>?) {
        itemView.findViewById<CardView>(R.id.card_content).apply {
            setOnClickListener {
                listener?.onClickListener(data, this)
            }
        }

        itemView.findViewById<TextView>(R.id.coupon_title).apply {
            text = data.couponTitle
        }
        itemView.findViewById<TextView>(R.id.card_remaining_time).apply {
            text = data.couponRemainingText
        }
        if (data.disabled) {
            itemView.findViewById<ConstraintLayout>(R.id.coupon_constraint).apply {
                setBackgroundColor(resources.getColor(R.color.medium_grey))
            }
            itemView.findViewById<ImageButton>(R.id.card_activation_button).apply {
                setBackgroundResource(R.drawable.ic_lock_24px)
                backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.grey)
                visibility = View.VISIBLE
            }
            itemView.findViewById<Button>(R.id.active_item_button).apply {
                visibility = View.GONE
            }
        } else {
            itemView.findViewById<ConstraintLayout>(R.id.coupon_constraint).apply {
                setBackgroundColor(resources.getColor(R.color.light_grey))
            }
            itemView.findViewById<ImageButton>(R.id.card_activation_button).apply {
                visibility = View.GONE
            }
            itemView.findViewById<TextView>(R.id.card_remaining_time).apply {
                setTextColor(Color.RED)
            }
            if (data.isActive) {
                itemView.findViewById<Button>(R.id.active_item_button).apply {
                    visibility = View.VISIBLE
                    background = resources.getDrawable(R.drawable.button_background_active)
                    text = resources.getString(R.string.activado)
                    setTextColor(resources.getColor(R.color.colorPrimary))

                    setOnClickListener {
                        buttonListener.onClickButtonListener(true, data)
                    }
                }
            } else {
                itemView.findViewById<Button>(R.id.active_item_button).apply {
                    visibility = View.VISIBLE
                    background = resources.getDrawable(R.drawable.button_background_disabled)
                    setTextColor(resources.getColor(R.color.white))
                    text = resources.getString(R.string.active)
                    setOnClickListener {
                        buttonListener.onClickButtonListener(false, data)
                    }
                }
            }
        }
    }

}