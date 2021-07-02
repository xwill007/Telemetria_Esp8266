package com.willvargas.telemetria_esp8266;

public class ESP01 {
    String ESPid, contador;
    public ESP01(String ESPid, String contador) {
        this.ESPid = ESPid;
        this.contador = contador;
    }

    public String getESPid() {
        return ESPid;
    }

    public String getContador() {
        return contador;
    }
}
