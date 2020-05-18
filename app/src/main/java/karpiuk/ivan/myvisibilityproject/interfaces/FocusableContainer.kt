package karpiuk.ivan.myvisibilityproject.interfaces


interface FocusableContainer {
    var isInFocus: Boolean
    var currentViewInFocus: Focusable?

    fun onNewFocusCaptured(focusable: Focusable?) {
        if (focusable == currentViewInFocus)
            return
        currentViewInFocus?.onLostFocus()
        currentViewInFocus = focusable?.also {
            if (isInFocus)
                it.onCapturedFocus()
        }
    }
}