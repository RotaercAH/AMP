package com.amp.botrunningsystem.service.impl.utils;

import com.amp.botrunningsystem.utils.BotInterface;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class Consumer extends Thread{
    private Bot bot;
    private static RestTemplate restTemplate;
    private final static String receiveBotMoveUrl = "http://127.0.0.1:8080/pk/receive/bot/move/";
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        Consumer.restTemplate = restTemplate;
    }

    public void startTimeout(long timeout, Bot bot) {
        this.bot = bot;
        this.start();

        try {
            this.join(timeout);//等待timeout秒后，会继续执行后续操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();//最多等待timeout秒，之后会中断这个线程，防止用户代码有问题
        }
    }

    private String addUid(String code, String uid) { //
        int k = code.indexOf(" implements java.util.function.Supplier<Integer>");
        return code.substring(0, k) + uid + code.substring(k);

    }

    @Override
    public void run() {
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0, 8);

        Supplier<Integer> botInterface = Reflect.compile(
            "com.amp.botrunningsystem.utils.Bot" + uid,
            addUid(bot.getBotCode(), uid)
        ).create().get();

        File file = new File("input.txt");
        try (PrintWriter fout = new PrintWriter(file)) {
            fout.println(bot.getInput());
            fout.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Integer direction = botInterface.get();

        System.out.println("move-direction: " + bot.getUserId() + " " + direction);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", bot.getUserId().toString());
        data.add("direction", direction.toString());

        restTemplate.postForObject(receiveBotMoveUrl, data, String.class);
    }
}
