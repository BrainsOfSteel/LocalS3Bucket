package com.local.file.server;

import com.local.common.OffsetObj;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileOffsetFetcher {

    public char[] getFileContent(int startOffset, int endOffset, String fileName) throws Exception{
        char[] content = new char[endOffset-startOffset+1];
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            bufferedReader.skip(startOffset);
            int retVal = bufferedReader.read(content,0, endOffset-startOffset+1);
            if(retVal < 0){
                throw new Exception("Nothing read");
            }
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getFileName(){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Util.META_FILE))){
            return bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, OffsetObj> getIndex(String fileName){
        Map<String, OffsetObj> index = new HashMap<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            boolean flag = false;
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                if(flag){
                    String [] content = line.split(",");
                    OffsetObj obj = new OffsetObj();
                    obj.setStartOffset(Long.parseLong(content[1]));
                    obj.setEndOffset(Long.parseLong(content[2]));
                    index.put(content[0], obj);
                }
                else{
                    if(Util.INDEX_MARKUP.equalsIgnoreCase(line)){
                        flag = true;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return index;
    }
}
