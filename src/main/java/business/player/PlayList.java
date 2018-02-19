package business.player;

import business.library.Library;

import java.io.File;

/**
 *
 */
public abstract class PlayList {
    private static File[] files;
    private static File currentFile;
    private static int currentFileIndex;
    private static boolean playing = false;

    /**
     * Set up new list for playing
     * @param fileList Files for play queue
     * @throws Exception Some exception???
     */
    public static void SetList(File[] fileList) throws Exception {
        if (fileList == null || fileList.length < 1) {
            throw new Exception("File list was not initialized or is empty!");
        }
        files = fileList;

        currentFileIndex = 0;
        currentFile = files[currentFileIndex];

        Player.SetFile(currentFile);
    }

    /**
     * Play or pause an audio file
     */
    public static void TogglePlay() {
        if (playing) {
            Player.Pause();
            playing = false;
        } else {
            Player.Play();
            playing = true;
        }
    }

    /**
     * Play next file in the queue
     */
    public static void PlayNext() {
        Player.Stop();
        currentFileIndex = currentFileIndex + 1 < files.length ? currentFileIndex + 1 : 0;
        currentFile = files[currentFileIndex];
        Player.SetFile(currentFile);
        playing = true;
        Player.Play();
    }

    /**
     * Play previous file in the queue
     */
    public static void PlayPrevious() {
        Player.Stop();
        currentFileIndex = currentFileIndex - 1 >= 0 ? currentFileIndex - 1 : files.length - 1;
        currentFile = files[currentFileIndex];
        Player.SetFile(currentFile);
        playing = true;
        Player.Play();
    }
}
