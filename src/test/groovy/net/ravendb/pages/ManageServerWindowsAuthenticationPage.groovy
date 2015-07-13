package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerWindowsAuthenticationPage extends Page {

    static at = {
        addUserSettingsButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        addUserSettingsButton { $("button[title='Add user settings(Alt+N)']") }
    }
}
