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
package de.matzefratze123.heavyspleef.command;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.matzefratze123.heavyspleef.core.Game;
import de.matzefratze123.heavyspleef.core.GameManager;
import de.matzefratze123.heavyspleef.core.LoseCause;
import de.matzefratze123.heavyspleef.util.Permissions;
import de.matzefratze123.heavyspleef.util.PvPTimerManager;

public class CommandHub extends HSCommand {

	public CommandHub() {
		setPermission(Permissions.TELEPORT_HUB);
		setUsage("/spleef hub");
		setOnlyIngame(true);
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player)sender;
		tpToHub(player);
	}
	
	public static void tpToHub(final Player player) {
		if (GameManager.getSpleefHub() == null) {
			player.sendMessage(_("noSpleefHubSet"));
			return;
		}
		
		if (GameManager.isActive(player)) {
			Game game = GameManager.fromPlayer(player);
			game.leave(player, LoseCause.QUIT);
		}
		
		PvPTimerManager.addToTimer(player, new Runnable() {
			
			@Override
			public void run() {
				PvPTimerManager.cancelTimerTask(player);
				player.teleport(GameManager.getSpleefHub());
				player.sendMessage(_("welcomeToHUB"));
			}
		});
		
		
		player.sendMessage(_("teleportingToHub"));
	}
	
}
