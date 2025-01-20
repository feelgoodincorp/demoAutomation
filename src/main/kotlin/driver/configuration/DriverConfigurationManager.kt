package driver.configuration

import org.openqa.selenium.Capabilities
import org.openqa.selenium.MutableCapabilities
import org.openqa.selenium.Platform
import org.openqa.selenium.chrome.ChromeOptions

/**
 * Translates specified DriverConfiguration with defined parameters value to Capabilities variable
 */
class DriverConfigurationManager {
    fun getCapability(configuration: DriverConfiguration): Capabilities {
        val capabilities = when (configuration) {
            is WebDriverConfiguration -> {
                getBrowserCapabilities(configuration)
            }
            is MobileDriverConfiguration -> {
                getMobileCapabilities(configuration)
            }
            else -> throw IllegalArgumentException("Unspecified driver configuration")
        }

        capabilities.setCapability("url", configuration.url)
        capabilities.setCapability("platformName", configuration.platform.name)
        capabilities.setCapability("environment", configuration.environment.name)
        return capabilities.merge(getEnvironmentCapabilities(configuration))
    }

    private fun getMobileCapabilities(configuration: MobileDriverConfiguration): MutableCapabilities {
        return MutableCapabilities().apply {
            this.setCapability("deviceName", configuration.deviceName)
            this.setCapability("app", configuration.app)
        }
    }

    private fun getBrowserCapabilities(configuration: WebDriverConfiguration): MutableCapabilities {
        return MutableCapabilities().apply {
            val browserSpecificCapabilities = when (configuration.browser) {
                BrowserType.CHROME -> {
                    setCapability("browserName", BrowserType.CHROME.name)

                    ChromeOptions().apply {
                        addArguments("--disable-popup-blocking")
                    }
                }
                BrowserType.FIREFOX -> TODO()
                BrowserType.EDGE -> TODO()
                BrowserType.SAFARI -> TODO()
                BrowserType.OPERA -> TODO()
            }

            merge(browserSpecificCapabilities)
        }
    }

    private fun getEnvironmentCapabilities(configuration: DriverConfiguration): MutableCapabilities {
        return MutableCapabilities().apply {
            when (configuration.environment) {
                Environment.LOCAL -> {
                    // TODO: Create property value if should be applied
                    if (configuration.platform == Platform.ANDROID || configuration.platform == Platform.IOS) {
                        // System.setProperty("appPath", props.getProperty("appPath"))
                    } else {
                        // System.setProperty("webdriver.*.driver", props.getProperty("driverPath"))
                    }
                }
                Environment.BROWSERSTACK -> {
                    setCapability("os", configuration.platform.family().name.lowercase().replaceFirstChar { it.uppercase() })
                    setCapability("osVersion", configuration.platformVersion)
                    setCapability("browserstack.user", "cdwmehkfu_OtZYkF")
                    setCapability("browserstack.key", "6uzNozVVzrFXvy4jJoHz")
                    setCapability("name", "Sample Test")
                    setCapability("build", "Build Number 1")
                }
            }
        }
    }
}