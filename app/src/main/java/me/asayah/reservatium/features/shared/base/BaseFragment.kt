package me.asayah.reservatium.features.shared.base

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment: Fragment() {

    protected fun createSnackbar(view: View, @StringRes id: Int): Snackbar {
        return Snackbar.make(view, id, Snackbar.LENGTH_SHORT).apply {
            show()
        }
    }
}