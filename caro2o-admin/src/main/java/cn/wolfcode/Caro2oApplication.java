package cn.wolfcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Caro2oApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(Caro2oApplication.class, args);
        System.out.println("             _      ___   \n" +
                "    o O O   | |    |_ _|  \n" +
                "   o        | |__   | |   \n" +
                "  TS__[O]   |____| |___|  \n" +
                " {======|__|\"\"\"\"\"||\"\"\"\"\"||\"\"\"\"\"||\"\"\"\"\"||\"\"\"\"\"||\"\"\"\"\"| \n" +
                "./o--000'\"\"`-0-0-'`-0-0-'`-0-0-'`-0-0-'`-0-0-'`-0-0-' ");
    }
}
