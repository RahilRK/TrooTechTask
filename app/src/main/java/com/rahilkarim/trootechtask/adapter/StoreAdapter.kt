package com.rahilkarim.trootechtask.adapter

import android.content.Context
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahilkarim.trootechtask.databinding.StoreItemBinding
import com.rahilkarim.trootechtask.ui.store.model.Franquicia
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class StoreAdapter(
    private val activity: Context,
    private val list: ArrayList<Franquicia>,
    private val onClick: storeListOnClick,
) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    var tag = "StoreAdapter"
    lateinit var binding: StoreItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = StoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[holder.adapterPosition]


        binding.negocio.text = "${model.negocio}"

        binding.negocio.setOnClickListener {

            onClick.onClick(position,model)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    }

    interface storeListOnClick {

        fun onClick(position: Int, model: Franquicia)
    }
}