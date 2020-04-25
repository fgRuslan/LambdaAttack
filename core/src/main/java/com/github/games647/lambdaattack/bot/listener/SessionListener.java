package com.github.games647.lambdaattack.bot.listener;

import com.github.games647.lambdaattack.LambdaAttack;
import com.github.games647.lambdaattack.Options;
import com.github.games647.lambdaattack.bot.Bot;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;

import java.util.logging.Level;

public abstract class SessionListener extends SessionAdapter {

    protected final Options options;
    protected final Bot owner;


    public SessionListener(Options options, Bot owner) {
        this.options = options;
        this.owner = owner;
    }

    @Override
    public void disconnected(DisconnectedEvent disconnectedEvent) {
        String reason = disconnectedEvent.getReason();
        owner.getLogger().log(Level.INFO, "Disconnected: {0}", reason);
    }

    @SuppressWarnings("static-access")
	public void onJoin() {
        if (options.autoRegister) {
            String password = "LambdaAttack";
            try {
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            owner.sendMessage(Bot.COMMAND_IDENTIFIER + "register " + password + ' ' + password);
            owner.sendMessage(Bot.COMMAND_IDENTIFIER + "login " + password);
            
            int delay = options.msgDelay;
            System.out.println(delay);
            
            try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            //Запускаем поток который каждую секунду будет спамить
            Thread t1 = new Thread(new Runnable() {
                public void run()
                {
                	while(true) {
                		try {
							Thread.currentThread().sleep(delay);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		owner.sendMessage(options.message);
                	}
                }});  
            t1.start();

        }
    }
}
