package net.ravendb.pages

import geb.Page

import java.nio.file.Path
import java.nio.file.Paths

import net.ravendb.modules.TopNavigationBar


class FileSystemPage extends Page {

    static at = {
        newFolderButton
    }

    static content = {
        topNavigation { module TopNavigationBar }

        newFolderButton { $("button[title='Create folder']") }
        uploadInput { $("input#upload") }
        removeButton { $("button[title='Remove folder']") }

        folderNameInput { $("input#filesystemName") }
        createButton { $("button", text:"Create") }

        folders { $("a.dynatree-title") }

        uploadQueueRow { $("div#uploadQueue table tr") }
    }

    def createDirectory(String name) {
        newFolderButton.click()
        waitFor { folderNameInput.displayed }

        folderNameInput = name
        createButton.click()

        waitFor {
            def found = false
            folders.each {
                if(it.text().contains(name)) {
                    found = true
                }
            }
            found
        }
    }

    def clickFolder(String name) {
        folders.each {
            if(it.text().contains(name)) {
                it.click()
            }
        }
    }

    def uploadFile(File f, String folder) {
        clickFolder(folder)

        uploadInput << f.getAbsolutePath()

        waitFor {
            def found = false
            uploadQueueRow.each {
                if(
                    it.$("td")[0].text().contains(folder + "/" + f.getName()) &&
                    it.$("td")[1].text().contains("Uploaded")
                    ) {
                    found = true
                }
            }
            found
        }
    }
}
