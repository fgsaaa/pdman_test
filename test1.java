import com.luciad.imageio.webp.WebPReadParam;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test1 {

    public static void main(String[] args) {
        String directoryPath = "E:\\fgs\\demo\\pdman-web\\pdman-web"; // 更改为要读取的目录路径
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            renameJSFiles(directory);
            System.out.println("文件重命名完成！");
        } else {
            System.out.println("指定的路径不是一个有效的目录。");
        }
    }

    public static void renameJSFiles(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    renameJSFiles(file); // 递归调用处理子目录
                } else if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.toLowerCase().endsWith(".js")) {
                        String newFileName = fileName.substring(0, fileName.length() - 3) + ".txt1";
                        File newFile = new File(file.getParent(), newFileName);
                        if (file.renameTo(newFile)) {
                            System.out.println("文件重命名成功：" + file.getAbsolutePath() + " -> " + newFile.getAbsolutePath());
                        } else {
                            System.out.println("无法重命名文件：" + file.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

}
