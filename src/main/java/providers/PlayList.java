package providers;

//import static providers.status.api.implementation.StatusProvider.setStatus;

/**
 *
 */
public abstract class PlayList {
//    private static ArrayList<AudioFile> files;
//    private static AudioFile currentFile;
//    private static int currentFileIndex;
//    private static boolean playing = false;
//
//    /**
//     * Set up new list for playing
//     * @param fileList Files for play queue
//     * @throws Exception Exception
//     */
//    public static void SetList(ArrayList<AudioFile> fileList) throws Exception {
//        if (fileList == null || fileList.size() < 1) {
//            throw new Exception("File list was not initialized or is empty!");
//        }
//        files = fileList;
//
//        currentFileIndex = 0;
//        currentFile = files.get(currentFileIndex);
//
//        PlayerProvider.setFile(currentFile.file);
//
//        setStatus("Basic playlist set");
//    }
//
//    /**
//     * play or pause an audio file
//     */
//    public static void TogglePlay() {
//        if (playing) {
//            PlayerProvider.pause();
//            playing = false;
//        } else {
//            PlayerProvider.play();
//            playing = true;
//        }
//    }
//
//    /**
//     * play next file in the queue
//     */
//    public static void PlayNext() {
//        PlayerProvider.stop();
//        currentFileIndex = currentFileIndex + 1 < files.size() ? currentFileIndex + 1 : 0;
//        currentFile = files.get(currentFileIndex);
//        PlayerProvider.setFile(currentFile.file);
//        playing = true;
//        PlayerProvider.play();
//    }
//
//    /**
//     * play previous file in the queue
//     */
//    public static void PlayPrevious() {
//        PlayerProvider.stop();
//        currentFileIndex = currentFileIndex - 1 >= 0 ? currentFileIndex - 1 : files.size() - 1;
//        currentFile = files.get(currentFileIndex);
//        PlayerProvider.setFile(currentFile.file);
//        playing = true;
//        PlayerProvider.play();
//    }
}
