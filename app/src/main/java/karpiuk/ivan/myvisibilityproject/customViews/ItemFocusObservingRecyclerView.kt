package karpiuk.ivan.myvisibilityproject.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.doOnNextLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import karpiuk.ivan.myvisibilityproject.Utils
import karpiuk.ivan.myvisibilityproject.interfaces.Focusable
import karpiuk.ivan.myvisibilityproject.interfaces.FocusableContainer
import karpiuk.ivan.myvisibilityproject.interfaces.VISIBILITY_PERCENTAGE_THRESHOLD


class ItemFocusObservingRecyclerView(context: Context, attrs: AttributeSet?) :
    RecyclerView(context, attrs), FocusableContainer, Focusable {

    private val TAG = ItemFocusObservingRecyclerView::class.simpleName
    override var isInFocus: Boolean = false

    override var currentViewInFocus: Focusable? = null

    init {
        doOnNextLayout { updateViewsFocus() }
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        currentViewInFocus?.let {
            val percentage = Utils.calculateVisibilityPercentage(this, it as View)
            if (percentage < VISIBILITY_PERCENTAGE_THRESHOLD) {
                onNewFocusCaptured(null)
            }
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == SCROLL_STATE_IDLE)
            updateViewsFocus()
    }

    private fun updateViewsFocus() {
        val layoutManager = layoutManager

        currentViewInFocus?.let {
            val percentage = Utils.calculateVisibilityPercentage(this, it as View)
            if (percentage < VISIBILITY_PERCENTAGE_THRESHOLD) {
                onNewFocusCaptured(null)
            }
        }

        if (layoutManager is LinearLayoutManager) {
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

            val isEndOfList = !canScrollVertically(1)

            var visiblePositions = (firstVisiblePosition..lastVisiblePosition).toList()

            if (isEndOfList) {
                visiblePositions = visiblePositions.toList()
            }

            for (i in visiblePositions) {
                val viewByPosition = layoutManager.findViewByPosition(i) ?: continue
                val visibilityPercentage = Utils.calculateVisibilityPercentage(this, viewByPosition)

                if (visibilityPercentage < VISIBILITY_PERCENTAGE_THRESHOLD) {
                    continue
                }

                if (viewByPosition is Focusable) {
                    onNewFocusCaptured(viewByPosition)
                    break
                }
            }
        }
    }

    override fun onCapturedFocus() {
        isInFocus = true
        currentViewInFocus?.onCapturedFocus()
    }

    override fun onLostFocus() {
        isInFocus = false
        currentViewInFocus?.onLostFocus()
    }
}