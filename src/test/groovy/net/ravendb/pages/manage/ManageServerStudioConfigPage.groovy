package net.ravendb.pages.manage

import geb.Page
import net.ravendb.modules.manage.ManageServerMenu

import org.openqa.selenium.WebElement


class ManageServerStudioConfigPage extends Page {

    public final static OPTION_ON = "On"
    public final static OPTION_OFF = "Off"

    static at = {
        $("div[data-view='views/manage/studioConfig']").displayed
        menu.toSystemDatabaseLink.displayed
    }

    static content = {
        // modules
        menu { module ManageServerMenu }

        // content
        warnWhenUsingSystemDatabaseButton { $("button.dropdown-toggle")[0] }
        warnWhenUsingSystemDatabaseOnLink { warnWhenUsingSystemDatabaseButton.parent().$("a", text:OPTION_ON) }
        warnWhenUsingSystemDatabaseOffLink { warnWhenUsingSystemDatabaseButton.parent().$("a", text:OPTION_OFF) }
    }

    boolean isOptionDisplayed(WebElement button, String option) {
        return button.$("span", text:option).displayed
    }
}
