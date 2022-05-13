package com.rahilkarim.trootechtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahilkarim.trootechtask.databinding.CategoryItemBinding
import com.rahilkarim.trootechtask.ui.menu.db_model.Category
import com.rahilkarim.trootechtask.ui.menu.db_model.Menu


class CategoryAdapter(
    private val activity: Context,
    private val list: ArrayList<Category>,
    private val onClick: categoryListOnClick,
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var tag = "CategoryAdapter"
    lateinit var binding: CategoryItemBinding
    lateinit var adapter: MenuItemAdapter

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[holder.adapterPosition]


        binding.categoryName.text = "${model.categoryName}"
        binding.categoryName.setOnClickListener {

        }

        setAdapter(onClick,binding, model.menuList as ArrayList<Menu>)
    }

    private fun setAdapter(
        onClick: categoryListOnClick,
        binding: CategoryItemBinding,
        menuList: ArrayList<Menu>
    ) {

        binding.menuItemRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MenuItemAdapter(activity, menuList, object : MenuItemAdapter.menuItemOnClick {
            override fun add(position: Int, model: Menu) {
                onClick.onClick(position,model)
            }
        })
        binding.menuItemRecyclerView.adapter = adapter
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

    interface categoryListOnClick {

        fun onClick(position: Int, model: Menu)
    }
}