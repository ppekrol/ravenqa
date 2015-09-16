package net.ravendb.utils

import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

/**
 * Utils class for manipulating files.
 */
public class FilesUtils {

    /**
     * Compare two streams.
     * @param stream1
     * @param stream2
     * @return true if stream are equal, false otherwise
     * @throws IOException
     */
    public static boolean streamsEqual(FileInputStream stream1, FileInputStream stream2) throws IOException {
        byte[] buf1 = new byte[4096]
        byte[] buf2 = new byte[4096]
        boolean done1 = false
        boolean done2 = false

        try {
            while (!done1) {
                int off1 = 0
                int off2 = 0

                while (off1 < buf1.length) {
                    int count = stream1.read(buf1, off1, buf1.length - off1)
                    if (count < 0) {
                        done1 = true
                        break
                    }
                    off1 += count
                }
                while (off2 < buf2.length) {
                    int count = stream2.read(buf2, off2, buf2.length - off2)
                    if (count < 0) {
                        done2 = true
                        break
                    }
                    off2 += count
                }
                if (off1 != off2 || done1 != done2) {
                    return false
                }
                for (int i = 0; i < off1; i++) {
                    if (buf1[i] != buf2[i]) {
                        return false
                    }
                }
            }
            return true
        } finally {
            stream1.close()
            stream2.close()
        }
    }

}
