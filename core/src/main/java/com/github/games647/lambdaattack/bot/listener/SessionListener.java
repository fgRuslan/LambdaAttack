//Copyright fgRuslan, Sapphire_DEV | Пошел нахуй отсюда
package com.github.games647.lambdaattack.bot.listener;

import java.util.logging.Level;

import com.github.games647.lambdaattack.Options;
import com.github.games647.lambdaattack.bot.Bot;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;

public abstract class SessionListener extends SessionAdapter {

    protected final Options options;
    protected final Bot owner;
    
    public final String password = "LordSapphire";


    public SessionListener(Options options, Bot owner) {
        this.options = options;
        this.owner = owner;
    }

    @Override
    public void disconnected(DisconnectedEvent disconnectedEvent) {
        String reason = disconnectedEvent.getReason();
        owner.getLogger().log(Level.INFO, "Отключен: {0}", reason);
    }

    @SuppressWarnings("static-access")
	public void onJoin() {
        if (options.autoRegister) {
            //String password = "LordSapphire";//Пароль при регистрации (для дауна который вскрыл прогу)
            try {
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            owner.sendMessage("/register LordSapphire LordSapphire");
            owner.sendMessage("/reg LordSapphire");
            owner.sendMessage("/login LordSapphire");
            
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
