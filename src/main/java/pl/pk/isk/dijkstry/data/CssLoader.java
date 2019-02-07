package pl.pk.isk.dijkstry.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CssLoader {

    public static String loadCss() {
        try {
            final StringBuilder sb = new StringBuilder();
            Files.lines(new File("style.css").toPath())
                    .forEach(line -> sb.append(line));
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Cannot read style.css");
        }
    }
}
