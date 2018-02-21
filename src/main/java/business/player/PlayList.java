package business.player;

import models.AudioFile;

import java.util.ArrayList;

import static business.utiliy.Status.SetStatus;

/**
 *
 */
public abstract class PlayList {
    private static ArrayList<AudioFile> files;
    private static AudioFile currentFile;
    private static int currentFileIndex;
    private static boolean playing = false;

    /**
     * Set up new list for playing
     * @param fileList Files for play queue
     * @throws Exception Some exception???
     */
    public static void SetList(ArrayList<AudioFile> fileList) throws Exception {
        if (fileList == null || fileList.size() < 1) {
            throw new Exception("File list was not initialized or is empty!");
        }
        files = fileList;

        currentFileIndex = 0;
        currentFile = files.get(currentFileIndex);

        Player.SetFile(currentFile.file);

        SetStatus("Basic playlist set");
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
        currentFileIndex = currentFileIndex + 1 < files.size() ? currentFileIndex + 1 : 0;
        currentFile = files.get(currentFileIndex);
        Player.SetFile(currentFile.file);
        playing = true;
        Player.Play();
    }

    /**
     * Play previous file in the queue
     */
    public static void PlayPrevious() {
        Player.Stop();
        currentFileIndex = currentFileIndex - 1 >= 0 ? currentFileIndex - 1 : files.size() - 1;
        currentFile = files.get(currentFileIndex);
        Player.SetFile(currentFile.file);
        playing = true;
        Player.Play();
    }
}
