package ca.tetervak.fragmentdemo1

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class InfoDialog : DialogFragment() {

    companion object {
        private const val TITLE = "title"
        private const val MESSAGE = "message"

        fun newInstance(title: String, message: String): InfoDialog {
            val fragment = InfoDialog()
            fragment.arguments = bundleOf(TITLE to title, MESSAGE to message)
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(requireContext())
            .setTitle(requireArguments().getString(TITLE))
            .setMessage(requireArguments().getString(MESSAGE))
            .setPositiveButton(android.R.string.ok, null)
            .create()
}