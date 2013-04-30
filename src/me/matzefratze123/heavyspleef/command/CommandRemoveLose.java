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

import me.matzefratze123.heavyspleef.core.Game;
import me.matzefratze123.heavyspleef.core.GameManager;
import me.matzefratze123.heavyspleef.util.Permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRemoveLose extends HSCommand {

	public CommandRemoveLose() {
		setMinArgs(2);
		setMaxArgs(2);
		setOnlyIngame(true);
		setPermission(Permissions.REMOVE_LOSEZONE);
		setUsage("/spleef removelose <Name> <ID>");
		setTabHelp(new String[]{"<name> <id>"});
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player)sender;
		
		int id;
		try {
			id = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			player.sendMessage(_("notANumber", args[1]));
			return;
		}
		if (!GameManager.hasGame(args[0].toLowerCase())) {
			player.sendMessage(_("arenaDoesntExists"));
			return;
		}
		Game game = GameManager.getGame(args[0].toLowerCase());
		if (!game.hasLoseZone(id)) {
			player.sendMessage(_("loseZoneWithIDDoesntExists"));
			return;
		}
		if (game.isIngame() || game.isCounting()) {
			player.sendMessage(_("cantRemoveLoseWhileRunning"));
			return;
		}
		game.removeLoseZone(id);
		player.sendMessage(_("loseZoneRemoved", String.valueOf(id)));
		return;
				
		
	}

}
