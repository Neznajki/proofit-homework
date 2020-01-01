package helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    public List<File> getAllDirectoryFiles(File folderFile)
    {
        List<File> result = new ArrayList<>();

        for (File file : folderFile.listFiles()) {
            if (
                    file.isFile() &&
                    file.getName().matches(".*\\.json")
            ) {
                result.add(file);
            }
        }

        return result;
    }
}
