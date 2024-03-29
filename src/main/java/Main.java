import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    public static void main(String[] args) {
        for (;;) {
            scanner = new Scanner(System.in);
            Path source = takePath("Введите путь для копирования:");
            Path destination = takePath("Введите путь для вставки:");
            try {
                FileUtil.copyFolder(source, destination);
            }
            catch(Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private static Path takePath(String message){
        for(;;) {
            try {
                System.out.println(message);
                String pathToCopy = scanner.nextLine().trim();
                Path source = Paths.get(pathToCopy);
                if (Files.notExists(source)) {
                    throw new FileNotFoundException("Путь не найден: " + source);
                }
                return source;
            } catch(FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
}
