package driver.configuration

data class WebDriverConfiguration(
        val browser: String,
        override val environment: String,
        override val url: String,
        override val additionalCapabilities: Map<String, Any> = emptyMap()
) : DriverConfiguration