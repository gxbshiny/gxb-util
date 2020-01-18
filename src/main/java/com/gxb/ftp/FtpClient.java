package com.gxb.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: gxb
 * @Time: 2019/12/2 10:43
 * @Description:
 */
public class FtpClient {
    private static final Logger log = LoggerFactory.getLogger(FtpClient.class);

    private FTPClient client;
    private String host;
    private int port;
    private String username;
    private String password;
    private String encoding;
    private int fileType;

    public FtpClient() {
        this.port = 0; // 若小于等于0则连接默认端口
        this.encoding = "UTF-8"; // 默认为UTF-8
        this.fileType = FTPClient.BINARY_FILE_TYPE; // 默认为二进制文件
        this.client = new FTPClient();
    }

    public FtpClient(String host, String username, String password) {
        this();
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public FtpClient(String host, int port, String username, String password) {
        this();
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public FtpClient(String host, int port, String username, String password, String encoding, int fileType) {
        this();
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.encoding = encoding;
        this.fileType = fileType;
    }

    public boolean login() {
        try {
            client.setControlEncoding(encoding);
            if (port <= 0) client.connect(host);
            else client.connect(host, port);
            client.login(username, password);
            client.setFileType(fileType);
            client.enterLocalPassiveMode();
            int replyCode = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                client.disconnect();
                log.error("FTP登录失败 replyCode:" + replyCode);
                return false;
            }
            client.changeWorkingDirectory("/");
        } catch (Throwable e) {
            log.error("FTP客户端登录失败:", e);
            return false;
        }
        log.error("FTP登录成功...");
        return true;
    }

    public boolean changeWorkingDirectory(String dir) throws IOException {
        return client.changeWorkingDirectory(dir);
    }

    public boolean logout() {
        try {
            client.logout();
        } catch (IOException e) {
            log.error("FTP退出登录异常..." + e.getMessage());
            return false;
        } finally {
            if (client.isConnected()) {
                try {
                    client.disconnect();
                } catch (IOException e) {
                    log.error("FTP断开连接异常...", e.getMessage());
                    return false;
                }
            }
        }
        log.error("FTP退出登录成功...");
        return true;
    }

    public String[] fileNameList(String path) {
        String[] names = null;
        try {
            names = client.listNames();
        } catch (Throwable e) {
            log.error("获取文件名列表失败", e);
        }
        return names;
    }

    public boolean downFile(String remoteFile, String localFile) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(localFile);
            client.retrieveFile(remoteFile, fos);
        } catch (Exception e) {
            log.error("下载文件异常 remoteFile:" + remoteFile + " localFile:" + localFile, e);
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error("关闭文件输出流异常", e);
                }
            }
        }
        return true;
    }

    public boolean uploadFile(File localFile, String remotePath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(localFile);
            client.storeFile(remotePath, fis);
        } catch (Exception e) {
            log.error("上传文件异常 localFile:" + localFile.getName() + " remotePath:" + remotePath, e);
            return false;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("关闭文件输入流异常", e);
                }
            }
        }
        return true;
    }

    public FTPClient getClient() {
        return client;
    }

    public void setClient(FTPClient client) {
        this.client = client;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }
}
