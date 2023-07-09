package com.amp.botrunningsystem.service.impl.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BotPool extends Thread{
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();//条件变量
    private Queue<Bot> bots = new LinkedList<>();
    //队列空的时候会阻塞住，队列有任务需要执行时会立刻执行
    //生产者会向队列中添加任务，消费者从队列中取出任务执行
    public void addBot(Integer userId, String botCode, String input) {
        lock.lock();
        try {
            bots.add(new Bot(userId, botCode, input));
            condition.signalAll();//唤醒睡眠(被await住)的线程
        } finally {
            lock.unlock();
        }
    }

    //目前只支持Java代码，后续可以执行Docker代码，支持其他语言且增强安全性
    private void consume(Bot bot){
        Consumer consumer = new Consumer();
        consumer.startTimeout(2000, bot);
    }
    @Override
    public void run() {
        while (true) {
            lock.lock();
            if(bots.isEmpty()){
                try {
                    condition.await();//await会自动释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock();
                    break;
                }
            } else {
                Bot bot = bots.remove();
                lock.unlock();
                consume(bot); // 耗时较长(需要编译执行代码)，可能会执行几秒钟，先解锁，避免阻塞
            }
        }
    }
}
