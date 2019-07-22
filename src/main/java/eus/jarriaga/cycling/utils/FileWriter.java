package eus.jarriaga.cycling.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {

    public static String save(String data, String fileLocation, String fileType) {
        FileOutputStream fop = null;
        File file;
        String fileBaseName = null;
        try {
            //5feb83e33dbf2eb5debd962ad68f9767.jpg
            fileBaseName = getNewFileName() + "." + fileType;
            String fileName = fileLocation + fileBaseName;
            file = new File(fileName);
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = data.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
                return fileBaseName;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static String getNewFileName() {
        return MD5(String.valueOf(System.nanoTime())).toUpperCase();
    }

    private static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
