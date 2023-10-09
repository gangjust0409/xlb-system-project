package org.bdz.gql;

import org.bdz.gql.java8.FunctionClazz;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class JavaOrSpringApplicationTests {

    /**
     * java 8 ?????
     */
    @Test
    void contextLoads() {
        FunctionClazz clazz = ((str1, str2) ->  str1 + "\t" + str2);

        String show = clazz.show("解码", "xp");
        System.out.println(show);
    }

    /**
     * java 8 base64????
     */
    @Test
    void testBase64(){
        String name = "??";
        File dirFile = new File("D:\\javatest\\");
        if (!dirFile.exists())
            dirFile.mkdir();
        String str = "转化图像的透明程度。值定义转换的比例。值为0%则是完全透明，值为100%则图像无变化。值在0%和100%之间，则是效果的线性乘子，也相当于图像样本乘以数量。 若值未设置，值默认是1。该函数与已有的opacity属性很相似，不同之处在于通过filter，一些浏览器为了提升性能会提供硬件加速。";
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(dirFile.getAbsolutePath() + "\\base64.txt"))) {
            String base64Str = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
//            R collect = Stream.of(base64Str.toCharArray()).collect(Collectors.);
            fileOutputStream.write(base64Str.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //??
    @Test
    void testBase64Reader(){
        File file = new File("D:\\javatest\\base64.txt");
        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\javatest\\decodebase64.txt"));
            int len=0;
            while ((len=fileInputStream.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }
            //??
            System.out.println("执行成功");
            byte[] contentBytes = Base64.getDecoder().decode(sb.toString());
            System.err.println(new String(contentBytes, "UTF-8").indexOf("???"));
            fileOutputStream.write(contentBytes);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }





}
