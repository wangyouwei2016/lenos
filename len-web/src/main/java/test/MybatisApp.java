package test;

import org.mybatis.generator.api.ShellRunner;


public class MybatisApp {

  public static void main(String[] args) {
    args = new String[] { "-configfile", "len-web\\src\\main\\resources\\auto-config\\mybatis-config.xml", "-overwrite" };
    ShellRunner.main(args);
  }

}
