package driver

import driver.configuration.DriverConfigurationManager
import driver.configuration.Environment
import driver.configuration.MobileDriverConfiguration
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.Platform
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

object DriverFactory {
    private val driverThreadLocal = ThreadLocal<RemoteWebDriver>()

    fun initDriver(): RemoteWebDriver {
        // TODO: Investigate if params should be passed by files due to BS launch ability
        // val props = Properties()
        // props.load(FileInputStream(capabilities.getCapability(c)))
        // val platform = props.getProperty("platform")

        val driverConfiguration = MobileDriverConfiguration(
                app = "",
                additionalCapabilities = emptyMap(),
                deviceName = "",
                environment = Environment.BROWSERSTACK,
                url = URL(""),
                platform = Platform.WIN11

        )

        val capabilities = DriverConfigurationManager().setConfiguration(driverConfiguration)

        val driver = when (capabilities.platform) {
            Platform.WIN11, Platform.WIN10 -> {
                RemoteWebDriver(URL("https://hub-cloud.browserstack.com/wd/hub"), capabilities)
            }
            // TODO: Investigate if next statements should return appiumdriver
            Platform.ANDROID -> {
                AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
            }
            Platform.IOS -> {
                IOSDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
            }
            else -> throw IllegalArgumentException("Unsupported platform: ${capabilities.platform}")
        }

//        val actionsFactory = MobileActionsFactory()
//        val mobileActions = actionsFactory.createActions(driver)
//
//        mobileActions.hideKeyboard()
//        mobileActions.launchApp()
//        mobileActions.closeApp()
//
//        if (mobileActions is IOSSpecificActions) {
//            mobileActions.setLocation(37.7749, -122.4194)
//        }

        setDriver(driver)

        return driver
    }

    fun setDriver(driver: RemoteWebDriver) {
        driverThreadLocal.set(driver)
    }

    fun getDriver(): RemoteWebDriver {
        return driverThreadLocal.get() ?: throw IllegalStateException("Driver is not initialized. Call initDriver(platform) or setDriver(driver) first.")
    }
}