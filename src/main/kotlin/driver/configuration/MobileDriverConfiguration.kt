package driver.configuration

import org.openqa.selenium.Platform
import java.net.URL

data class MobileDriverConfiguration(
        val deviceName: String,
        val app: String?,
        override val platform: Platform,
        override val platformVersion: String,
        override val environment: Environment,
        override val url: URL,
        override val additionalCapabilities: Map<String, Any> = emptyMap()
) : DriverConfiguration