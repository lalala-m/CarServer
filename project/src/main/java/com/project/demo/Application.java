package com.project.demo;

import com.project.demo.websocket.WebSocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import java.net.Socket;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaRepositories
@MapperScan("com.project.demo.dao")
public class Application implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        ConfigurableApplicationContext cac = application.run(args);
        WebSocket.setApplicationContext(cac);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("正在检查 Redis 连接...");
        boolean redisConnected = false;
        for (int i = 0; i < 5; i++) {
            try (Socket s = new Socket("127.0.0.1", 6379)) {
                redisConnected = true;
                System.out.println("Redis 连接成功！");
                break;
            } catch (Exception e) {
                if (i == 0) System.out.println("Redis 未就绪，等待...");
                Thread.sleep(1000);
            }
        }
        if (!redisConnected) {
            System.out.println("警告: Redis 连接失败，部分功能可能受影响。");
        }
    }
}
