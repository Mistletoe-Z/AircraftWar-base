package edu.hitsz.application; // 注意检查你的包名是不是这个

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicThread extends Thread {

    //音频文件名
    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;

    // 🌟 1. 新增控制开关：是否循环播放、当前是否正在播放
    private boolean isLoop;
    private boolean isPlaying = true;

    // 🌟 2. 修改构造方法：增加一个 boolean isLoop 参数
    public MusicThread(String filename, boolean isLoop) {
        this.filename = filename;
        this.isLoop = isLoop; // 记录是否需要循环
        reverseMusic();
    }

    public void reverseMusic() {
        try {
            //定义一个AudioInputStream用于接收输入的音频数据，使用AudioSystem来获取音频的音频输入流
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            //用AudioFormat来获取AudioInputStream的格式
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;
    }

    public void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        //源数据行SoureDataLine是可以写入数据的数据行
        SourceDataLine dataLine = null;
        //获取受数据行支持的音频格式DataLine.info
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
        dataLine.start();
        try {
            int numBytesRead = 0;
            // 🌟 3. 核心修改：在读取音频的 while 循环里，加上 isPlaying 的判断
            // 这样只要外部调用了 stopMusic()，这里就会立刻打断播放
            while (numBytesRead != -1 && isPlaying) {
                //从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
                numBytesRead = source.read(buffer, 0, buffer.length);
                //通过此源数据行将数据写入混频器
                if (numBytesRead != -1) {
                    dataLine.write(buffer, 0, numBytesRead);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        dataLine.drain();
        dataLine.close();
    }

    // 🌟 4. 新增：提供给外部（比如 Game.java）强制停止音乐的方法
    public void stopMusic() {
        this.isPlaying = false;
    }

    @Override
    public void run() {
        // 🌟 5. 核心修改：使用 do-while 循环来支持背景音乐的重复播放
        do {
            InputStream stream = new ByteArrayInputStream(samples);
            play(stream);
        } while (isPlaying && isLoop); // 如果还在播放状态且要求循环，就再放一遍
    }
}