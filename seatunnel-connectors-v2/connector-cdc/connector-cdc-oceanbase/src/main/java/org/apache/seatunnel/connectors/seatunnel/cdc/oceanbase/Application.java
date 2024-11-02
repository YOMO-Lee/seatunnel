package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.oceanbase.clogproxy.client.LogProxyClient;
import com.oceanbase.clogproxy.client.config.ClientConf;
import com.oceanbase.clogproxy.client.config.ObReaderConfig;
import com.oceanbase.clogproxy.client.exception.LogProxyClientException;
import com.oceanbase.clogproxy.client.listener.RecordListener;
import com.oceanbase.oms.logmessage.DataMessage;
import com.oceanbase.oms.logmessage.LogMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @类描述 TODO
 * @创建作者 YOMO Lee
 * @创建团队 TCL格创东智
 * @创建时间 2024/11/2--星期六--1:28
 * @创建项目 seatunnel
 * @版本号 V1.0
 */
public class Application {

    private static final Log log = LogFactory.get();


    public static void main(String[] args) {
        ObReaderConfig config = new ObReaderConfig();
        config.setRsList("10.171.204.201:2882:2881");
        config.setUsername("root@dw_test#dw_center");
        config.setPassword("Bh%Yac{C9l-~#?~dq[}bSQO5ng8Rr?w");
        config.setSysUsername("root");
        config.setSysPassword("cP8SZNUecCJ&8O^;kBEKH^=dR:=Mn@*a");
        config.setStartTimestamp(0L);
        config.setTableWhiteList("dw_test.*.*");

        LogProxyClient client = new LogProxyClient(
                "10.171.204.201",
                2983,
                config,
                ClientConf.builder()
                        .transferQueueSize(1000)
                        .maxReconnectTimes(0)
                        .ignoreUnknownRecordType(true)
                        .build());
        AtomicBoolean started = new AtomicBoolean(false);
        CountDownLatch latch = new CountDownLatch(1);
        BlockingQueue<LogMessage> messageQueue = new LinkedBlockingQueue<>(4);

// 绑定一个处理日志数据的 RecordListener
        client.addListener(new RecordListener() {
            @Override
            public void notify(LogMessage message){
                // 在此添加数据处理逻辑
                switch (message.getOpt()) {
                    case HEARTBEAT:
                        log.info(
                                "Received heartbeat with checkpoint {}",
                                message.getCheckpoint());
                        if (started.compareAndSet(false, true)) {
                            latch.countDown();
                        }
                        break;
                    case BEGIN:
                        log.info("Received transaction begin: {}", message);
                        break;
                    case COMMIT:
                        log.info("Received transaction commit: {}", message);
                        break;
                    case INSERT:
                    case UPDATE:
                        System.out.println(message.getFieldCount());
                        System.out.println(message.getFileOffset());
                        System.out.println(message.getFieldList().size());
                        for (DataMessage.Record.Field item : message.getFieldList()) {
                            System.out.println("=======================================");
                            System.out.println(item.getValue());
                            System.out.println("==============");
                            System.out.println(item.getFieldname());
                            System.out.println("==============");
                            System.out.println(item.getType());
                        }
                        case DELETE:
                    case DDL:
                        try {
                            messageQueue.put(message);
                        } catch (InterruptedException e) {
                            throw new RuntimeException("Failed to add message to queue", e);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Unsupported log message type: " + message.getOpt());
                }
            }

            @Override
            public void onException(LogProxyClientException e) {
                log.error(e.getMessage());
            }
        });

        client.start();
        client.join();
    }

}
