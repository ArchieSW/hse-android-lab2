package dev.archie.hse_android_lab2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var itemList: MutableList<Item> = mutableListOf()
    var editingPosition: Int = RecyclerView.NO_POSITION

    interface OnDeleteItemClickListener {
        fun onDeleteItemClick(position: Int)
    }

    interface OnEditItemClickListener {
        fun onEditItemClick(position: Int)
    }

    private var onEditItemClickListener: OnEditItemClickListener? = null
    private var onDeleteItemClickListener: OnDeleteItemClickListener? = null

    fun setOnDeleteItemClickListener(listener: OnDeleteItemClickListener) {
        onDeleteItemClickListener = listener
    }

    fun setOnEditItemClickListener(listener: OnEditItemClickListener) {
        onEditItemClickListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val largeTextView: TextView = itemView.findViewById(R.id.textViewItemName)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewItemQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = itemList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindItem(holder, position)
        setupDeleteButton(holder, position)
        setupEditButton(holder, position)
    }

    private fun bindItem(holder: ViewHolder, position: Int) {
        holder.largeTextView.text = itemList[position].itemName
        holder.smallTextView.text = "Quantity: ${itemList[position].itemQuantity}"
    }

    private fun setupDeleteButton(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            onDeleteItemClickListener?.onDeleteItemClick(position)
        }
    }

    private fun setupEditButton(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<Button>(R.id.buttonEditItem).setOnClickListener {
            editingPosition = position
            onEditItemClickListener?.onEditItemClick(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItemList: List<Item>) {
        itemList.clear()
        itemList.addAll(newItemList)
        notifyDataSetChanged()
    }
}
