package driver.configuration

import org.openqa.selenium.Platform
import java.net.URL

data class WebDriverConfiguration(
        val browser: BrowserType,
        override val platform: Platform,
        override val environment: Environment,
        override val url: URL,
        override val additionalCapabilities: Map<String, Any> = emptyMap()
) : DriverConfiguration