package net.ravendb.geb

import geb.Browser
import geb.navigator.Navigator

import org.openqa.selenium.WebDriverException
import org.openqa.selenium.WebElement


class NonEmptyNavigator extends geb.navigator.NonEmptyNavigator {
    NonEmptyNavigator(Browser browser, Collection<? extends WebElement> contextElements) {
        super(browser, contextElements)
    }

    @Override
    Navigator click() {
        try {
            contextElements.first().click()
        } catch(WebDriverException e) {
            if(e.message.contains("Element not clickable")) {
                sleep(5000)
                contextElements.first().click()
            } else {
                throw e
            }
        }
        this
    }
}
