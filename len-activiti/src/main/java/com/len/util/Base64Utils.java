package com.len.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.log4j.Logger;

/**
 * Base64编码工具类
 * @author <a href="https://gitee.com/zzdevelop/lenosp">lenosp</a>
 * @date 2018/7/12
 */
public class Base64Utils {

    /**
     * 工具类 不可实例化
     */
    private Base64Utils() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger logger = Logger.getLogger(Base64Utils.class);

    public static String ioToBase64(InputStream in) throws IOException {
        String strBase64 = null;
        try {
            byte[] bytes = new byte[in.available()];
            // 将文件中的内容读入到数组中
            in.read(bytes);
            // 将字节流数组转换为字符串
            strBase64 = new String(Base64.getEncoder().encode(bytes));
            in.close();
        } catch (IOException ioe) {
            logger.error("图片转64编码异常", ioe);
        }
        return strBase64;
    }
}
