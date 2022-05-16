package com.example.i_calculator.ui

import androidx.activity.viewModels
import com.example.i_calculator.BR
import com.example.i_calculator.R
import com.example.i_calculator.base.BaseActivity
import com.example.i_calculator.databinding.ActivityMainBinding
import com.example.i_calculator.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.layout_frame,
                HomeFragment.newInstance(),
                HomeFragment::class.java.name
            )
            .addToBackStack(HomeFragment::class.java.name).commit()
    }

    override fun setupObserver() {
    }

    override fun onBackPressed() {
        if (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name == HomeFragment::class.java.name) {
            finishAffinity()
        } else super.onBackPressed()
    }
}