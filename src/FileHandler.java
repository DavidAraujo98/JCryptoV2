//package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JFileChooser;

abstract class FileHandler {

    public static Path getPath(boolean x) throws IOException {
        JFileChooser fch = new JFileChooser(new File("").getAbsolutePath());

        if (x) {
            fch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            fch.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }

        int r = fch.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION)
            return fch.getSelectedFile().toPath();

        return null;
    }
}
