package com.gmail.mmonkey.FriendsOnline;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class FriendsOnline extends JavaPlugin {
	
	//Log
	public Logger log = Logger.getLogger("Minecraft");
	
	public ArrayList<Friends> friendsList = new ArrayList<Friends>();
	
	public void onEnable() {
		
		//Load config
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		
		//Load list
		try{
			friendsList = SaveLoad.load(getDataFolder().getPath() + File.separator + "list.bin");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//Register Commands
		getCommand("notify").setExecutor(new Notify(this));
		
		//Register Listeners
		getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
		getServer().getPluginManager().registerEvents(new RegistrationListener(this), this);
		
	}
	
	public void onDisable() {
		
		//Save list
		try{
			SaveLoad.save(friendsList, getDataFolder().getPath() + File.separator + "list.bin");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}