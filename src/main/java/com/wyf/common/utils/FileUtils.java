package com.wyf.common.utils;

import sun.misc.BASE64Decoder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description: IO文件工具类
 * @Date: 2020/10/24 19:54
 * @Version: 1.0
 */
public class FileUtils {

    private static File file = null;

    /**
     * @return java.util.List<java.io.File>
     * @Description 获取文件路径
     * @ClassName getFiles
     * @Date 10:35 2020/3/10
     * @Param [path]
     */
    public static List<File> getFiles(String path) {
        List<File> fileList = new ArrayList<>();
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File fileIndex : files) {
                    //如果这个文件是目录，则进行递归搜索
                    if (!fileIndex.isDirectory()) {
                        //如果文件是普通文件，则将文件句柄放入集合中
                        fileList.add(fileIndex);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("文件读取路径错误：-->getFiles");
        }
        return fileList;
    }


    /**
     * 功能描述: 读取文件数据
     * @param: [path]
     * @param: [List<String>]
     * @return: java.util.List<java.awt.Image>
     * @date: 2020/10/24 20:11
     */
    public static List<Image> addFile(String path, List<String> paths) throws IOException {
        //创建一个Image类型的数组链表用于接收Image对象
        List<Image> imgList = new ArrayList<>();

        if (paths.size() == 0) {
            List<File> files = getFiles(path);
            //遍历传入的路径
            for (File filepath : files) {
                Image image = Toolkit.getDefaultToolkit().getImage(path + "/" + filepath.getName());
                imgList.add(image);    //将Image对象添加到列表中
            }
        } else {
            //遍历传入的路径
            for (String filepath : paths) {
                Image image = Toolkit.getDefaultToolkit().getImage(filepath);
                imgList.add(image);    //将Image对象添加到列表中
            }
        }
        return imgList;
    }


    // 1.String-->FileInputStream
    public static FileInputStream getImageByte(String infile) throws FileNotFoundException {
        FileInputStream imageByte = null;
        file = new File(infile);
        imageByte = new FileInputStream(file);
        return imageByte;
    }


    /**
     * @return java.lang.String
     * @Description Image-->base64
     * @ClassName Image2Base64
     * @Date 17:52 2020/5/14
     * @Param [image]
     */
    public static String Image2Base64(Image image, String type) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write((BufferedImage) image, type, baos);
        byte[] by = baos.toByteArray();
        return Base64Utils.encodeToString(Objects.requireNonNull(by)).replaceAll("\r\n", "");
    }


    // 2.图片到byte数组
    public static byte[] image2byte(String path) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    /**
     * byte数组到图片
     * @param path 图片本地路径
     * @title byte2image
     * @author yeweilong
     * @dateTime 2018-10-08
     */
    public void byte2image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) {
            return;
        }
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    /**
     * byte数组到16进制字符串
     * @param data 图片本地路径
     * @title byte2string
     * @author yeweilong
     * @dateTime 2018-10-08
     */
    public String byte2string(byte[] data) {
        if (data == null || data.length <= 1) {
            return "0x";
        }
        if (data.length > 200000) {
            return "0x";
        }
        StringBuffer sb = new StringBuffer();
        int buf[] = new int[data.length];
        // byte数组转化成十进制
        for (int k = 0; k < data.length; k++) {
            buf[k] = data[k] < 0 ? (data[k] + 256) : (data[k]);
        }
        // 十进制转化成十六进制
        for (int k = 0; k < buf.length; k++) {
            if (buf[k] < 16) {
                sb.append("0" + Integer.toHexString(buf[k]));
            } else {
                sb.append(Integer.toHexString(buf[k]));
            }
        }
        return "0x" + sb.toString().toUpperCase();
    }

    /**
     * 本地图片转换成base64字符串
     * @param imgFile 图片本地路径-基于web使用
     * @title ImageToBase64ByLocal
     * @dateTime 2018-10-08
     */
    public static String ImageToBase64ByLocal(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        String base64 = "data:image/jpeg;base64," + new String(Base64.encodeBase64(data));
        // 返回Base64编码过的字节数组字符串
        return base64;
    }

    /**
     * 数据库图片转换成base64字符串
     * @param imgFile 图片本地路径
     * @title byteToBase64ByOrcl
     * @dateTime 2018-10-11
     */
    public static String byteToBase64ByOrcl(byte[] imgFile) {
        return Base64Utils.encodeToString(imgFile).replaceAll("\r\n", "");
    }

    /**
     * 判断图片真实文件类型
     *
     * @throws IOException
     * @title bytesToHexString
     * @dateTime 2018-10-11
     */
    public static String bytesToHexString(InputStream stream) {
        byte[] src = new byte[3];
        try {
            stream.read(src, 0, src.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        switch (stringBuilder.toString().toUpperCase()) {
            case "FFD8FF":
                return "jpg";
            case "89504E":
                return "png";
            case "474946":
                return "jif";
            default:
                return "0000";
        }
    }

    /**
     * @return java.lang.String
     * @Description File-->base64
     * @ClassName file2Base64
     * @Date 10:30 2020/4/20
     * @Param [file]
     */
    public static String fileToBase64(File file) {
        if (file == null) {
            return null;
        }
        String base64 = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] buff = new byte[fin.available()];
            fin.read(buff);
            base64 = Base64Utils.encodeToString(buff).replaceAll("\r\n", "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }
}
