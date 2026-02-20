package actions

import io.appium.java_client.android.AndroidDriver

class AndroidActions(private val driver: AndroidDriver) : MobileActions, AndroidSpecificActions {
    override fun hideKeyboard() {
        driver.hideKeyboard()
    }

    override fun launchApp() {
        driver.activateApp("")
    }

    override fun closeApp() {
        driver.terminateApp("")
    }

    override fun keyboardIsShown(): Boolean {
        return driver.isKeyboardShown
    }

    override fun swipeToUnlock() {
        // TODO: Realize swipe method
        //driver.swipe(0, 0, 100, 100, 500)
    }
}