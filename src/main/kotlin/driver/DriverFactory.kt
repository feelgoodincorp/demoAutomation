package driver

import driver.configuration.DriverConfiguration
import driver.configuration.DriverConfigurationManager
import driver.configuration.WebDriverConfiguration
import driver.configuration.MobileDriverConfiguration
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.Capabilities
import org.openqa.selenium.Platform
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

object DriverFactory {
    private val driverThreadLocal = ThreadLocal<RemoteWebDriver>()

    // TODO: separated methods as necessary to use native appium methods
    fun initWebDriver(driverConfiguration: WebDriverConfiguration): RemoteWebDriver {
        // TODO: Investigate if params should be passed by files due to BS launch ability
        // val props = Properties()
        // props.load(FileInputStream(capabilities.getCapability(c)))
        // val platform = props.getProperty("platform")

        // val actionsFactory = MobileActionsFactory()
        // val mobileActions = actionsFactory.createActions(driver)
        //
        // mobileActions.hideKeyboard()
        //
        // if (mobileActions is IOSSpecificActions) {
        //     mobileActions.setLocation(37.7749, -122.4194)
        // }

        return initDriver(driverConfiguration) { url, capabilities ->
            when (driverConfiguration.platform) {
                Platform.WIN11,
                Platform.WIN10 -> RemoteWebDriver(url, capabilities)
                else -> throw IllegalArgumentException("Unsupported web platform: ${driverConfiguration.platform}")
            }
        }
    }

    fun initMobileDriver(driverConfiguration: MobileDriverConfiguration): AppiumDriver {
        return initDriver(driverConfiguration) { url, capabilities ->
            when (driverConfiguration.platform) {
                Platform.ANDROID -> AndroidDriver(url, capabilities)
                Platform.IOS -> IOSDriver(url, capabilities)
                else -> throw IllegalArgumentException("Unsupported mobile platform: ${driverConfiguration.platform}")
            }
        }
    }

    fun setDriver(driver: RemoteWebDriver) {
        driverThreadLocal.set(driver)
    }

    fun getDriver(): RemoteWebDriver {
        return driverThreadLocal.get() ?: throw IllegalStateException("Driver is not initialized. Call initDriver(platform) or setDriver(driver) first.")
    }

    private fun <T : RemoteWebDriver> initDriver(
            driverConfiguration: DriverConfiguration,
            driverSupplier: (URL, Capabilities) -> T
    ): T {
        val driver = driverSupplier(driverConfiguration.url, DriverConfigurationManager().getCapability(driverConfiguration))

        return driver.also { setDriver(driver) }
    }
}