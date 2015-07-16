package net.ravendb.modules

import geb.Module


class DeleteResourceModalDialog extends Module {

    final static String DELETE_OPTION_KEEP_FILES = "Keep files on disk"
    final static String DELETE_OPTION_DELETE_EVERYTING = "Delete everything"

    static content = {
        header { $("h4", text:"Sure?") }
        deleteOptionsButton { $("button[data-toggle='dropdown']") }
        deleteOptionsDropdown { $("ul.dropdown-menu") }
        confirmButton { $("button", text:"Yep, delete") }
    }

    def confirm(String deleteOption) {
        if(deleteOption) {
            deleteOptionsButton.click()

            def toClick
            deleteOptionsDropdown.$("a").each {
                if(it.text().equals(deleteOption)) {
                    toClick = it
                }
            }
            assert toClick
            toClick.click()
        }

        confirmButton.click()
    }
}
