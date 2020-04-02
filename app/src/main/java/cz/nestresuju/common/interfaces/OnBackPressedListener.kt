package cz.nestresuju.common.interfaces

/**
 * Interface for custom handlers of back button.
 */
interface OnBackPressedListener {

    /**
     * Called when user taps back button. Returns true if action was handled by this listener.
     */
    fun onBackPressed(): Boolean
}