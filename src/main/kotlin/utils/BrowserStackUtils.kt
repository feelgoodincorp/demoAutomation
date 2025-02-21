package utils

import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

class BrowserStackUtils {

    fun uploadApp() {
        // curl -u "BROWSERSTACK_USERNAME:BROWSERSTACK_ACCESS_KEY" \
        //    -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
        //    -F "file=@path/to/your/app.apk"

        // receive json {
        //  "app_url": "bs://<unique-app-id>"
        //}
    }

    fun startTestOnBrowserStack() {
        val capabilities = DesiredCapabilities().apply {
            setCapability("browserstack.user", "YOUR_USERNAME")
            setCapability("browserstack.key", "YOUR_ACCESS_KEY")
            setCapability("app", "bs://<unique-app-id>")
            setCapability("device", "Google Pixel 7")
            setCapability("os_version", "13.0")
            setCapability("automationName", "UiAutomator2")
        }

        val driver = AndroidDriver(URL("http://hub-cloud.browserstack.com/wd/hub"), capabilities)
    }
}