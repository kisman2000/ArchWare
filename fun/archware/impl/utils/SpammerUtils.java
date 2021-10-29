package fun.archware.impl.utils;

import fun.archware.impl.managers.FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SpammerUtils {
    private static List<String> words;

    public void init() throws IOException {
        if(!new File(FileManager.HOME.getAbsolutePath() + "\\spammer.arch").exists()){
            new File(FileManager.HOME.getAbsolutePath() + "\\spammer.arch").createNewFile();
            return;
        }
        words = new ArrayList<>(Files.readAllLines(Paths.get(new File(FileManager.HOME.getAbsolutePath() + "\\spammer.arch").toURI())));
    }

    public static String getMessage(){
        String message = ListUtil.getRandomItemInArrayList(words);
        if(message.contains("$time")){
            message = message.replaceAll("\\$time", new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
        }if(message.contains("$random")){
            StringBuilder stringBuilder = new StringBuilder();
            final char[] var = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            for(int i = 0; i < (int)(Math.random()*4); i++){
                stringBuilder.append(var[(int)(Math.random()*var.length)]);
            }
            message = message.replaceAll("\\$random", stringBuilder.toString());
        }

        return message;
    }
}
