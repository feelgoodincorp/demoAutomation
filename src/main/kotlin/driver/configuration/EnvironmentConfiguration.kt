package driver.configuration

data class EnvironmentConfiguration(
    val browser: String = "chrome",
    val execution: String = "local",
    val gridUrl: String? = null,
    val headless: Boolean = false,
    val platform: String = "WIN11",
    val appPackage: String? = null,
    val appActivity: String? = null,
    val baseUrl: String = "https://example.com",
)
