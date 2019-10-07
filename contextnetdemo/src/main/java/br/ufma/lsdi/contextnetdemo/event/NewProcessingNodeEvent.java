package br.ufma.lsdi.contextnetdemo.event;

import lombok.Value;

@Value
public class NewProcessingNodeEvent {
    private String uuid;
}
