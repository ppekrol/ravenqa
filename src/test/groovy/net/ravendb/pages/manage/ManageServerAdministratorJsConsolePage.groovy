package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
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
