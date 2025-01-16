package driver.configuration

interface DriverConfiguration{
        val environment: String;
        val url: String;
        val additionalCapabilities: Map<String, Any>;
}