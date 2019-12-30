package staticAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    public static List<File> getAllDirectoryFiles(String directoryPath, boolean recursive)
    {
        List<File> result = new ArrayList<>();

        File folder = new File(directoryPath);
        for (File file :  folder.listFiles()) {
            if (file.isDirectory()) {
                if (recursive) {
                    result.addAll(getAllDirectoryFiles(file.getAbsolutePath(), true));
                }
            } else if (file.getName().matches(".*\\.json")) {
                result.add(file);
            }
        }

        return result;
    }
}
