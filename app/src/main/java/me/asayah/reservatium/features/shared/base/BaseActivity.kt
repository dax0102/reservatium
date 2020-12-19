package me.asayah.reservatium.features.shared.base

import android.view.View
import android.widget.Toolbar
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity: AppCompatActivity() {

    private var toolbar: MaterialToolbar? = null

    protected fun createSnackbar(view: View, @StringRes id: Int): Snackbar {
        return Snackbar.make(view, id, Snackbar.LENGTH_SHORT).apply {
            show()
        }
    }

    protected fun setupToolbar(toolbar: MaterialToolbar) {
        this.toolbar = toolbar

        setSupportActionBar(this.toolbar)
        this.toolbar?.setNavigationOnClickListener { onBackPressed() }
        this.supportActionBar?.run {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    protected fun setToolbarTitle(title: String?) {
        this.toolbar?.title = title
    }

    protected fun setToolbarTitle(@StringRes id: Int) {
        this.toolbar?.setTitle(id)
    }

}