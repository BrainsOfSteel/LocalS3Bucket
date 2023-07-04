package com.local.controller;

import com.local.common.OffsetObj;
import com.local.file.server.FileOffsetFetcher;
import com.local.request.FileOffsetRequest;
import com.local.response.LatestFileNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class FileController {
    @Autowired
    private FileOffsetFetcher fileOffsetFetcher;

    @GetMapping("/getDataFromFileOffsets")
    @ResponseBody
    public char[] getDataFromFileOffset(@RequestBody FileOffsetRequest request){
        try {
            return fileOffsetFetcher.getFileContent(request.getStartOffset(), request.getEndOffset(), request.getFileName());
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/getFileName")
    @ResponseBody
    public LatestFileNameResponse getFileName(){
        String fileName = fileOffsetFetcher.getFileName();
        LatestFileNameResponse latestFileNameResponse = new LatestFileNameResponse();
        latestFileNameResponse.setLatestFileName(fileName);
        return latestFileNameResponse;
    }

    @GetMapping("/getIndex")
    @ResponseBody
    public Map<String, OffsetObj> getIndex(@RequestParam("fileName") String fileName){
        return fileOffsetFetcher.getIndex(fileName);
    }
}
