/**
 *   HeavySpleef - The simple spleef plugin for bukkit
 *   
 *   Copyright (C) 2013 matzefratze123
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package me.matzefratze123.heavyspleef.command;

import java.util.HashSet;
import java.util.Set;

import me.matzefratze123.heavyspleef.core.Game;
import me.matzefratze123.heavyspleef.core.GameManager;
import me.matzefratze123.heavyspleef.util.ArrayHelper;
import me.matzefratze123.heavyspleef.util.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandList extends HSCommand {

	public CommandList() {
		setMaxArgs(1);
		setMinArgs(0);
		setOnlyIngame(true);
		setPermission(Permissions.LIST);
		setUsage("/spleef list [name]");
		setTabHelp(new String[]{"[name]"});
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player)sender;
		
		if (args.length == 0) {
			if (GameManager.isInAnyGame(player)) {
				Game game = GameManager.fromPlayer(player);
				printList(game, player);
			} else {
				Set<String> games = ArrayHelper.asSet(GameManager.getGamesAsString());
				
				player.sendMessage(ChatColor.GRAY + "All games: " + games.toString());
			}
		} else if (args.length > 0) {
			if (!GameManager.hasGame(args[0])) {
				sender.sendMessage(_("arenaDoesntExists"));
				return;
			}
			
			Game game = GameManager.getGame(args[0]);
			printList(game, player);
		}
	}
	
	private void printList(Game game, Player player) {
		Set<Player> active = ArrayHelper.asSet(game.getPlayers());
		Set<Player> out = ArrayHelper.asSet(game.getOutPlayers());
		
		Set<String> activeString = new HashSet<String>();
		Set<String> outString = new HashSet<String>();
		
		for (Player activePlayer : active)
			activeString.add(activePlayer.getName());
		for (Player outPlayer : out)
			outString.add(outPlayer.getName());
		
		player.sendMessage(ChatColor.AQUA + "Active: " + activeString.toString());
		player.sendMessage(ChatColor.RED + "Out: " + outString.toString());
	}
	
}
