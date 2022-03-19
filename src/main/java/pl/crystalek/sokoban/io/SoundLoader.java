package pl.crystalek.sokoban.io;

import pl.crystalek.sokoban.io.file.FileManager;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

final class SoundLoader {

    Map<String, Clip> getSoundList(final FileManager fileManager) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        final Map<String, Clip> soundList = new HashMap<>();

        for (final Map.Entry<String, InputStream> entry : fileManager.getSoundFileList().entrySet()) {
            final InputStream inputStream = entry.getValue();
            final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));

            final Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            soundList.put(entry.getKey(), clip);

            audioInputStream.close();
            inputStream.close();
        }

        return soundList;
    }
}
