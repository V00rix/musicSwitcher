package business.library;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParsingReader;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.gagravarr.tika.FlacParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;


import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Library {
    public String root;
    private File rootFolder;

    public Library(String root) {
        this.root = root;
        this.rootFolder = new File(root);
    }

    public static File[] files = null;

    /* List Files */

    /**
     * List all files in the directory
     *
     * @return List file names
     */
    public File[] ListFiles(String root) {
        root = (root != null ? root : this.root);

        File[] files = new File(root).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return !file.isDirectory();
            }
        });
        return files;
    }

    /**
     * List all files in the directory, filtering by types
     *
     * @return Filtered list of files
     */
    public File[] ListFiles(String root, final String... fileTypes) {
        root = (root != null ? root : this.root);

        File[] files = new File(root).listFiles(new FileFilter() {
            private final FileNameExtensionFilter filter =
                    new FileNameExtensionFilter("Audio Files",
                            fileTypes);

            public boolean accept(File file) {
                return filter.accept(file) && !file.isDirectory();
            }
        });

        return files;
    }

    /* List Subdirectories */

    /**
     * List all subdirectories in the directory
     *
     * @return List file names
     */
    public File[] ListSubdirectories(String root) {
        root = (root != null ? root : this.root);

        File[] files = new File(root).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        return files;
    }

    /* List Files Recursively */

    /**
     * List all subdirectories in the directory
     *
     * @return List file names
     */
    public File[] ListFilesRecursively(String root) {
        root = (root != null ? root : this.root);

        File[] files = this.ListFiles(root);

        File[] dirs = this.ListSubdirectories(root);

        ArrayList<File> allFiles = new ArrayList<File>(Arrays.asList(files));

        IterateSubdirectories(dirs, allFiles);

        return allFiles.toArray(new File[0]);
    }

    /**
     * List all subdirectories in the directory
     *
     * @return List file names
     */
    public File[] ListFilesRecursively(String root, final String... fileTypes) {
        root = (root != null ? root : this.root);

        File[] files = this.ListFiles(root, fileTypes);

        File[] dirs = this.ListSubdirectories(root);

        ArrayList<File> allFiles = new ArrayList<File>(Arrays.asList(files));

        IterateSubdirectories(dirs, allFiles, fileTypes);

        return allFiles.toArray(new File[0]);
    }

    /* Helpers */

    /**
     * Iterate through subdirectories
     * @param dirs
     * @param allFiles
     */
    private void IterateSubdirectories(File[] dirs, ArrayList<File> allFiles, final String... fileTypes) {
        for (File dir : dirs) {
            File[] fls = ListFilesRecursively(dir.getAbsolutePath(), fileTypes);
            for (File file: fls) {
                System.out.println(file);
            }
            allFiles.addAll(Arrays.asList(fls));
        }
    }

    /**
     * Extract file names from files
     *
     * @param files List of files
     * @return List of file names
     */
    public static ArrayList<String> GetNames(File[] files) {
        ArrayList<String> fileNames = new ArrayList<String>();
        for (File file : files) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }

    /**
     * @param file
     */
    public static void GetMetadata(File file) throws IOException {
        System.out.println("Getting metadata for " + file.getName());

        try {

            InputStream stream = new FileInputStream(file); // the document to be parsed
            ContentHandler contentHandler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseContext = new ParseContext();
            parser.parse(stream, contentHandler, metadata, parseContext);
            stream.close();

            System.out.println("Title: " + metadata.get("title"));
            System.out.println("Artist: " + metadata.get("xmpDM:artist"));
            System.out.println("Composer: " + metadata.get("xmpDM:composer"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

//        System.in.read();
    }
}
