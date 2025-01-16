package driver.configuration

data class MobileDriverConfiguration(
        val deviceName: String,
        val app: String?,
        override val environment: String,
        override val url: String,
        override val additionalCapabilities: Map<String, Any> = emptyMap()
) : DriverConfiguration