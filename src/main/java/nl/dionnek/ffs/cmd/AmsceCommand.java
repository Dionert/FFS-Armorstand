package nl.dionnek.ffs.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AmsceCommand implements CommandExecutor {

    private final AmsceUtils amsceUtils;

    public AmsceCommand(AmsceUtils amsceUtils) {
        this.amsceUtils = amsceUtils;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You cannot execute this command.");
            return false;
        }

        Player player = (Player) sender;
        amsceUtils.createExplodeCircle(player.getLocation());
        return true;
    }
}
