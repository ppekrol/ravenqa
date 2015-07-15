package net.ravendb.modules

import geb.Module


class TasksMenu extends Module {

    static content = {
        importDatabaseLink { $("a[href^='#databases/tasks/importDatabase']") }
        exportDatabaseLink { $("a[href^='#databases/tasks/exportDatabase']") }
        toggleIndexingLink { $("a[href^='#databases/tasks/toggleIndexing']") }
        createSampleDataLink { $("a[href^='#databases/tasks/sampleData']") }
        csvImportLink { $("a[href^='#databases/tasks/csvImport']") }
    }
}
