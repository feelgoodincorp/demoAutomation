package actions

import io.appium.java_client.ios.IOSDriver

class IOSActions(private val driver: IOSDriver) : MobileActions, IOSSpecificActions {
    override fun hideKeyboard() {
        driver.executeScript("mobile: hideKeyboard")
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

    override fun setLocation(latitude: Double, longitude: Double) {
        driver.executeScript("mobile: setLocation", mapOf("latitude" to latitude, "longitude" to longitude))
    }
}