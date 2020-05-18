package karpiuk.ivan.myvisibilityproject.interfaces

const val VISIBILITY_PERCENTAGE_THRESHOLD: Byte = 50

interface Focusable {
    fun onCapturedFocus()
    fun onLostFocus()
}