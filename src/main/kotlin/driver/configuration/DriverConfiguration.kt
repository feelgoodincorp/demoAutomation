package driver.configuration

import org.openqa.selenium.Platform
import java.net.URL

interface DriverConfiguration{
        val environment: Environment
        val url: URL
        val platform: Platform
        val additionalCapabilities: Map<String, Any>
}
enum class Environment{
        LOCAL,
        BROWSERSTACK
}

enum class BrowserType{
        FIREFOX,
        SAFARI,
        OPERA,
        EDGE,
        CHROME
}