package providers;

//import static providers.status.api.implementation.StatusProvider.setStatus;
//import static providers.status.api.implementation.StatusProvider.trackProgress;
//import static providers.status.api.implementation.StatusProvider.progressCompleted;
//import static providers.TimeTrack.Track;

public class Library {
//    public static ArrayList<AudioFile> files = null;
//
//    private String root;
//    private File rootFolder;
//
//    private static ContentHandler contentHandler = new DefaultHandler();
//    private static Metadata metadata = new Metadata();
//    private static Parser parser = new Mp3Parser();
//    private static ParseContext parseContext = new ParseContext();
//
//    /**
//     * Constructor
//     * @param root Root for music files
//     */
//    public Library(String root) {
//        this.root = root;
//        this.rootFolder = new File(root);
//        setStatus("Library created");
//    }
//
//    /* List Files */
//
//    /**
//     * List all files in the directory
//     *
//     * @return List file names
//     */
//    public File[] ListFiles(String root) {
//        root = (root != null ? root : this.root);
//
//        File[] files = new File(root).listFiles(new FileFilter() {
//            public boolean accept(File file) {
//                return !file.isDirectory();
//            }
//        });
//        return files;
//    }
//
//    /**
//     * List all files in the directory, filtering by types
//     *
//     * @return Filtered list of files
//     */
//    public File[] ListFiles(String root, final String... fileTypes) {
//        root = (root != null ? root : this.root);
//
//        File[] files = new File(root).listFiles(new FileFilter() {
//            private final FileNameExtensionFilter filter =
//                    new FileNameExtensionFilter("Audio Files",
//                            fileTypes);
//
//            public boolean accept(File file) {
//                return filter.accept(file) && !file.isDirectory();
//            }
//        });
//
//        return files;
//    }
//
//    /* List Subdirectories */
//
//    /**
//     * List all subdirectories in the directory
//     *
//     * @return List file names
//     */
//    public File[] ListSubdirectories(String root) {
//        root = (root != null ? root : this.root);
//
//        File[] files = new File(root).listFiles(new FileFilter() {
//            public boolean accept(File file) {
//                return file.isDirectory();
//            }
//        });
//
//        return files;
//    }
//
//    /* List Files Recursively */
//
//    /**
//     * List all subdirectories in the directory
//     *
//     * @return List file names
//     */
//    public File[] ListFilesRecursively(String root) {
//        root = (root != null ? root : this.root);
//
//        File[] files = this.ListFiles(root);
//
//        File[] dirs = this.ListSubdirectories(root);
//
//        ArrayList<File> allFiles = new ArrayList<File>(Arrays.asList(files));
//
//        IterateSubdirectories(dirs, allFiles);
//
//        return allFiles.toArray(new File[0]);
//    }
//
//    /**
//     * List all subdirectories in the directory
//     *
//     * @return List file names
//     */
//    public File[] ListFilesRecursively(String root, final String... fileTypes) {
//        root = (root != null ? root : this.root);
//
//        File[] files = this.ListFiles(root, fileTypes);
//
//        File[] dirs = this.ListSubdirectories(root);
//
//        ArrayList<File> allFiles = new ArrayList<File>(Arrays.asList(files));
//
//        IterateSubdirectories(dirs, allFiles, fileTypes);
//
//        return allFiles.toArray(new File[0]);
//    }
//
//    /**
//     * Update files from library
//     *
//     * @throws Exception
//     */
//    public void UpdateFiles() throws Exception {
//        File[] recFiles = ListFilesRecursively(null, "mp3");
//
//        files = new ArrayList<AudioFile>();
//
//        for (File file : recFiles) {
//            AudioFile f = new AudioFile();
//            f.file = file;
//            f.filePath = file.getName();
//            files.add(f);
//        }
//
//        this.GetMetadata();
//    }
//
//    /* Metadata */
//
//    /**
//     * @param file
//     */
//    public void GetMetadata(AudioFile file) throws IOException {
//        setStatus("Getting metadata for " + file.filePath);
//        progressCompleted++;
//
//        try {
//
//            InputStream stream = new FileInputStream(file.file);
//            parser.parse(stream, contentHandler, metadata, parseContext);
//            stream.close();
//
//            file.title = metadata.get("title");
//            file.album = metadata.get("xmpDM:album");
//            file.artist = metadata.get("xmpDM:artist");
//            file.track = Integer.parseInt(metadata.get("xmpDM:trackNumber").split("/")[0]);
//            file.genre = metadata.get("xmpDM:genre");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Fill library files with metadata
//     */
//    public void GetMetadata() throws Exception {
//        trackProgress(() -> {
//            for (AudioFile file : files) {
//                this.GetMetadata(file);
//            }
//            return null;
//        }, files.size());
//    }
//
//    /* Cache */
//
//    /**
//     * Save Cache
//     */
//    public void SaveCache(String filePath) throws IOException {
//        File f = new File(filePath);
//        if (f.exists()) {
//            f.delete();
//        }
//        f.createNewFile();
//
//        FileOutputStream fos = new FileOutputStream(filePath);
//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        oos.writeObject(files);
//        oos.flush();
//        oos.close();
//    }
//
//
//    /**
//     * Read Cache
//     */
//    public void ReadCache(String filePath) throws IOException, ClassNotFoundException {
//        File f = new File(filePath);
//        if (!f.exists()) {
//            throw new FileNotFoundException();
//        }
//
//        FileInputStream fis = new FileInputStream(filePath);
//        ObjectInputStream ois = new ObjectInputStream(fis);
//        files = (ArrayList<AudioFile>) ois.readObject();
//        ois.close();
//
//        System.out.println(files);
//    }
//
//    /* Helpers */
//
//    /**
//     * Iterate through subdirectories
//     *
//     * @param dirs
//     * @param allFiles
//     */
//    private void IterateSubdirectories(File[] dirs, ArrayList<File> allFiles, final String... fileTypes) {
//        for (File dir : dirs) {
//            File[] fls = ListFilesRecursively(dir.getAbsolutePath(), fileTypes);
//            for (File file : fls) {
//                System.out.println(file);
//            }
//            allFiles.addAll(Arrays.asList(fls));
//        }
//    }
//
//    /**
//     * Extract file names from files
//     *
//     * @param files List of files
//     * @return List of file names
//     */
//    public static ArrayList<String> GetNames(File[] files) {
//        ArrayList<String> fileNames = new ArrayList<String>();
//        for (File file : files) {
//            fileNames.add(file.getName());
//        }
//        return fileNames;
//    }
}
