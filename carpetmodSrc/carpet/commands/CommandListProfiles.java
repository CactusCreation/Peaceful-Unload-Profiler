package carpet.commands;

import carpet.helpers.PeacefulProfilingManager;
import carpet.helpers.ProfileEntry;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Map;

public class CommandListProfiles extends CommandCarpetBase
{
    @Override
    public String getName()
    {
        return "listProfiles";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "/listProfiles";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        Map<Integer, ProfileEntry> profiles = PeacefulProfilingManager.getEntries();

        if (profiles.isEmpty())
        {
            sender.sendMessage(new TextComponentString("No profiles recorded."));
            return;
        }

        sender.sendMessage(new TextComponentString(
                "Profiles (" + profiles.size() + "):"
        ));

        for (Integer id : profiles.keySet())
        {
            sender.sendMessage(new TextComponentString(
                    "#" + id
            ));
        }
    }
}