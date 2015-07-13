package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerAdministratorJsConsolePage extends Page {

    static at = {
        executeCommandButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        executeCommandButton { $("button", text:"Execute command") }
    }
}
