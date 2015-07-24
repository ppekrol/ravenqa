package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerLicenseInformationPage extends Page {

    static at = {
        $("div[data-view='views/manage/licenseInformation']")
    }

    static content = {
        menu { module ManageServerMenu }
    }
}
