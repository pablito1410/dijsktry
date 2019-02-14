package pl.pk.isk.dijkstry.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CssLoader {

    public static String loadCss() {
        return "node {\n" +
                "    fill-color: black, gray;\n" +
                "    fill-mode: gradient-radial;\n" +
                "    text-color: white;\n" +
                "    size: 20px;\n" +
                "}\n" +
                "node.marked {\n" +
                "    size: 30px;\n" +
                "    fill-color: red, pink;\n" +
                "    fill-mode: gradient-diagonal2;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "edge.marked {\n" +
                "    fill-color: red, pink;\n" +
                "    size: 3px;\n" +
                "    fill-mode: gradient-diagonal2;\n" +
                "}\n";
//        try {
//            final StringBuilder sb = new StringBuilder();
//            Files.lines(new File("style.css").toPath())
//                    .forEach(line -> sb.append(line));
//            return sb.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("Cannot read style.css");
//        }
    }
}
