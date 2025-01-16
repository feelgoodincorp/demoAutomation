package utils

import driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

object ElementUtils {
    fun getElementBy(
            elementKey: String,
            findBy: FindBy,
            withException: Boolean = false
    ): WebElement {
        // TODO: surround with try catch
        val webElement = DriverFactory.getDriver().findElement(getFindByRule(elementKey, findBy))

        // TODO: add retries

        return if(withException && webElement == null) {
            throw NoSuchElementException()
        } else {
            webElement
        }
    }

    private fun getFindByRule(elementKey: String, findBy: FindBy) : By {
        return when (findBy) {
            FindBy.XPATH -> By.id(elementKey)
            FindBy.CLASS -> By.className(elementKey)
            FindBy.CSS_SELECTOR -> By.cssSelector(elementKey)
            FindBy.ID -> By.xpath(elementKey)
        }
    }

    enum class FindBy {
        XPATH, CLASS, CSS_SELECTOR, ID
    }
}


