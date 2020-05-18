package karpiuk.ivan.myvisibilityproject.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.doOnNextLayout
import androidx.viewpager.widget.ViewPager
import karpiuk.ivan.myvisibilityproject.interfaces.Focusable
import karpiuk.ivan.myvisibilityproject.interfaces.FocusableContainer
import java.lang.reflect.Field


class CustomViewPager : ViewPager, FocusableContainer {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override var isInFocus: Boolean = true
    override var currentViewInFocus: Focusable? = null
    private val onFocusChangeListener = object : SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            updateFocus()
        }
    }

    private fun updateFocus() {
        val view = getCurrentView()
        if (view is Focusable)
            onNewFocusCaptured(view)

    }

    init {
        doOnNextLayout { updateFocus() }
    }

    private fun getCurrentView(): View? {
        try {
            val currentItem = currentItem
            for (i in 0 until childCount) {
                val child: View = getChildAt(i)
                val layoutParams =
                    child.layoutParams as LayoutParams
                val f: Field =
                    layoutParams.javaClass.getDeclaredField("position")
                f.isAccessible = true
                val position = f.get(layoutParams) as Int
                if (!layoutParams.isDecor && currentItem == position) {
                    return child
                }
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addOnPageChangeListener(onFocusChangeListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeOnPageChangeListener(onFocusChangeListener)
    }
}