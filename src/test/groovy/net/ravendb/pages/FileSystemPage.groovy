package net.ravendb.pages

import geb.Page

import java.nio.charset.StandardCharsets

import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.TopNavigationBar


class FileSystemPage extends Page {

    public final static String FILE_REMOVED = "Files deleted"

    static at = {
        newFolderButton.displayed
        newFolderButton.displayed
        folders.displayed
    }

    static content = {
        // modules
        topNavigation { module TopNavigationBar }
        deleteFileModalDialog { module DeleteResourceModalDialog }
        alert { module AlertTextModule }

        // toolbars
        newFolderButton { $("button[title='Create folder']") }
        uploadInput { $("input#upload") }
        removeButton { $("button[title='Remove folder']") }

        deleteSelectedFileButton(required:false) { $("button[title='Delete selected documents (DEL)']") }

        // content
        folderNameInput { $("input#filesystemName") }
        createButton { $("button", text:"Create") }

        folders { $("a.dynatree-title") }
        files(required:false) { $("a[href*='#filesystems/edit']") }
        checkboxesSelector(required:false) { "img[src='content/images/unchecked.png']" }

        uploadQueueRow { $("div#uploadQueue table tr") }
        uploadQueueToggle { $("#uploadQueuePanelToggle") }
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

    def clickFile(String name) {
        def linkToClick
        files.each {
            if(it.@href.contains(URLEncoder.encode(name, StandardCharsets.UTF_8.toString()))) {
                linkToClick = it
            }
        }

        if(linkToClick) {
            linkToClick.click()
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

    def checkFile(String name) {
        def checkbox
        files.each {
            if(it.@href.contains(URLEncoder.encode(name, StandardCharsets.UTF_8.toString()))) {
                checkbox = it.parent().parent().$(checkboxesSelector)
            }
        }

        if(checkbox) {
            checkbox.click()
        }
    }
}
