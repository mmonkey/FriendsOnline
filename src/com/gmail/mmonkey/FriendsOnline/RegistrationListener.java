package com.gmail.mmonkey.FriendsOnline;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.mmonkey.AlertAPI.ContactRegisterEvent;
import com.gmail.mmonkey.AlertAPI.ContactUnregisterEvent;

public class RegistrationListener implements Listener {
	
	private FriendsOnline plugin;
	
	public RegistrationListener(FriendsOnline plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onUnregister(ContactUnregisterEvent event) {
		
		Player player = event.getPlayer();
		
		for(int i = 0; i < plugin.friendsList.size(); i++) {
			if(plugin.friendsList.get(i).getName().equals(player.getName())) {
				plugin.friendsList.get(i).getNotificationList().clear();
				plugin.friendsList.remove(i);
			}
		}
	}
	
	@EventHandler
	public void onRegister(ContactRegisterEvent event) {
		
		Player player = event.getPlayer();
		boolean contains = false;
		
		for(int i = 0; i < plugin.friendsList.size(); i++) {
			if(plugin.friendsList.get(i).getName().equals(player.getName())) {
				contains = true;
			}
		}
		
		if(!contains) {
			Friends f = new Friends(player.getName());
			plugin.friendsList.add(f);
		}
		
	}

}
