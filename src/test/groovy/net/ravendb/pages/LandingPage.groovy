package net.ravendb.pages

import geb.Page
import net.ravendb.modules.CreateDatabaseModalDialog


class LandingPage extends Page {

    static url = "/"

    static at = {
        createDbLink
    }

    static content = {
        createDbLink { $("a", text:"create one now") }
        manageYourServerLink { $("a", text:"manage your server") }

        // build reminder modal dialog
        buildReminderHeader(required:false) { $("h3", text:"Latest Build Reminder") }
        buildReminderCheckbox(required:false) { $("div.messageBox div.checkbox input") }
        buildReminderCloseButton(required:false) { $("div.messageBox button.close i") }

        createDatabaseModalDialog { module CreateDatabaseModalDialog }
    }
}
