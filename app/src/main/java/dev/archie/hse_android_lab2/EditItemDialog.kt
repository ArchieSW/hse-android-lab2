package dev.archie.hse_android_lab2

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class EditItemDialog(private val itemName: String, private val itemQuantity: Int) : DialogFragment() {

    interface EditItemDialogListener {
        fun onEditItem(itemName: String, itemQuantity: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.edit_item_dialog, null)

        val editTextItemName = view.findViewById<EditText>(R.id.editItemDialogItemName)
        val editTextItemQuantity = view.findViewById<EditText>(R.id.editItemDialogItemQuantity)

        editTextItemName.setText(itemName)
        editTextItemQuantity.setText(itemQuantity.toString())

        builder.setView(view)
            .setPositiveButton("Apply changes") {_, _ ->
                val newName = editTextItemName.text.toString().trim()
                val newQuantity = editTextItemQuantity.text.toString().trim().toIntOrNull()

                val listener = requireActivity() as? EditItemDialogListener
                newQuantity?.let { listener?.onEditItem(newName, it) }
            }
            .setNegativeButton("Cancel") {_, _ ->
                dialog?.cancel()
            }
        return builder.create()
    }
}
