package com.example.demoautomation

import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.element

// page_url = https://www.jetbrains.com/
class MainPage {
    val seeDeveloperToolsButton = element(byXpath("//*[@data-test-marker='Developer Tools']"))
    val findYourToolsButton = element(byXpath("//*[@data-test='suggestion-action']"))
    val toolsMenu = element(byXpath("//div[@data-test='main-menu-item' and @data-test-marker = 'Developer Tools']"))
    val searchButton = element("[data-test='site-header-search-action']")
}
