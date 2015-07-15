package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.TopNavigationBar

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions


class NewIndexPage extends Page {

    static at = {
        indexNameInput
    }

    static content = {
        topNavigation { module TopNavigationBar }
        alert { module AlertTextModule }

        //tool bar
        saveButton { $("button[title='Save the index (Alt+S)']") }

        // form
        indexNameInput { $("input#indexName") }
        mapsEditors { $("pre#indexEditor textarea") }
    }

    def createAndSaveIndex(String name, List<String> maps) {
        indexNameInput = name
        maps.eachWithIndex { map, index ->
            mapsEditors[index].firstElement().sendKeys(map)
        }

        sleep(10000)
        waitFor { !(saveButton.@disabled == 'true') }
        saveButton.click()

        waitFor { alert.containsMessage("Saved " + name) }
    }
}
