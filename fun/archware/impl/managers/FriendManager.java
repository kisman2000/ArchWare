/*
 * Created with :heart: by katch.
 * (c) 4.23.2021
 */

package fun.archware.impl.managers;

import net.minecraft.entity.Entity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FriendManager {
    private List<String> friends = new ArrayList<>();

    public FriendManager() throws IOException {
        if(!new File(FileManager.HOME.getAbsolutePath() + "\\friends.arch").exists()){
            new File(FileManager.HOME.getAbsolutePath() + "\\friends.arch").createNewFile();
            return;
        }

        friends.addAll(Files.readAllLines(Paths.get(new File(FileManager.HOME.getAbsolutePath() + "\\friends.arch").toURI())));
    }

    public void addFriend(Entity entity){
        friends.add(entity.getName());
        try{
            flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void removeFriend(Entity entity){
        friends.remove(entity.getName());
        try{
            flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean isFriend(Entity entity){
        return friends.contains(entity.getName());
    }

    private void flush() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        friends.forEach(friend -> stringBuilder.append(friend).append("\n"));
        Files.write(Paths.get(new File(FileManager.HOME.getAbsolutePath() + "\\friends.arch").toURI()), stringBuilder.toString().getBytes(), StandardOpenOption.WRITE);
    }
}
