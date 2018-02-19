package business.library;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileFilter;
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

        ArrayList<File> allFiles = new ArrayList<File>();

        allFiles.addAll(Arrays.asList(files));

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

        ArrayList<File> allFiles = new ArrayList<File>();

        allFiles.addAll(Arrays.asList(files));

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

    public static void GetMetadata(File file) {
        System.out.println("Getting metadata");
    }
}
