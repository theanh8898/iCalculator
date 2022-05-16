package com.example.i_calculator.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.i_calculator.ui.MainViewModel

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {
    val MY_PREFS_NAME = "MyPrefsFile"
    val TAG = "ngphong"
    var baseActivity: AppCompatActivity? = null
        private set

    abstract val viewModel: V

    lateinit var viewBinding: T
        private set

    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int

    val activityViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
        viewBinding.lifecycleOwner = this
        viewBinding.setVariable(bindingVariable, viewModel)
        viewBinding.executePendingBindings()
        initView()
        setupObserver()
    }

    override fun onDestroyView() {
        viewModel.onDestroyView()
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as AppCompatActivity?
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    /**
     * Init default view
     */
    abstract fun initView()

    /**
     * Setup all Observer
     */
    abstract fun setupObserver()

    fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).setMessage(message).show()
    }

    fun hideKeyboard(v: View) {
        val inputManager =
            v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }
}