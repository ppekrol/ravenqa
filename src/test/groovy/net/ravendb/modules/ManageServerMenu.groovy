package net.ravendb.modules

import geb.Module


class ManageServerMenu extends Module {

    static content = {
        apiKeysLink { $("a", text:"API Keys") }
        windowsAuthenticationLink { $("a", text:"Windows Authentication") }
        clusterLink { $("a", text:"Cluster") }
        globalConfigurationLink { $("a", text:"Global configuration") }
        serverSmugglingLink { $("a", text:"Server smuggling") }
        backupLink { $("a", text:"Backup") }
        compactLink { $("a", text:"Compact") }
        restoreLink { $("a", text:"Restore") }
        adminLogsLink { $("a", text:"Admin Logs") }
        trafficWatchLink { $("a", text:"Traffic Watch") }
        licenseInformationLink { $("a", text:"License Information") }
        gatherDebugInfoLink { $("a", text:"Gather Debug Info") }
        ioTestLink { $("a", text:"IO Test") }
        administratorJsConsole { $("a", text:"Administator JS Console") }
        studioConfigLink { $("a", text:"Studio Config") }
        toSystemDatabaseLink { $("a", text:"To System Database") }

        areYouSureModalDialog { module AreYouSureModalDialog }
    }
}
