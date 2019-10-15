package br.lsdi.ufma.cepdemo;

import com.espertech.esper.client.EventBean;
import lombok.val;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CepdemoApplication implements CommandLineRunner {

    private CEP cep;

    public static void main(String[] args) {
        SpringApplication.run(CepdemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        EventBus.getDefault().register(this);
        cep = new CEP();
        val epl1 = "insert into TemperatureAverage select avg(value), 5, max(timestamp) from Temperature#win:time(5 sec)";
        cep.addEPL(epl1);
        val epl2 = "select * from TemperatureAverage";
        cep.addEPL(epl2, (events) -> teste(events));
    }

    @Subscribe
    public void on(Temperature event) {
        cep.sendEvent(event);
    }

    private void teste(EventBean[] events) {
        System.out.println(events[0].getUnderlying());;
    }

}
