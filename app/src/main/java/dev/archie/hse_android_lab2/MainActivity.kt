package dev.archie.hse_android_lab2;

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity: AppCompatActivity(), AddItemDialog.AddItemDialogListener,
    RecyclerAdapter.OnDeleteItemClickListener,
    RecyclerAdapter.OnEditItemClickListener,
    EditItemDialog.EditItemDialogListener {

    private val adapter = RecyclerAdapter()
    private val products = mutableListOf<Item>()
    private lateinit var viewFlipper: ViewFlipper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.viewFlipper = findViewById(R.id.flipper)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.setOnDeleteItemClickListener(this)
        adapter.setOnEditItemClickListener(this)
    }

    fun openDialogMenu(view: View) {
        val dialog = AddItemDialog()
        dialog.show(supportFragmentManager, "AddItemDialog")
    }

    override fun onItemAdded(itemName: String, itemQuantity: Int) {
        val item = Item(itemName, itemQuantity)
        products.add(item)
        updateAdapter()
    }

    override fun onDeleteItemClick(position: Int) {
        products.removeAt(position)
        updateAdapter()
    }

    override fun onEditItemClick(position: Int) {
        val currentItem = products[position]
        val editDialog = EditItemDialog(currentItem.itemName, currentItem.itemQuantity)
        editDialog.show(supportFragmentManager, "editItemDialog")
    }

    override fun onEditItem(itemName: String, itemQuantity: Int) {
        val item = Item(itemName, itemQuantity)
        products[adapter.editingPosition] = item
        updateAdapter()
    }

    private fun updateAdapter() {
        adapter.updateItems(products)
        if (products.isEmpty()) {
            viewFlipper.displayedChild = 1 // Show "No items to show" message
        } else {
            viewFlipper.displayedChild = 0 // Show RecyclerView
        }
    }
}
