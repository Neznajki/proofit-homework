package helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    public List<File> getAllDirectoryFiles(File folderFile, boolean recursive)
    {
        List<File> result = new ArrayList<>();

        for (File file :  folderFile.listFiles()) {
            if (file.isDirectory()) {
                if (recursive) {
                    result.addAll(getAllDirectoryFiles(file, true));
                }
            } else if (file.getName().matches(".*\\.json")) {
                result.add(file);
            }
        }

        return result;
    }
}
