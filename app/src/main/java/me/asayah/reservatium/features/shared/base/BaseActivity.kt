package me.asayah.reservatium.features.shared.base

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

abstract class BaseActivity: AppCompatActivity() {

    private var toolbar: MaterialToolbar? = null

    protected fun setupTopAppBar(toolbar: MaterialToolbar) {
        this.toolbar = toolbar

        setSupportActionBar(this.toolbar)
        this.toolbar?.setNavigationOnClickListener { onBackPressed() }
        this.supportActionBar?.run {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    protected fun setToolbarTitle(@StringRes id: Int) {
        this.toolbar?.setTitle(id)
    }
}