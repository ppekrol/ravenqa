package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.TopNavigationBar

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions


class NewIndexPage extends Page {

    final static String NEW_INDEX_ADD_OPTION_BUTTON = "Add"
    final static String NEW_INDEX_MAP_OPTION = "Map"
    final static String NEW_INDEX_REDUCE_OPTION = "Reduce"
    final static String NEW_INDEX_FIELD_OPTION = "Field"
    final static String NEW_INDEX_SPATIAL_FIELD_OPTION = "Spatial Field"
    final static String NEW_INDEX_MAX_INDEX_OUTPUTS_OPTION = "Max Index Outputs"
    final static String NEW_INDEX_STORE_ALL_FIELDS_OPTION = "Store All Fields"

    static at = {
        indexNameInput.displayed
    }

    static content = {
        topNavigation { module TopNavigationBar }
        messagesContainer { module AlertTextModule }

        //tool bar
        menuToolbar { $('.btn-toolbar') }
        saveButton { $("button[title='Save the index (Alt+S)']") }
        addButtonSelector { "button" }
        dropdownContainer { $("ul.dropdown-menu li") }

        // form
        indexNameInput { $("input#indexName") }
        mapsEditors { $("pre#indexEditor textarea") }
        reduceEditors { $("div[data-bind='if: reduce'] pre.form-control.ui-resizable textarea") }
        fieldNameInput { $("input#fieldName0") }
        storageDropdown { $("select.form-control.fieldControl") }
        yesOption { $("option[value='Yes']") }
        spatialFieldNameInput { $("input[placeholder='spatial field name']") }
        maxIndexOutputsInput { $("input[min='0']") }
    }

    def createAndSaveIndex(String name, List<String> maps) {
        indexNameInput = name
        maps.eachWithIndex { map, index ->
            mapsEditors[index].firstElement().sendKeys(map)
        }

        waitFor { !(saveButton.@disabled == 'true') }
        saveButton.click()

        messagesContainer.waitForMessage("Saved " + name)
    }

    def createAndSaveComplexIndex(String name, List<String> maps, List<String> reduce, String field, String spatialField, String maxIndexOutputs) {
        indexNameInput = name
        maps.eachWithIndex { map, index ->
            mapsEditors[index].firstElement().sendKeys(map)
        }
        reduce.eachWithIndex { reduceText, index ->
            reduceEditors[index].firstElement().sendKeys(reduceText)
        }
        fieldNameInput = field
        storageDropdown.click()
        yesOption.click()
        spatialFieldNameInput = spatialField
        maxIndexOutputsInput = maxIndexOutputs

        waitFor { !(saveButton.@disabled == 'true') }
        saveButton.click()

        messagesContainer.waitForMessage("Saved " + name)
    }

    def clickAddButtonAndSelectOption(CharSequence buttonName, CharSequence optionName) {
        def container = menuToolbar
        def addButton
        container.find(addButtonSelector).each{
            if(it.getAttribute("innerHTML").contains(buttonName)){
                addButton = it
            }
        }
        assert addButton

        if(addButton.displayed) {
            addButton.click()

            def dropdown = dropdownContainer
            def option
            dropdown.each {
                if(it.text().equals(optionName)) {
                    option = it
                }
            }
            assert option
            option.click()
        }
    }
}
