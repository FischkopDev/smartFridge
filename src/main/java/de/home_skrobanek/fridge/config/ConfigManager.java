package de.home_skrobanek.fridge.config;

import java.io.*;

/**
 * This class handles the communication with the
 * needed config files.
 */
public class ConfigManager {

    private File config = new File("config/settings.txt");

    private FileWriter fw;
    private BufferedWriter bw;

    //create the file and write the standard values
    public ConfigManager(){
        try{
            if(!config.exists()) {
                config.createNewFile();

                fw = new FileWriter(config);
                bw = new BufferedWriter(fw);

                bw.write("host=localhost");
                bw.newLine();
                bw.write("port=8001");

                bw.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //reading from the config file
    public String readFromConfig(String path){
        try{
            FileReader fr = new FileReader(config);
            BufferedReader br = new BufferedReader(fr);

            String line ="";

            while((line = br.readLine()) != null){
                if(line.contains(path)){
                    br.close();
                    return line.split("=")[1];
                }
            }

            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
