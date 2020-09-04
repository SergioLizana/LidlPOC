package com.accenture.lidlpoc.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericListAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    var items: List<T>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: OnClickListListener<T>? = null

    constructor(items: List<T>?, listener: OnClickListListener<T>) {
        if (items == null) {
            this.items = emptyList()
        } else {
            this.items = items
        }
        this.listener = listener
    }

    constructor() {
        items = emptyList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false), viewType
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GenericBinder<T>).bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int = getLayoutId(position, items[position])

    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder


}