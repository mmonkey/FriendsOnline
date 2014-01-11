package com.gmail.mmonkey.FriendsOnline;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.mmonkey.AlertAPI.AlertAPI;

public class Notify implements CommandExecutor {
	
	private FriendsOnline plugin;
	
	public Notify(FriendsOnline plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		//Check sender
		if(!(sender instanceof Player)) {
			sender.sendMessage("[FriendsOnline] Only players may use /notify.");
			return true;
		}
		
		Player player = (Player) sender;
		
		//Check perms
		if(!player.hasPermission("friendsonline.use")) {
			player.sendMessage(ChatColor.RED + "You do not have permission.");
			return true;
		}
		
		//Check if player is in address book
		if(!AlertAPI.isInAddressBook(player.getName())) {
			player.sendMessage(ChatColor.RED + "You need to be registered before you can use this command.");
			player.sendMessage(ChatColor.RED + "To register, use command: " + ChatColor.WHITE + " /register" + ChatColor.RED + ".");
			return true;
		}
		
		if(args.length == 1) {
			
			//List player on contact list
			if(args[0].equalsIgnoreCase("list")) {
				
				StringBuilder players = new StringBuilder();
				
				for(int i = 0; i < plugin.friendsList.size(); i++) {
					if(plugin.friendsList.get(i).getName().equals(player.getName())) {
						for(int j = 0; j < plugin.friendsList.get(i).getNotificationList().size(); j++) {
							if(j == 0) {
								players.append(plugin.friendsList.get(i).getNotificationList().get(j));
							} else {
								players.append(", " + plugin.friendsList.get(i).getNotificationList().get(j));
							}
						}
					}
				}
				
				if(players.length() != 0) {
					player.sendMessage(ChatColor.GOLD + "Your notification list:");
					player.sendMessage(players.toString());
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "Your notification list is empty.");
					return true;
				}

			//Add Specified player to notification list
			} else {

				for(int i = 0; i < plugin.friendsList.size(); i++) {
					if(plugin.friendsList.get(i).getName().equals(player.getName())) {
						if(!plugin.friendsList.get(i).getNotificationList().contains(args[0])) {
							plugin.friendsList.get(i).addNotification(args[0]);
							player.sendMessage(ChatColor.GOLD + args[0] + ChatColor.GREEN + " has been added to your notification list!");
							return true;
						} else {
							player.sendMessage(ChatColor.GOLD + args[0] + ChatColor.RED + " is already on your notification list!");
							return true;
						}
					}
				}
				Friends f = new Friends(player.getName());
				plugin.friendsList.add(f);
				player.sendMessage(ChatColor.GOLD + args[0] + ChatColor.GREEN + " has been added to your notification list!");
				return true;
			}
			
		} else if(args.length == 2) {
			
			//Remove specified player from notification list
			if(args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("delete")) {
				
				for(int i = 0; i < plugin.friendsList.size(); i++) {
					if(plugin.friendsList.get(i).getName().equals(player.getName())) {
						if(plugin.friendsList.get(i).getNotificationList().contains(args[1])) {
							plugin.friendsList.get(i).removeNotification(args[1]);
							player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " has been removed from your notification list!");
							return true;
						}
					}
				}
				player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.RED + " is not on your notification list, nothing to remove!");
				return true;
				
			//Command not understood (invalid argument)	
			} else {
				player.sendMessage(ChatColor.RED + "Command not understood.");
				return false;
			}
		
		//Command not understood (wrong number of arguments)
		} else {
			player.sendMessage(ChatColor.RED + "Command not understood.");
			return false;
		}
	}

}
