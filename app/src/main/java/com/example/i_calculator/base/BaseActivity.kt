package com.example.i_calculator.base

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.i_calculator.R

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val bindingVariable: Int
    lateinit var viewDataBinding: T
        private set

    /**
     * Get ViewModel with this activity
     *
     * @return ViewModel instance
     */
    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onViewCreated()
        performDataBinding()
        initView()
        setupErrorWatcher()
        setupObserver()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.setVariable(bindingVariable, viewModel)
        viewDataBinding.executePendingBindings()
    }

    /**
     * Init default view
     */
    abstract fun initView()

    /**
     * Setup all Observer
     */
    abstract fun setupObserver()

    private fun setupErrorWatcher() {
        viewModel.errorDialogWatcher.observe(this, Observer {
            showAlertDialog(getString(R.string.app_name), it)
        })

        viewModel.errorToastyWatcher.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun showAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message).setTitle(title)
        val dialog = builder.create()
        dialog.show()
    }
}