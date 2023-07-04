package com.local.file.server;

import com.local.common.OffsetObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DumpTestData {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String fileName = Util.FILE_PREFIX + System.currentTimeMillis();
        try(FileWriter fw = new FileWriter(fileName)){
            String key = "key";
            Random randomLength = new Random();
            for(int i =0;i<500;i++){
                int randomNumber = randomLength.nextInt(CHARACTERS.length()*15 - 1 + 1) + 1;
                fw.write(key+i+"|"+ generateRandomString(randomNumber)+i+"\n");
            }
            fw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

        Map<String, OffsetObj> index = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line = null;
            long start=0L;
            long end = 0L;
            while((line = br.readLine())!=null){
                String[] keyValue = line.split("\\|");
                start = end;
                start += keyValue[0].length()+1;
                end = start + keyValue[1].length();
                index.put(keyValue[0], new OffsetObj(start, end));
                end++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try(FileWriter fw = new FileWriter(fileName,true)){
            fw.write(Util.INDEX_MARKUP+"\n");
            for(Map.Entry<String, OffsetObj> entry : index.entrySet()){
                fw.write(entry.getKey()+","+entry.getValue().getStartOffset()+","+entry.getValue().getEndOffset());
                fw.write("\n");
                fw.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try(FileWriter fw = new FileWriter(Util.META_FILE)){
            fw.write(fileName);
            fw.write("\n");
            fw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(index);
    }
}
