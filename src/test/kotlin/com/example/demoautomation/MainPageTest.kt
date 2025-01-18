package com.example.demoautomation


import driver.DriverFactory
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.annotations.*

class MainPageTest {
    private lateinit var driver: RemoteWebDriver;


        @BeforeClass
        fun setUpAll() {
            DriverFactory.initDriver()
            driver = DriverFactory.getDriver()
        }

    @BeforeMethod
    fun setUp() {
        driver.get("https://www.jetbrains.com/")
    }

    @Test
    fun search() {
        driver.sessionId

    }

    @Test
    fun toolsMenu() {

    }

    @Test
    fun navigationToAllTools() {

    }
}
