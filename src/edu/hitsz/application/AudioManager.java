package edu.hitsz.application;

public class AudioManager {
    private static AudioManager instance = new AudioManager();

    private MusicThread bgmThread;
    private MusicThread bossBgmThread;

    private AudioManager() {}

    public static AudioManager getInstance() {
        return instance;
    }

    // 播放普通 BGM
    public void playNormalBGM() {
        stopNormalBGM(); // 防止重复播放
        bgmThread = new MusicThread("src/videos/bgm.wav", true);
        bgmThread.start();
    }

    public void stopNormalBGM() {
        if (bgmThread != null) {
            bgmThread.stopMusic();
        }
    }

    // 播放 Boss BGM，同时自动暂停普通 BGM
    public void playBossBGM() {
        stopNormalBGM();
        if (bossBgmThread == null || !bossBgmThread.isAlive()) {
            bossBgmThread = new MusicThread("src/videos/bgm_boss.wav", true);
            bossBgmThread.start();
        }
    }

    // 停止 Boss BGM，自动恢复普通 BGM
    public void stopBossBGM() {
        if (bossBgmThread != null) {
            bossBgmThread.stopMusic();
        }
        playNormalBGM();
    }
    public void playGetSupplySound() {
        new MusicThread("src/videos/get_supply.wav", false).start();
    }
    public void gameoverSound() {
        new MusicThread("src/videos/game_over.wav", false).start();
    }
    public void bullethitSound() {
        new MusicThread("src/videos/bullet_hit.wav", false).start();
    }
    public void bombSound() {
        new MusicThread("src/videos/bomb_explosion.wav", false).start();
    }
}