package driver

import driver.configuration.BrowserType
import driver.configuration.DriverConfiguration
import driver.configuration.DriverConfigurationManager
import driver.configuration.Environment
import driver.configuration.WebDriverConfiguration
import driver.configuration.MobileDriverConfiguration
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.Capabilities
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

object DriverFactory {
    private val driverThreadLocal = ThreadLocal<WebDriver>()

    // TODO: separated methods as necessary to use native appium methods
    fun initWebDriver(driverConfiguration: WebDriverConfiguration): WebDriver {
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
            when(driverConfiguration.environment) {
                Environment.LOCAL -> RemoteWebDriver(url, capabilities)
                Environment.BROWSERSTACK -> {
                    when (driverConfiguration.browser) {
                        BrowserType.CHROME -> ChromeDriver()
                        else -> throw IllegalArgumentException("Unsupported web browser: ${driverConfiguration.browser}")
                    }
                }
            }

//            when (driverConfiguration.platform) {
//                Platform.WIN11,
//                Platform.WIN10 -> {
//
//                }
//                else -> throw IllegalArgumentException("Unsupported web platform: ${driverConfiguration.platform}")
//            }
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

    fun setDriver(driver: WebDriver) {
        driverThreadLocal.set(driver)
    }

    fun getDriver(): WebDriver {
        return driverThreadLocal.get() ?: throw IllegalStateException("Driver is not initialized. Call initDriver(platform) or setDriver(driver) first.")
    }

    private fun <T : WebDriver> initDriver(
            driverConfiguration: DriverConfiguration,
            driverSupplier: (URL, Capabilities) -> T
    ): T {
        val driver = driverSupplier(driverConfiguration.url, DriverConfigurationManager().getCapability(driverConfiguration))

        return driver.also { setDriver(driver) }
    }
}