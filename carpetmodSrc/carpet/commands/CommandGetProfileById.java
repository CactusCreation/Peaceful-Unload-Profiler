package carpet.commands;

import carpet.helpers.PeacefulProfilingManager;
import carpet.helpers.ProfileEntry;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandGetProfileById extends CommandCarpetBase
{
    @Override
    public String getName()
    {
        return "getProfileById";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "/getProfileById id";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length != 1)
        {
            throw new WrongUsageException(getUsage(sender));
        }

        int id;

        try
        {
            id = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e)
        {
            throw new NumberInvalidException("Invalid profile id");
        }

        ProfileEntry entry = PeacefulProfilingManager.get(id);

        if (entry == null)
        {
            throw new CommandException("No profile found with id " + id);
        }

        sender.sendMessage(new net.minecraft.util.text.TextComponentString(entry.toString()));
    }
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(
                    args,
                    PeacefulProfilingManager.getEntries()
                            .keySet()
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.toList())
            );
        }

        return Collections.emptyList();
}
}