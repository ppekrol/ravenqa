package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.TopNavigationBar


class EditIndexPage extends Page {

    final static String TRY_INDEX = "Try index"
    final static String MAKE_PERMANENT = "Make permanent"

    static at = {
        indexNameInput
    }

    static content = {
        // modules
        topNavigation { module TopNavigationBar }
        alert { module AlertTextModule }

        //tool bar
        menuToolbar { $('.btn-toolbar') }
        editOptionButtonSelector { ".dropdown-toggle" }
        editDropdownMenuSelector { "ul.dropdown-menu li" }

        warningContainer { $('div.alert-warning') }

        // form
        indexNameInput { $("input#indexName") }
        mapsEditors { $("pre#indexEditor textarea") }
    }

    def clickEditIndexOption(CharSequence optionName) {
        def container = menuToolbar
        container.find(editOptionButtonSelector).click()
        def option
        container.find(editDropdownMenuSelector).each {
            if(it.getAttribute("innerHTML").contains(optionName)) {
                option = it
            }
        }
        assert option
        option.click()
    }
}
