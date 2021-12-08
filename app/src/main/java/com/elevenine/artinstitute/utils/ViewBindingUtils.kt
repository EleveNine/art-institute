package com.elevenine.artinstitute.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author Sherzod Nosirov
 * @since July 29, 2021
 */

/**
 * Utility method that provides the delegate to instantiate and control the lifecycle of Fragments'
 * ViewBindings. Note that the Fragment that uses this delegate must have the layout ID in the
 * constructor of the super [Fragment] class.
 *
 * @param viewBindingFactory the unit function that binds the view of the given layout ID to the
 * given Fragment (Pass here FragmentBinding::bind).
 *
 * credit: [https://engineering.procore.com/fix-your-android-memory-leaks-in-fragments]
 */

fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> T
): ReadOnlyProperty<Fragment, T> = object : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    init {
        viewLifecycleOwnerLiveData.observe(this@viewBinding, Observer { viewLifecycleOwner ->
            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    binding = null
                }
            })
        })

        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                view ?: error(
                    "You must either pass in the layout ID into " +
                            "${this@viewBinding.javaClass.simpleName}'s constructor or inflate " +
                            "a view in onCreateView()"
                )
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.let { return it }

        val viewLifecycleOwner = try {
            thisRef.viewLifecycleOwner
        } catch (e: IllegalStateException) {
            error(
                "Should not attempt to get bindings when Fragment views haven't been created " +
                        "yet. The fragment has not called onCreateView() at this point."
            )
        }
        if (!viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error(
                "Should not attempt to get bindings when Fragment views are destroyed. " +
                        "The fragment has already called onDestroyView() at this point."
            )
        }

        return viewBindingFactory(thisRef.requireView()).also { viewBinding ->
            this.binding = viewBinding
        }
    }
}