package net.ravendb.modules

import geb.Module


class ManageServerMenu extends Module {

    static content = {
        apiKeysLink { $("a[href='#admin/settings/apiKeys']") }
        windowsAuthenticationLink { $("a[href='#admin/settings/windowsAuth']") }
        clusterLink { $("a[href='#admin/settings/cluster']") }
        globalConfigurationLink { $("a[href='#admin/settings/globalConfig']") }
        serverSmugglingLink { $("a[href='#admin/settings/serverSmuggling']") }
        backupLink { $("a[href='#admin/settings/backup']") }
        compactLink { $("a[href='#admin/settings/compact']") }
        restoreLink { $("a[href='#admin/settings/restore']") }
        adminLogsLink { $("a[href='#admin/settings/adminLogs']") }
        trafficWatchLink { $("a[href='#admin/settings/trafficWatch']") }
        licenseInformationLink { $("a[href='#admin/settings/licenseInformation']") }
        gatherDebugInfoLink { $("a[href='#admin/settings/debugInfo']") }
        ioTestLink { $("a[href='#admin/settings/ioTest']") }
        administratorJsConsole { $("a[href='#admin/settings/console']") }
        studioConfigLink { $("a[href='#admin/settings/studioConfig']") }
        toSystemDatabaseLink { $("a", text:"To System Database") }

        areYouSureModalDialog { module AreYouSureModalDialog }
    }
}
