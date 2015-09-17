package net.ravendb.pages

import geb.Page


class ConfigurationsPage extends Page {

    static at = {
        configurationsBreadcrumb.displayed
    }

    static content = {
        // modules
        topNavigation(required:false) { module TopNavigationBar }

        // content
        configurationsBreadcrumb { $("ul.breadcrumb span", text:"Configurations") }

        encryptionConfigurationLink(required:false) { $("a", text:"Raven/Encryption/Verification") }
    }
}
