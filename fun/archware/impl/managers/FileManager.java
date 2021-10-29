package fun.archware.impl.managers;

import fun.archware.ArchWare;
import fun.archware.base.altmanager.Alt;
import fun.archware.base.module.Module;
import fun.archware.base.setting.Setting;
import net.minecraft.client.Minecraft;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileManager {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static final File HOME = new File(mc.mcDataDir.getAbsolutePath() + "\\" + ArchWare.CLIENT_NAME);

    public static void init() throws IOException {
        if(!HOME.exists()){
            HOME.mkdirs();
            new File(HOME.getAbsolutePath() + "\\configs").mkdir();
        }if(new File(HOME.getAbsolutePath() + "\\configs\\config.arch").exists()){
            initConfig("config.arch");
        }else{
            new File(HOME.getAbsolutePath() + "\\configs\\config.arch").createNewFile();
            update("config.arch");
        }
    }

    public static boolean initConfig(final String CONFIG_NAME) throws IOException {
        if(!new File(HOME.getAbsolutePath() + "\\configs\\" + CONFIG_NAME).exists()){
            return false;
        }

        final JSONObject config = new JSONObject(new JSONTokener(new FileInputStream(HOME.getAbsolutePath() + "\\configs\\" + CONFIG_NAME)));
        for(final Module module : ArchWare.moduleManager.modules){
            try {
                final JSONObject jsonObject = config.getJSONObject(module.getName());
                if(jsonObject.has("isToggled")){
                    if(jsonObject.getBoolean("isToggled") && !module.isToggled()) module.toggle();
                    else if(!jsonObject.getBoolean("isToggled") && module.isToggled()) module.toggle();
                }
                if(jsonObject.has("key")) module.setKey(jsonObject.getInt("key"));
                for(final Setting setting : ArchWare.settingManager.getSettingsByMod(module)){
                    if(jsonObject.has(setting.getName())){
                        switch(setting.getSettingType()){
                            case Boolean:
                                setting.setValueBoolean(jsonObject.getBoolean(setting.getName()));
                                break;
                            case String:
                                setting.setValueString(jsonObject.getString(setting.getName()));
                                break;
                            case Numeric:
                                setting.setValueNumeric(jsonObject.getFloat(setting.getName()));
                                break;
                            case MultiString:
                                setting.getSelectedValues().clear();
                                setting.getSelectedValues().addAll(jsonObject.getJSONArray(setting.getName()).toList().stream().map(Objects::toString).collect(Collectors.toList()));
                                break;
                        }
                    }
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return true;
    }


    public static void update(String FILE) throws IOException {
        if(!new File(HOME.getAbsolutePath() + "\\configs\\config.arch").exists()) new File(HOME.getAbsolutePath() + "\\configs\\config.arch").createNewFile();
        final JSONObject config = new JSONObject();
        config.put("version", ArchWare.VERSION);
        for(final Module module : ArchWare.moduleManager.modules){
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", module.getKey());
            jsonObject.put("isToggled", module.isToggled());
            for(final Setting setting : ArchWare.settingManager.getSettingsByMod(module)){
                switch(setting.getSettingType()){
                    case Boolean:
                        jsonObject.put(setting.getName(), setting.getValueBoolean());
                        break;
                    case String:
                        jsonObject.put(setting.getName(), setting.getValueString());
                        break;
                    case Numeric:
                        jsonObject.put(setting.getName(), setting.getValueNumeric());
                        break;
                    case MultiString:
                        jsonObject.put(setting.getName(), setting.getSelectedValues().toArray(new String[0]));
                        break;
                }
            }
            config.put(module.getName(), jsonObject);
        }
        if(!new File(HOME.getAbsolutePath() + "\\configs\\" + FILE).exists()) new File(HOME.getAbsolutePath() + "\\configs\\config.arch").createNewFile();
        Files.write(Paths.get(new File(HOME.getAbsolutePath() + "\\configs\\" + FILE).toURI()), config.toString().getBytes(), StandardOpenOption.WRITE);
    }
    public static void initAlts(){
        if(!new File(HOME.getAbsolutePath() + "\\alts.arch").exists()){
            try {
                new File(HOME.getAbsolutePath() + "\\alts.arch").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            Files.readAllLines(Paths.get(new File(HOME.getAbsolutePath() + "\\alts.arch").toURI())).stream().forEach(alt -> ArchWare.alts.add(new Alt(alt.split(":")[0], alt.split(":").length == 1 ? "" : alt.split(":")[1])));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAlts(){
        final StringBuilder alts = new StringBuilder();
        ArchWare.alts.stream().forEach(alt -> alts.append(alt.getName()).append(":").append(alt.getPassword()).append("\n"));
        try {
            Files.write(Paths.get(new File(HOME.getAbsolutePath() + "\\alts.arch").toURI()), alts.toString().getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
