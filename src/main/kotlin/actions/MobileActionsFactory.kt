package actions

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.remote.RemoteWebDriver

class MobileActionsFactory {
    fun createActions(driver: RemoteWebDriver): MobileActions {

        return when (driver) {
            is AndroidDriver -> AndroidActions(driver)
            is IOSDriver -> IOSActions(driver)
            else -> throw IllegalArgumentException("Unsupported driver type")
        }
    }
}