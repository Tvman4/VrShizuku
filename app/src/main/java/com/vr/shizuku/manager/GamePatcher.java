package com.vr.shizuku.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class GamePatcher {

    public static boolean patchGameWithDll(File sourceDll, File targetDirectory) {
        try {
            if (!targetDirectory.exists()) {
                boolean created = targetDirectory.mkdirs();
                if (!created) {
                    return false;
                }
            }

            File destinationFile = new File(targetDirectory, sourceDll.getName());
            Files.copy(sourceDll.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
