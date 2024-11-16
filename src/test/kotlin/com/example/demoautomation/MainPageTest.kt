package com.example.demoautomation

import com.codeborne.selenide.Condition.attribute
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selectors.*
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.selenide.AllureSelenide
import java.util.*
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.Assert.*
import org.testng.annotations.*
import java.net.URI
import java.net.URISyntaxException

class MainPageTest {
    var mainPage = MainPage()
    @BeforeMethod
    fun setUp() {
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
//        Configuration.browser = "firefox";
//        Configuration.browserCapabilities = new FirefoxOptions();//.addArguments("--remote-allow-origins=*");
        val userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1"
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("webSocketUrl", true)
        val options = FirefoxOptions()
        options.merge(capabilities)
        options.setCapability("webSocketUrl", true)
        val profile = FirefoxProfile()
        profile.setPreference("general.useragent.override", userAgent)
        options.setProfile(profile)
        val driver: WebDriver = FirefoxDriver(options)
        val wsUrl = (driver as RemoteWebDriver).getCapabilities().getCapability("webSocketUrl") as String
        println("WebSocket URL: $wsUrl")

        // Set device emulation (example for iPhone X)
        val deviceWidth = 375
        val deviceHeight = 812
        driver["https://www.example.com"]
        DeviceEmulation.setDeviceEmulation(driver, deviceWidth, deviceHeight, userAgent)

        // Now you can start interacting with the emulated device


        // Implement your WebSocket client to interact with WebDriver BiDi
        var client: MyWebSocketClient? = null
        client = try {
            MyWebSocketClient(URI(wsUrl))
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
        client.connect()
    }

    @Test
    fun search() {
        mainPage.searchButton.click()
        Selenide.`$`("[data-test='search-input']").sendKeys("Selenium")
        Selenide.`$`("button[data-test='full-search-button']").click()
        Selenide.`$`("input[data-test='search-input']").shouldHave(attribute("value", "Selenium"))
    }

    object DeviceEmulation {
        fun setDeviceEmulation(driver: WebDriver, width: Int, height: Int, userAgent: String) {
            val script = ("window.screen = { width: " + width + ", height: " + height + ", availWidth: " + width + ", availHeight: " + height + " };"
                    + "navigator.__defineGetter__('userAgent', function() { return '" + userAgent + "'; });")
            (driver as JavascriptExecutor).executeScript(script)
        }
    }

    inner class MyWebSocketClient(serverUri: URI?) : WebSocketClient(serverUri) {
        fun onOpen(handshakedata: ServerHandshake?) {
            println("Connected to WebSocket")
        }

        fun onMessage(message: String) {
            println("Received message: $message")
        }

        fun onClose(code: Int, reason: String, remote: Boolean) {
            println("WebSocket closed: $reason")
        }

        fun onError(ex: Exception) {
            println("WebSocket error: " + ex.message)
        }
    }


    companion object {
        @JvmStatic
        @BeforeClass
        fun setUpAll() {
            Configuration.browserSize = "1280x800"
            SelenideLogger.addListener("allure", AllureSelenide())
        }
    }
}
