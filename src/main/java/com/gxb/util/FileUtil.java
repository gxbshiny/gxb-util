package com.gxb.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.*;
import java.util.*;

/**
 * @Author: gxb
 * @Date: 2019/11/16 11:33
 * @Description:
 */
public final class FileUtil {

    private FileUtil() {

    }

    public static boolean rename(File file, String name) {
        return file.renameTo(new File(file.getParent(), name));
    }


    /**
     * 获取目标文件夹下的所有文件
     *
     * @param folder 目标文件夹
     * @return
     */
    public static File[] getFileList(String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            return new File[0];
        }
        if (!file.isDirectory()) {
            return new File[0];
        }
        return file.listFiles();
    }

    /**
     * 获取指定路径的指定文件 不存在则创建
     *
     * @param path     文件路径
     * @param fileName 文件名
     * @return
     */
    public static File creatFile(String path, String fileName) throws IOException {
        File folder = new File(path);
        if (!folder.exists()) folder.mkdirs();
        File file = new File(folder, fileName);
        if (!file.exists()) file.createNewFile();
        return file;
    }

    /**
     * 获取当前目录的指定文件 不存在则创建
     *
     * @param fileName 文件名
     * @return
     */
    public static File creatFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) file.createNewFile();
        return file;
    }

    /**
     * 把集合写入文件
     *
     * @param collection 集合
     * @param file       文件
     * @throws IOException
     */
    public static void collectionToFile(Collection<String> collection, File file) throws IOException {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Const.UTF_8));
            Iterator<String> its = collection.iterator();
            while (its.hasNext()) {
                bw.write(its.next());
                bw.newLine();
            }
            bw.flush();
        } finally {
            if (bw != null) bw.close();
        }
    }

    /**
     * 把文件读取到集合
     *
     * @param file       文件
     * @param collection 集合
     * @throws IOException
     */
    public static void fileToCollection(File file, Collection<String> collection) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Const.UTF_8));
            String temp;
            while ((temp = br.readLine()) != null) {
                collection.add(temp);
            }
        } finally {
            if (br != null) br.close();
        }
    }

    /**
     * 把map写入文件
     *
     * @param map   map
     * @param file  文件
     * @param split 分隔符
     * @throws IOException
     */
    public static void mapToFile(Map<String, String> map, File file, String split) throws IOException {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Const.UTF_8));
            Iterator<Map.Entry<String, String>> its = map.entrySet().iterator();
            while (its.hasNext()) {
                Map.Entry<String, String> entry = its.next();
                bw.write(entry.getKey() + split + entry.getValue());
                bw.newLine();
            }
            bw.flush();
        } finally {
            if (bw != null) bw.close();
        }
    }

    /**
     * 把文件读取到map
     *
     * @param file  目标文件
     * @param map   用于保存的map
     * @param split 字段的分隔符   key=value --> map.put(key,value);
     * @throws IOException
     */
    public static void fileToMap(File file, Map<String, String> map, String split) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String temp;
            while ((temp = br.readLine()) != null) {
                String[] kv = temp.split(split, 2);
                if (kv.length != 2) {
                    continue;
                }
                map.put(kv[0], kv[1]);
            }
        } finally {
            if (br != null) br.close();
        }
    }

    /**
     * 通过url把文件下载到内存
     *
     * @param client 用于http请求的http客户端
     * @param url    文件的url地址
     * @param len    byte[]的最大长度
     * @return byte[]类型的list
     * @throws IOException
     */
    public static List<byte[]> downFile(HttpClient client, String url, int len) throws IOException {
        List<byte[]> list = new LinkedList<>();
        BufferedInputStream bis = null;
        HttpResponse response;
        try {
            HttpGet get = new HttpGet(url);
            response = client.execute(get);
            bis = new BufferedInputStream(response.getEntity().getContent());
            int l;
            byte[] temp = new byte[len];
            while ((l = bis.read(temp)) > 0) {
                if (l == len) {
                    list.add(temp);
                } else {
                    byte[] b = new byte[l];
                    System.arraycopy(temp, 0, b, 0, l);
                    list.add(b);
                }
                temp = new byte[len];
            }
        } finally {
            if (bis != null) bis.close();
        }
        return list;
    }

    /**
     * 读取文件到内存中
     *
     * @param file 目标文件
     * @param l    byte[]的最大长度
     * @return byte[]类型的list List<byte[]>
     * @throws IOException
     */
    public static List<byte[]> readFile(File file, int l) throws IOException {
        List<byte[]> result = new ArrayList<>();
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            byte[] temp = new byte[l];
            int len;
            while ((len = bis.read(temp)) > 0) {
                result.add(Arrays.copyOfRange(temp, 0, len));
                temp = new byte[l];
            }
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
        return result;
    }

    /**
     * 把字节码保存到文件
     *
     * @param list     byte[]类型的list
     * @param fileName 文件名
     * @throws IOException
     */
    public static void bytesToFile(List<byte[]> list, String fileName) throws IOException {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(fileName));
            for (byte[] bytes : list) {
                bos.write(bytes);
            }
        } finally {
            if (bos != null) {
                bos.close();
            }
        }
    }

    /**
     * 检查两个文件夹下是否有同名文件
     *
     * @param path1 文件夹1的路径
     * @param path2 文件夹2的路径
     * @return 同名文件的数组
     */
    public static String[] checkSameName(String path1, String path2) {
        File[] files1 = getFileList(path1);
        File[] files2 = getFileList(path2);
        List<String> result = new ArrayList<>(Math.min(files1.length, files2.length));
        Set<String> names1 = new HashSet<>(files1.length);
        for (File file : files1) names1.add(file.getName());
        for (File file : files2) {
            if (names1.contains(file.getName())) result.add(file.getName());
        }
        String[] arr = new String[result.size()];
        for (int i = 0; i < result.size(); i++) arr[i] = result.get(i);
        return arr;
    }

}
