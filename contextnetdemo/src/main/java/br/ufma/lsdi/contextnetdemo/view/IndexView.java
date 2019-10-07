package br.ufma.lsdi.contextnetdemo.view;

import br.ufma.lsdi.contextnetdemo.node.ProcessingNode;
import br.ufma.lsdi.contextnetdemo.event.NewMessageDataEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.val;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;

@Route("")
@Push
public class IndexView extends VerticalLayout {

    private final TextArea messagesTextArea = new TextArea("Messages received");
    private final TextArea uuidsTextArea = new TextArea("Processing Nodes");
    private final Set<String> uuids = new HashSet<>();

    public IndexView(ProcessingNode processingNode) {
        EventBus.getDefault().register(this);
        buildLayout(processingNode);
    }

    private void buildLayout(ProcessingNode processingNode) {

        add(new Label("Processing Node UUID: " + processingNode.getUuid()));
        add(new Hr());

        val uuidTextField = new TextField("Send To");
        uuidTextField.setSizeFull();
        val messageTextField = new TextField("Message");
        messageTextField.setSizeFull();
        val statusLabel = new Label();

        uuidsTextArea.setSizeFull();

        val left = new VerticalLayout();
        left.setSizeFull();
        left.add(uuidsTextArea);
        left.add(uuidTextField);
        left.add(messageTextField);
        val button = new Button("Enviar");
        button.addClickListener(e -> sendMessage(processingNode, uuidTextField, messageTextField, statusLabel));
        left.add(button);
        left.add(statusLabel);

        messagesTextArea.setSizeFull();

        val right = new VerticalLayout();
        right.setSizeFull();
        right.add(messagesTextArea);
        val horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.add(left);
        horizontalLayout.add(right);

        add(horizontalLayout);
    }

    private void sendMessage(ProcessingNode processingNode, TextField uuidTextField, TextField messageTextField, Label statusLabel) {
        processingNode.sendMessage(messageTextField.getValue(), uuidTextField.getValue());
        statusLabel.setText("Message has been sent.");
        messageTextField.clear();
    }

    @Subscribe
    public void on(NewMessageDataEvent event) {
        if ("message".equals(event.getMessageData().getType())) {
            getUI().get().access(() -> {
                val oldMessages = messagesTextArea.getValue();
                val newMessage = event.getMessageData().getMessage() + " from " + event.getMessageData().getSender();
                val message = "".equals(oldMessages) ? newMessage : oldMessages + "\n" + newMessage;
                messagesTextArea.setValue(message);
            });
        }
        else if ("uuid".equals(event.getMessageData().getType())) {
            val uuid = event.getMessageData().getMessage();
            uuids.add(uuid);
            val value = String.join("\n", uuids);
            getUI().get().access(() -> {
                uuidsTextArea.setValue(value);
            });
        }
    }

}
