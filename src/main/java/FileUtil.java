import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static void copyFolder(Path srcDirectory, Path dstDirectory) throws IOException {
        Path dstPath = getCopyPath(srcDirectory, dstDirectory);

        if (Files.exists(dstPath)) {
            throw new FileAlreadyExistsException(dstPath + " уже существует");
        }
        else if (Files.isRegularFile(srcDirectory)) {
            Files.copy(srcDirectory, dstPath);
        }
        else {
            Files.createDirectory(dstPath);
            Files.walk(srcDirectory).forEach(path -> {
                try {
                    if (Files.isDirectory(path) && isParentFolderEqual(path, dstPath)) {
                        copyFolder(path, dstPath);
                    } else if (Files.isRegularFile(path) && isParentFolderEqual(path, dstPath)) {
                        Files.copy(path, getCopyPath(path, dstPath));
                    }
                } catch (IOException ex) {
                    throw new RuntimeException("Не удалось скопировать директорию", ex);
                }
            });
        }
    }

    private static boolean isParentFolderEqual(Path src, Path dst) {
        Path srcFolder = src.getParent();
        Path srcFolderName = srcFolder.getFileName();

        return dst.endsWith(srcFolderName);
    }

    private static Path getCopyPath(Path source, Path destination) {
        Path sourceName = source.getFileName();
        return destination.resolve(sourceName);
    }
}
