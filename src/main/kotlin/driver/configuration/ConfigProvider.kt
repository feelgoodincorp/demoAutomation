package driver.configuration

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import java.io.File

object ConfigProvider {
    private val configuration : EnvironmentConfiguration by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        loadConfig()
    }

    fun get(): EnvironmentConfiguration = configuration

    private fun loadConfig(): EnvironmentConfiguration {
        val configFilePath = System.getProperty("configFile")
            ?: System.getenv("CONFIG_FILE")
            ?: "config/local.yaml"

        val file = File(configFilePath)
        if (!file.exists() || !file.isFile) {
            return EnvironmentConfiguration()
        }

        val yamlContent = file.readText()
        var loaded = Yaml.default.decodeFromString<EnvironmentConfiguration>(yamlContent)

        val props = System.getProperties()
        loaded = loaded.copy(
            browser   = props.getProperty("test.browser",   loaded.browser),
            execution = props.getProperty("test.execution", loaded.execution),
            gridUrl   = props.getProperty("test.gridUrl",   loaded.gridUrl) ?: loaded.gridUrl,
            headless  = props.getProperty("test.headless")?.toBoolean() ?: loaded.headless
        )

        System.getenv("TEST_BROWSER")?.let { loaded = loaded.copy(browser = it) }
        System.getenv("TEST_EXECUTION")?.let { loaded = loaded.copy(execution = it) }

        return loaded
    }
}