package com.infuq.security.server;


import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;



public class ContextSSLFactory {

    private static final SSLContext SSLContextServer;
    private static final SSLContext SSLContextClient;
    static {
        SSLContext sslContextServer = null;
        SSLContext sslContextClient = null;
        try {
            sslContextServer = SSLContext.getInstance("SSLv3");
            sslContextClient = SSLContext.getInstance("SSLv3");
        } catch(NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        try {
            if(getKeyManagersServer() != null && getTrustManagersServer() != null) {
                sslContextServer.init(getKeyManagersServer(), getTrustManagersServer(), null);
            }
            if(getKeyManagersClient() != null && getTrustManagersClient() != null) {
                sslContextClient.init(getKeyManagersClient(), getTrustManagersClient(), null);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        sslContextServer.createSSLEngine().getSupportedCipherSuites();
        sslContextClient.createSSLEngine().getSupportedCipherSuites();

        SSLContextServer = sslContextServer;
        SSLContextClient = sslContextClient;
    }

    public ContextSSLFactory() {}

    public static SSLContext selectSslContextServer() {
        return SSLContextServer;
    }
    public static SSLContext selectSslContextClient() {
        return SSLContextClient;
    }

    private static TrustManager[] getTrustManagersServer() {
        FileInputStream is = null;
        KeyStore ks;
        TrustManagerFactory keyFac;
        TrustManager[] kms = null;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            keyFac = TrustManagerFactory.getInstance("SunX509");
            File file = new File("/home/v-infuq/tmp/ssl/foo.jks");
            is = new FileInputStream(file);
            ks = KeyStore.getInstance("JKS");
            String keyStorePass = "pAw#d3";
            ks.load(is, keyStorePass.toCharArray());
            keyFac.init(ks);
            kms = keyFac.getTrustManagers();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return kms;
    }


    private static TrustManager[] getTrustManagersClient() {
        FileInputStream is = null;
        KeyStore ks;
        TrustManagerFactory keyFac;
        TrustManager[] kms = null;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            keyFac = TrustManagerFactory.getInstance("SunX509");
            File file = new File("/home/v-infuq/tmp/ssl/bar.jks");
            is = new FileInputStream(file);
            ks = KeyStore.getInstance("JKS");
            String keyStorePass = "pAw#d3";
            ks.load(is, keyStorePass.toCharArray());
            keyFac.init(ks);
            kms = keyFac.getTrustManagers();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return kms;
    }

    private static KeyManager[] getKeyManagersServer() {
        FileInputStream is = null;
        KeyStore ks;
        KeyManagerFactory keyFac;
        KeyManager[] kms = null;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            keyFac = KeyManagerFactory.getInstance("SunX509");
            File file = new File("/home/v-infuq/tmp/ssl/foo.jks");
            is = new FileInputStream(file);
            ks = KeyStore.getInstance("JKS");
            String keyStorePass = "pAw#d3";
            ks.load(is, keyStorePass.toCharArray());
            keyFac.init(ks, keyStorePass.toCharArray());
            kms = keyFac.getKeyManagers();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return kms;
    }


    private static KeyManager[] getKeyManagersClient() {
        FileInputStream is = null;
        KeyStore ks;
        KeyManagerFactory keyFac;
        KeyManager[] kms = null;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            keyFac = KeyManagerFactory.getInstance("SunX509");
            File file = new File("/home/v-infuq/tmp/ssl/bar.jks");
            is = new FileInputStream(file);
            ks = KeyStore.getInstance("JKS");
            String keyStorePass = "pAw#d3";
            ks.load(is, keyStorePass.toCharArray());
            keyFac.init(ks, keyStorePass.toCharArray());
            kms = keyFac.getKeyManagers();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return kms;
    }


}