package net.ravendb.utils

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions


public class HtmlUtils {
    static makeElementVisible(def browser, def element) {
        // make file input visible (needs to be more than 10px x 10px for IE web driver)
        String js = "arguments[0].style.height='20px'; arguments[0].style.width='20px'; arguments[0].style.visibility='visible'; arguments[0].style.display='block';arguments[0].style.opacity=1;"
        ((JavascriptExecutor) browser.driver).executeScript(js, element.firstElement());
    }

    static scrollIntoView(def browser, def element) {
        ((JavascriptExecutor) browser.driver).executeScript("arguments[0].scrollIntoView(true);", element.firstElement());
    }

    static scrollToTop(def browser) {
        Actions actions = new Actions(browser.driver)
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform()
    }
}
