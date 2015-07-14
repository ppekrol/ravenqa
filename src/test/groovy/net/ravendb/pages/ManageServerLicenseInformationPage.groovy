package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerLicenseInformationPage extends Page {

    static at = {
        $("div[data-view='views/manage/licenseInformation']")
    }

    static content = {
        menu { module ManageServerMenu }
    }
}
