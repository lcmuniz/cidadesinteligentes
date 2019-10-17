package br.lsdi.ufma.cepdemo;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventBean;
import lombok.val;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class CEPOperator {

    private final EPServiceProvider engine;

    public CEPOperator() {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.getEPAdministrator().getConfiguration().addEventType(Temperature.class);
        engine.getEPAdministrator().getConfiguration().addEventType(TemperatureAverage.class);
    }

    public void addEPL(String epl, OperatorListener listener) {
        val statement = engine.getEPAdministrator().createEPL(epl);
        statement.addListener((newEvents, oldEvents) -> listener.process(newEvents));
    }

    public void addEPL(String epl) {
        engine.getEPAdministrator().createEPL(epl);
    }

    public void sendEvent(Object event) {
        engine.getEPRuntime().sendEvent(event);
    }

    public interface OperatorListener {
        void process(EventBean[] newEvents);
    }

}
