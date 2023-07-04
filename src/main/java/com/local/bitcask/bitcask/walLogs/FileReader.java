package com.local.bitcask.bitcask.walLogs;

import java.io.BufferedReader;
import java.util.zip.CRC32;

public class FileReader {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new java.io.FileReader("1.txt"))){
            String line = null;
            CRC32 crc32 = new CRC32();
            while((line = br.readLine())!= null){
                String actualValue = line.split("\\|")[1];
                long checkSumOriginal = Long.parseLong(line.split("\\|")[0]);
                crc32.update(actualValue.getBytes());
                long checksum = crc32.getValue();
                crc32.reset();
                if(checksum != checkSumOriginal){
                    System.out.println("tampered value = "+actualValue);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
