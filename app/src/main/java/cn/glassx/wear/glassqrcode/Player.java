package cn.glassx.wear.glassqrcode;


/**
 * Created by Fanz on 5/13/15.
 */
interface Player {
  void play();
  void playUrl(String mediaURL);
  void pause();
  void stop();
  boolean isPlaying();
  int getCurrentPosition();
  int getDuration();
  void seekTo(int progress);
}
