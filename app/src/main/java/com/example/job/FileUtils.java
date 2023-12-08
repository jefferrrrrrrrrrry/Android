package com.example.job;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    // 方法：将assets目录下的文件复制到内部存储
    public static void copyAssetToInternalStorage(Context context, String assetFileName, String destinationFileName) throws IOException {
        InputStream is = context.getAssets().open(assetFileName);
        FileOutputStream fos = context.openFileOutput(destinationFileName, Context.MODE_PRIVATE);
        copyFile(is, fos);
        is.close();
        fos.close();
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    // 方法：将HashMap写入到指定的内部存储文件中
    public static void writeHashMapToFile(Context context, String fileName, HashMap<String, String> hashMap) {
        try {
            JSONObject jsonObject = new JSONObject();
            for (HashMap.Entry<String, String> entry : hashMap.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
            String jsonString = jsonObject.toString();

            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            outputStreamWriter.write(jsonString);
            outputStreamWriter.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 方法：从指定的内部存储文件中读取数据到HashMap
    public static HashMap<String, String> readHashMapFromFile(Context context, String fileName) {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray keys = jsonObject.names();
            for (int i = 0; i < keys.length(); ++i) {
                String key = keys.getString(i);
                String value = jsonObject.getString(key);
                hashMap.put(key, value);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}