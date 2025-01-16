package actions

interface MobileActions {
    fun hideKeyboard()
    fun launchApp()
    fun closeApp()
    fun keyboardIsShown(): Boolean
}

interface AndroidSpecificActions {
    fun swipeToUnlock()
}

interface IOSSpecificActions {
    fun setLocation(latitude: Double, longitude: Double)
}