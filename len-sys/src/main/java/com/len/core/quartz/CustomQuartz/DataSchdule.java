package com.len.core.quartz.CustomQuartz;

import java.io.*;

import org.springframework.stereotype.Component;

import com.len.core.annotation.Log;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * 定时还原数据库数据
 */

@Component
@Slf4j
public class DataSchdule {

    // @Scheduled(cron = "0 0/5 * * * ? ")
    @Log(type = Log.LOG_TYPE.UPDATE, desc = "定时还原数据库")
    public static void restData() throws IOException, InterruptedException {
        // SQL文件路径
        String sqlPath = "G:\\os\\sql\\lenos_test.sql";
        try(
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sqlPath), "utf-8"));
        ) {
            Runtime rt = Runtime.getRuntime();

            Process process = rt.exec(
                "D:\\java\\mysql-5.7.21-winx64\\mysql-5.7.21-winx64\\bin\\mysql.exe -hlocalhost -uroot -ppassword --default-character-set=utf8 "
                    + "lenos_test");
            OutputStream outputStream = process.getOutputStream();
            String str = null;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            str = sb.toString();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8");
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
            log.info("数据库还原成功");
        } catch (IOException e) {
            log.error("数据库还原失败");
            e.printStackTrace();
        }
    }
}