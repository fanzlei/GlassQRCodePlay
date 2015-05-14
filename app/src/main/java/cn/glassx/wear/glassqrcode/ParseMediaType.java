package cn.glassx.wear.glassqrcode;


import android.util.Log;

/**
 * Created by Fanz on 5/13/15.
 */
public class ParseMediaType {

    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_PICTURE = 3;
    public static final int TYPE_NONE = 4;

    private static final String[] musicEnding = new String[]{
            "mp3",
            "amr",
            "aeo"
    };
    private static final String[] videoEnding = new String[]{
            "mp4",
            "avi",
            "3gp",
            "flv",
            "rmvb"
    };
    private static final String[] pictureEnding = new String[]{
            "jpg",
            "jpeg",
            "png"
    };
    public static int parse(String mediaURL){
        String endString = mediaURL.substring(mediaURL.lastIndexOf(".")+1,mediaURL.length());
        for(String str : musicEnding){
            if(endString.equals(str)){
                return TYPE_MUSIC;
            }
        }
        for(String str : videoEnding){
            if(endString.equals(str)){
                return TYPE_VIDEO;
            }
        }
        for(String str : pictureEnding){
            if(endString.equals(str)){
                return TYPE_PICTURE;
            }
        }
        return TYPE_NONE;
    }
}
