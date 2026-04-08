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
import java.io.IOException;
import java.net.Socket;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;
/**
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaRepositories
@MapperScan("com.project.demo.dao")
public class Application implements ApplicationRunner {
	private Process redisProcess;  // 用于存储 Redis 进程对象
	
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application .class);
        ConfigurableApplicationContext cac= application.run(args);
        WebSocket.setApplicationContext(cac);
    }
	
	@Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("正在启动 Redis 服务...");

        // 根据操作系统选择启动命令
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder builder = new ProcessBuilder();

        if (os.contains("win")) {
             builder.command("D:/redis/redis-server.exe");
        } else {
            // Linux/Mac 系统
            builder.command("redis-server");
        }

        // 启动 Redis 进程
        redisProcess = builder.start();

        boolean redisReady = false;
        for (int i = 0; i < 10; i++) { // 最多等待 5 秒
            try (Socket s = new Socket("127.0.0.1", 6379)) {
                redisReady = true;
                break;
            } catch (Exception e) {
                Thread.sleep(500); // 每隔 500ms 重试一次
            }
        }
        if (!redisReady) {
            throw new IllegalStateException("Redis 启动超时！");
        }

        // 添加关闭钩子（应用退出时自动关闭 Redis）
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("开始应用关闭流程...");

            // 阶段1：等待 Spring 组件关闭
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException ignored) {}

            // 阶段2：优雅关闭 Redis
            if (redisProcess != null && redisProcess.isAlive()) {
                System.out.println("正在关闭 Redis 服务...");
                try {
                    // 尝试用 redis-cli 正常关闭
                    new ProcessBuilder(
                            "cmd.exe", "/c",
                            "\"D:/redis/redis-cli.exe\"",
                            "shutdown"
                    ).start();
                    if (!redisProcess.waitFor(2, TimeUnit.SECONDS)) {
                        redisProcess.destroyForcibly();
                    }
                } catch (Exception e) {
                    System.err.println("关闭失败，强制终止: " + e.getMessage());
                    redisProcess.destroyForcibly();
                }
            }
            System.out.println("Redis 服务已关闭");
        }));

        System.out.println("Redis 服务已启动！");
    }
}
