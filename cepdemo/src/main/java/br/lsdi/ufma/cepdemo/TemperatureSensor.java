package br.lsdi.ufma.cepdemo;

import lombok.val;
import org.greenrobot.eventbus.EventBus;
import org.springframework.stereotype.Service;

@Service
public class TemperatureSensor {

    public TemperatureSensor() {
        new Thread(() -> {
            while(true) {
                val event = new Temperature(Math.floor(Math.random() * 100), System.currentTimeMillis());
                EventBus.getDefault().post(event);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
