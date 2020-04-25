package com.github.games647.lambdaattack;

public class Options {

    public final String hostname;
    public final int port;
    public final int amount;
    public final int joinDelayMs;
    public final String botNameFormat;
    public final GameVersion gameVersion;
    public final boolean autoRegister;
    public final int msgDelay;
    public final String message;

    public Options(String hostname, int port, int amount, int joinDelayMs,
                   String botNameFormat, GameVersion gameVersion, boolean autoRegister, int msgDelay, String message) {
        this.hostname = hostname;
        this.port = port;
        this.amount = amount;
        this.joinDelayMs = joinDelayMs;
        this.botNameFormat = botNameFormat;
        this.gameVersion = gameVersion;
        this.autoRegister = autoRegister;
        this.msgDelay = msgDelay;
        this.message = message;
    }
}
