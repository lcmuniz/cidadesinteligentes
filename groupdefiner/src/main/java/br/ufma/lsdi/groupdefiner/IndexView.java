package br.ufma.lsdi.groupdefiner;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class IndexView extends VerticalLayout {

    public IndexView(MyGroupDefiner groupDefiner) {
        add(new Label("Group Definer Started..."));
    }

}
