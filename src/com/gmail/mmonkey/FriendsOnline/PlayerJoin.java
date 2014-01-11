package com.gmail.mmonkey.FriendsOnline;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.mmonkey.AlertAPI.AlertAPI;
import com.gmail.mmonkey.AlertAPI.Utils.SendResult;

public class PlayerJoin implements Listener {

	private FriendsOnline plugin;
	
	public PlayerJoin(FriendsOnline plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		String player = event.getPlayer().getName();
		ArrayList<String> recipients = new ArrayList<String>();
		String message = (player + " logged in!");
		
		for(int i = 0; i < plugin.friendsList.size(); i++) {
			
			//Add players to recipient list if this player is on their notification list
			if(plugin.friendsList.get(i).getNotificationList().contains(player)) {
				recipients.add(plugin.friendsList.get(i).getName());
			}
			
			//If this is the last iteration, send the message
			if(i == plugin.friendsList.size() - 1) {
				SendResult result = AlertAPI.send(recipients, message);
				
				if(result.isSent()) {
					//plugin.log.info("[FriendsOnline] Message Sent!"); //Console spam, used for debugging.
				} else {
					plugin.log.info("[FriendsOnline] " + result.toString());
				}
			}
		}
	}

}