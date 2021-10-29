package fun.archware.impl.managers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class MacroManager {
    private List<String> macro = new ArrayList<>();
    public MacroManager() throws IOException {
        if(!new File(FileManager.HOME.getAbsolutePath() + "\\macro.arch").exists()) {
            try {
                new File(FileManager.HOME.getAbsolutePath() + "\\macro.arch").createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        macro.addAll(Files.readAllLines(Paths.get(new File(FileManager.HOME.getAbsolutePath() + "\\macro.arch").toURI())));
    }

    public List<String> getMacroByKey(int key){
        List<String> out = new ArrayList<>();
        for(String macro : macro){
            if(macro.split(":")[0].equals(String.valueOf(key))){
                out.add(macro.split(":")[1]);
            }
        }

        return out;
    }

    public void addMacro(int key, String macro){
        this.macro.add(key + ":" + macro);
    }

    public void removeMacro(int key){
        for(int i = 0; i < macro.size(); i++){
            if(macro.get(i).split(":")[0].equals(String.valueOf(key))) macro.remove(i);
        }
    }

    public void update(){
        StringBuilder content = new StringBuilder();
        macro.forEach(macro -> content.append(macro).append("\n"));
        try {
            Files.write(Paths.get(new File(FileManager.HOME.getAbsolutePath() + "\\macro.arch").toURI()), content.toString().getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
