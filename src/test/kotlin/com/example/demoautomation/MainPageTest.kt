package com.example.demoautomation


import driver.DriverFactory
import driver.configuration.BrowserType
import driver.configuration.ConfigProvider
import driver.configuration.Environment
import driver.configuration.WebDriverConfiguration
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.annotations.*
import java.net.URL

class MainPageTest {
    private lateinit var driver: WebDriver

    @BeforeClass
    fun setUpAll() {

        // TODO: remake with parameters, that can make configs
        val config = ConfigProvider.get()
        val a = 0;
//
//        val driverConfiguration = WebDriverConfiguration(
//                environment = Environment.BROWSERSTACK,
//                url = URL("https://hub-cloud.browserstack.com/wd/hub"), //"http://127.0.0.1:4723/wd/hub"
//                platform = Platform.WIN10,
//                platformVersion = "10",
//                additionalCapabilities = emptyMap(),
//                browser = BrowserType.CHROME
//        )
//
//        DriverFactory.initWebDriver(driverConfiguration)
//        driver = DriverFactory.getDriver()
    }

    @BeforeMethod
    fun setUp() {
        driver.get("https://www.jetbrains.com/")
        // todo same test for login page for mobile and for web
    }

    @Test
    fun search() {
        //driver.sessionId

    }

    @Test
    fun toolsMenu() {

    }

    @Test
    fun navigationToAllTools() {

    }
}
