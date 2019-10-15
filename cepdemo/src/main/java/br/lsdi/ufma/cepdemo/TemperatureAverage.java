package br.lsdi.ufma.cepdemo;

import lombok.Value;

@Value
public class TemperatureAverage {
    private double value;
    private int timeWindow;
    private long timestamp;
}
