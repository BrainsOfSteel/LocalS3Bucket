package com.local.rollingLog;

import com.local.file.server.DumpTestData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RollingLog {
    private String fileName;
    private final String directoryName = "rollingLogs";
    private final long MAX_BYTES = 10 * 1024;//10 KBs
    private final String LOG_FILE_PREFIX = "log_";
    private BufferedWriter bufferedFileWriter;
    private File file;

    public void log(String line) throws IOException {
        try {
            if (fileName == null) {
                fileName = LOG_FILE_PREFIX + System.currentTimeMillis();
                file = new File(directoryName, fileName);
                bufferedFileWriter = new BufferedWriter(new FileWriter(file, true));
            }

            if(file.length() + line.length() +1 > MAX_BYTES){
                bufferedFileWriter.close();
                fileName = LOG_FILE_PREFIX + System.currentTimeMillis();
                file = new File(directoryName, fileName);
                bufferedFileWriter = new BufferedWriter(new FileWriter(file));

            }
            bufferedFileWriter.write(line);
            bufferedFileWriter.write("\n");
            bufferedFileWriter.flush();

        }catch (Exception e){
            e.printStackTrace();
            bufferedFileWriter.close();
        }
    }

    public static void main(String[] args) throws IOException {
        RollingLog rollingLog = new RollingLog();
        for(int i=0;i<1000;i++){
            rollingLog.log(DumpTestData.generateRandomString(100));
        }
    }


}
