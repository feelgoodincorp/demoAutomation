package driver.configuration

import org.openqa.selenium.Capabilities
import org.openqa.selenium.MutableCapabilities
import org.openqa.selenium.Platform
import org.openqa.selenium.chrome.ChromeOptions

class DriverConfigurationManager {
    fun setConfiguration(configuration: DriverConfiguration): Capabilities {
        val capabilities = when (configuration) {
            is WebDriverConfiguration -> {
                getBrowserCapabilities(configuration)
            }
            is MobileDriverConfiguration -> {
                getMobileCapabilities(configuration)
            }
            else -> throw Exception("")
        }

        capabilities.setCapability("platformName", configuration.platform.name)
        capabilities.setCapability("environment", configuration.environment.name)
        capabilities.setCapability("os", configuration.platform.family())
        capabilities.setCapability("url", configuration.url)
        capabilities.merge(getEnvironmentCapabilities(configuration))

        return capabilities
    }

    private fun getMobileCapabilities(configuration: MobileDriverConfiguration): MutableCapabilities {
        return MutableCapabilities().apply {
            this.setCapability("deviceName", configuration.deviceName)
            this.setCapability("app", configuration.app)
        }

    }

    private fun getBrowserCapabilities(configuration: WebDriverConfiguration): MutableCapabilities {
        return MutableCapabilities().apply {
            this.setCapability("browserVersion", "latest")

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
                    val platform = this.getCapability("os")
                    // TODO: Create property value if should be applied
                    if (platform.equals(Platform.ANDROID) || platform.equals(Platform.IOS)) {
                        // System.setProperty("appPath", props.getProperty("appPath"))
                    } else {
                        // System.setProperty("webdriver.*.driver", props.getProperty("driverPath"))
                    }
                }
                Environment.BROWSERSTACK -> {
                    this.setCapability("browserstack.user", "cdwmehkfu_OtZYkF")
                    this.setCapability("browserstack.key", "6uzNozVVzrFXvy4jJoHz")
                    this.setCapability("name", "Sample Test")
                    this.setCapability("build", "Build Number 1")
                }
            }
        }
    }
}