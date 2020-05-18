package karpiuk.ivan.myvisibilityproject.customViews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import karpiuk.ivan.myvisibilityproject.interfaces.Focusable

class FocusableLinearLayout(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs), Focusable {

    override fun onCapturedFocus() {
        setBackgroundColor(Color.CYAN)
    }

    override fun onLostFocus() {
        setBackgroundColor(Color.WHITE)
    }
}