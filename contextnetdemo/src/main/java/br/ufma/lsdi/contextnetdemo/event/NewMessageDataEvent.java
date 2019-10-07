package br.ufma.lsdi.contextnetdemo.event;

import br.ufma.lsdi.contextnetdemo.data.MessageData;
import lombok.Value;

@Value
public class NewMessageDataEvent {
    private MessageData messageData;
}
