package com.wyf.common.utils;

import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * @Description: 图片处理工具类
 * @Date: 2020/10/24 20:20
 * @Version: 1.0
 */
public class ImageUtils {

    /**
     * @Description 将图片缩放返回image
     * @ClassName   getScaledImage
     * @Date        15:01 2020/4/13
     * @Param       [srcImg, w, h]
     * @return      java.awt.Image
     */
    public static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }


    /**
     * @Description base64-->指定大小image
     * @ClassName   getImageByte
     * @Date        13:20 2020/4/23
     * @Param       [base64Str, w, h]
     * @return      java.awt.Image
     */
    public static BufferedImage getImageByte(String base64Str, int w, int h) throws IOException {
        Image srcImg = base64ToImage(base64Str);
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    /**
     * @Description 获取图片数组返回Image
     * @ClassName   byteToImage
     * @Date        8:28 2020/4/14
     * @Param       [datas]
     * @return      java.awt.Image
     */
    public static Image base64ToImage(String base64) throws IOException{
        if (base64.length()==0){
            return null;
        }

        BASE64Decoder decode = new BASE64Decoder();
        byte [] datas= decode.decodeBuffer(base64);

        Image image = Toolkit.getDefaultToolkit().createImage(datas);
        BufferedImage img = toBufferedImage(image);
        return img;
    }

    /**
     * @Description 将Image--> BufferedImage
     * 禁止使用Image.read()读取图像文件，图像头文件信息读取不完整导致色差
     * @ClassName   toBufferedImage
     * @Date        17:41 2020/6/28
     * @Param       [image]
     * @return      java.awt.image.BufferedImage
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }


    /***
     * 图片镜像处理
     * @param file
     * @param FX 0 为上下反转 1 为左右反转
     * @return
     */
    public static void imageMisro(File file, int FX) {
        try {
            Image image = Toolkit.getDefaultToolkit().getImage(file.getPath());
            BufferedImage bufferedimage = toBufferedImage(image);
            int w = bufferedimage.getWidth();
            int h = bufferedimage.getHeight();

            int[][] datas = new int[w][h];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    datas[j][i] = bufferedimage.getRGB(j, i);
                }
            }
            int[][] tmps = new int[w][h];
            if (FX == 0) {
                for (int i = 0, a = h - 1; i < h; i++, a--) {
                    for (int j = 0; j < w; j++) {
                        tmps[j][a] = datas[j][i];
                    }
                }
            } else if (FX == 1) {
                for (int i = 0; i < h; i++) {
                    for (int j = 0, b = w - 1; j < w; j++, b--) {
                        tmps[b][i] = datas[j][i];
                    }
                }
            }
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    bufferedimage.setRGB(j, i, tmps[j][i]);
                }
            }

            ImageIO.write(bufferedimage, "jpg", file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *
     * 镜像处理，输入image和方式，返回翻转的新image
     * type = 0 表示上下翻转，type = 1 表示左右翻转
     */
    public static Image imageMisro(Image image, int type) {
        try {
            //用到了自己写的方法
            BufferedImage bufferedimage = ImageToBufferedImage(image);

            int w = bufferedimage.getWidth();
            int h = bufferedimage.getHeight();

            int[][] datas = new int[w][h];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    datas[j][i] = bufferedimage.getRGB(j, i);
                }
            }
            int[][] tmps = new int[w][h];
            if (type == 0) {
                for (int i = 0, a = h - 1; i < h; i++, a--) {
                    for (int j = 0; j < w; j++) {
                        tmps[j][a] = datas[j][i];
                    }
                }
            } else if (type == 1) {
                for (int i = 0; i < h; i++) {
                    for (int j = 0, b = w - 1; j < w; j++, b--) {
                        tmps[b][i] = datas[j][i];
                    }
                }
            }
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    bufferedimage.setRGB(j, i, tmps[j][i]);
                }
            }


            Image newImage = (Image) bufferedimage;
            return newImage;
            //ImageIO.write(bufferedimage, "jpg", file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Image转换成BufferedImage
    public static BufferedImage ImageToBufferedImage(Image image) {
        BufferedImage bufferedimage = new BufferedImage
                (image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedimage.createGraphics();
        g.drawImage(image, 0, 0, null);    //这里，大家可能会有疑问，似乎没有对bufferedimage干啥
        g.dispose();                    //但是是正确的，g调用drawImage就自动保存了
        return bufferedimage;
    }

    /**
     * 网络图片转换Base64的方法
     * @param netImagePath     
     */
    public static String NetImageToBase64(String netImagePath) throws IOException{
        final ByteArrayOutputStream data = new ByteArrayOutputStream();

        // 创建URL
        URL url = new URL(netImagePath);
        final byte[] by = new byte[1024];
        // 创建链接
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);

        InputStream is = conn.getInputStream();
        // 将内容读取内存中
        int len = -1;
        while ((len = is.read(by)) != -1) {
            data.write(by, 0, len);
        }
        // 对字节数组Base64编码
        String strNetImageToBase64 = Base64Utils.encodeToString(data.toByteArray()).replaceAll("\r\n", "");
        // 关闭流
        is.close();
        return strNetImageToBase64;
    }

    /**
     * 本地图片转换Base64的方法
     * @param imgPath     
     */
    public static String ImageToBase64(String imgPath) throws IOException {
        byte[] data = null;
        // 读取图片字节数组
        InputStream in = new FileInputStream(imgPath);
        data = new byte[in.available()];
        in.read(data);
        in.close();
        // 对字节数组Base64编码，返回Base64编码过的字节数组字符串
        return Base64Utils.encodeToString(Objects.requireNonNull(data)).replaceAll("\r\n", "");
    }
}
