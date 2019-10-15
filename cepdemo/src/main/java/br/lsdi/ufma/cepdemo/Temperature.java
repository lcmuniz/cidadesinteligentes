package br.lsdi.ufma.cepdemo;

import lombok.Value;

@Value
public class Temperature {
    private double value;
    private long timestamp;
}
