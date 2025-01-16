package driver.configuration

import org.openqa.selenium.remote.DesiredCapabilities

class DriverConfigurationManager {

    // TODO: Check if DesiredCapabilities is deprecated and DriverConfiguration is better choice
    fun setConfiguration(configuration: DriverConfiguration): DesiredCapabilities {
        val capabilities = DesiredCapabilities().apply {
            setCapability("platformName", "iOS")
        }

        when (capabilities.toString()) {
            // TODO: Replace with enum
            "android" -> {
                capabilities.setCapability("platformName", "Android")
                capabilities.setCapability("deviceName", "emulator-5554")

            }
            "ios" -> {
                capabilities.setCapability("platformName", "iOS")
                capabilities.setCapability("deviceName", "iPhone 12")
            }
            "web" -> {
                capabilities.setCapability("browserName", "Chrome")
            }
        }

        when (capabilities.toString()) {
            // TODO: Replace with enum
            "local" -> {
                if (capabilities.getCapability("mobileDevice") != null) {

                    capabilities.setCapability("platformName", "Android")
                    capabilities.setCapability("deviceName", capabilities.getCapability("mobileDevice"))
                    capabilities.setCapability("app", capabilities.getCapability("app"))
                } else {
                    // System.setProperty("webdriver.chrome.driver", props.getProperty("driverPath"))
                }
            }
            "browserstack" -> {
                capabilities.setCapability("browserstack.user", capabilities.getCapability("browserstack.user"))
                capabilities.setCapability("browserstack.key", capabilities.getCapability("browserstack.key"))
                capabilities.setCapability("browser", capabilities.getCapability("browser"))
                capabilities.setCapability("app", capabilities.getCapability("app"))
                capabilities.setCapability("device", capabilities.getCapability("mobileDevice"))
            }
        }

        return capabilities
    }
}