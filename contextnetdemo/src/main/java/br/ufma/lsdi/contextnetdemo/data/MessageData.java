package br.ufma.lsdi.contextnetdemo.data;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
public class MessageData implements Serializable {
    private String type;   // message (text message) or uuid (registration message)
    private String message;
    private String sender;
    private String receiver;
}
