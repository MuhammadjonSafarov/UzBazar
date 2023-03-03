package uz.xia.bazar.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import uz.xia.bazar.R

class ProgressDialog:DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog=AlertDialog.Builder(requireContext(),R.style.FullScreenDialogTransparent)
            .setView(R.layout.item_progress_loading)
            .setCancelable(false)
            .create()
        super.setCancelable(false)
        return dialog
    }

    override fun getTheme(): Int = R.style.FullScreenDialogTransparent
}