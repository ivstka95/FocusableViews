package karpiuk.ivan.myvisibilityproject.customViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import androidx.core.widget.NestedScrollView
import karpiuk.ivan.myvisibilityproject.interfaces.Focusable
import karpiuk.ivan.myvisibilityproject.interfaces.FocusableContainer
import karpiuk.ivan.myvisibilityproject.interfaces.VISIBILITY_PERCENTAGE_THRESHOLD

class FocusAwareScrollView : NestedScrollView, FocusableContainer, Focusable {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        doOnNextLayout {
            findFocusableChildren(this)
            handleIdleStateAction.run()
        }
    }

    private var focusablesRecursiveSearchDepth = 2
    override var isInFocus: Boolean = false
    override var currentViewInFocus: Focusable? = null

    private fun findFocusableChildren(view: View) {
        if (view != this && view is Focusable)
            focusableViews.add(view)
        if (focusablesRecursiveSearchDepth > 0) {
            if (view is ViewGroup) {
                focusablesRecursiveSearchDepth--
                for (i in 0 until view.childCount) {
                    findFocusableChildren(view.getChildAt(i))
                }
            }
        }
    }

    private val focusableViews: MutableList<View> = mutableListOf()
    private val handleIdleStateAction = Runnable {
        val viewInFocus =
            focusableViews.filter { getViewVisibilityPercentage(it) > VISIBILITY_PERCENTAGE_THRESHOLD }
                .minBy { it.top } as Focusable
        onNewFocusCaptured(viewInFocus)

        Log.e("viewSeen", "viewInFocus $currentViewInFocus")
        Log.e("viewSeen", "______________________________________________________")
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        removeCallbacks(handleIdleStateAction)
        postDelayed(
            handleIdleStateAction,
            IDLE_STATE_THRESHOLD
        )
    }

    private fun getViewVisibilityPercentage(view: View): Float {
        var visiblePercent = 0f
        val viewLocation = IntArray(2)
        view.getLocationOnScreen(viewLocation)

        //Scroll view location on screen
        val scrollViewLocation = intArrayOf(0, 0)
        getLocationOnScreen(scrollViewLocation)

        //Get the bottom of view.
        val viewBottom = view.height + viewLocation[1]
        //if view's top is inside the scroll view.
        if (viewLocation[1] >= scrollViewLocation[1]) {
            visiblePercent = 100f
            //Get the bottom of scroll view
            val scrollBottom = height + scrollViewLocation[1]
            //If view's bottom is outside from scroll view
            if (viewBottom >= scrollBottom) {
                //Find the visible part of view by subtracting view's top from scrollview's bottom
                val visiblePart = scrollBottom - viewLocation[1]
                visiblePercent = visiblePart.toFloat() / view.height * 100
            }
        } //if view's top is outside the scroll view.
        else {
            //if view's bottom is outside the scroll view
            if (viewBottom > scrollViewLocation[1]) {
                //Find the visible part of view by subtracting scroll view's top from view's bottom
                val visiblePart = viewBottom - scrollViewLocation[1]
                visiblePercent = visiblePart.toFloat() / view.height * 100
            }
        }
        return visiblePercent
    }

    override fun onNewFocusCaptured(focusable: Focusable?) {
        super.onNewFocusCaptured(focusable)
    }

    override fun onCapturedFocus() {
        isInFocus = true
        currentViewInFocus?.onCapturedFocus()
    }

    override fun onLostFocus() {
        isInFocus = false
        currentViewInFocus?.onLostFocus()
    }

    companion object {
        private const val IDLE_STATE_THRESHOLD: Long = 1000
    }
}