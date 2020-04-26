package com.github.games647.lambdaattack.bot.listener;

import com.github.fgruslan.lambdaattack.CaptchaExtractor;
import com.github.fgruslan.lambdaattack.StringRandomizer;
import com.github.games647.lambdaattack.Options;
import com.github.games647.lambdaattack.bot.Bot;
import com.github.games647.lambdaattack.bot.EntitiyLocation;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.protocol.v1_15.data.message.Message;
import com.github.steveice10.protocol.v1_15.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.protocol.v1_15.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.protocol.v1_15.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.protocol.v1_15.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;

import java.util.Random;
import java.util.logging.Level;

public class SessionListener115 extends SessionListener {

    public SessionListener115(Options options, Bot owner) {
        super(options, owner);
    }
    
    @SuppressWarnings("static-access")
	@Override
    public void packetReceived(PacketReceivedEvent receiveEvent) {
        if (receiveEvent.getPacket() instanceof ServerChatPacket) {
            ServerChatPacket chatPacket = receiveEvent.getPacket();
            Message message = chatPacket.getMessage();
            owner.getLogger().log(Level.INFO, "Received Message: {0}", message.getFullText());
            
            /**Captcha catcher**/
            if(message.getFullText().contains("каптчу")) {
            	CaptchaExtractor.currentCaptcha = message.getFullText();
            	owner.sendMessage(CaptchaExtractor.extract());
            	try {
     				Thread.currentThread().sleep(3000);
     			} catch (InterruptedException e1) {
     				// TODO Auto-generated catch block
     				e1.printStackTrace();
     			}
                 owner.sendMessage(Bot.COMMAND_IDENTIFIER + "register " + password + ' ' + password);
                 owner.sendMessage(Bot.COMMAND_IDENTIFIER + "login " + password);
            }
            /**End captcha catcher**/
            
            /** Flood trigger blocker **/
            if(message.getFullText().contains("flood")) {
            	options.message = options.message + java.util.UUID.randomUUID().toString();//StringRandomizer.randomizeString(new Random(), "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 10);
            }
            /** End flood blocker **/
            
        } else if (receiveEvent.getPacket() instanceof ServerPlayerPositionRotationPacket) {
            ServerPlayerPositionRotationPacket posPacket = receiveEvent.getPacket();

            double posX = posPacket.getX();
            double posY = posPacket.getY();
            double posZ = posPacket.getZ();
            float pitch = posPacket.getPitch();
            float yaw = posPacket.getYaw();
            EntitiyLocation location = new EntitiyLocation(posX, posY, posZ, pitch, yaw);
            owner.setLocation(location);
        } else if (receiveEvent.getPacket() instanceof ServerPlayerHealthPacket) {
            ServerPlayerHealthPacket healthPacket = receiveEvent.getPacket();
            owner.setHealth(healthPacket.getHealth());
            owner.setFood(healthPacket.getFood());
        } else if (receiveEvent.getPacket() instanceof ServerJoinGamePacket) {
            super.onJoin();
        }
    }
}