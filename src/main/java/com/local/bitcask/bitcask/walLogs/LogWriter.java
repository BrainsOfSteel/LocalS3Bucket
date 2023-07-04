package com.local.bitcask.bitcask.walLogs;

import java.io.FileWriter;
import java.util.zip.CRC32;

public class LogWriter {
    public static void main(String[] args) {
        try(FileWriter fileWriter = new FileWriter("1.txt")){
            CRC32 crc32 = new CRC32();
            for(int i=0;i<100;i++){
                String line = "line "+i;
                crc32.update(line.getBytes());
                long checksum = crc32.getValue();
                crc32.reset();
                fileWriter.write(checksum+"|"+line);
                fileWriter.write("\n");
                fileWriter.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
