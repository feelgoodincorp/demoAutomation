package driver.configuration

import org.openqa.selenium.Platform
import java.net.URL

data class WebDriverConfiguration(
        val browser: BrowserType,
        val browserVersion: String = "latest",
        override val platform: Platform,
        override val platformVersion: String,
        override val environment: Environment,
        override val url: URL,
        override val additionalCapabilities: Map<String, Any> = emptyMap()
) : DriverConfiguration