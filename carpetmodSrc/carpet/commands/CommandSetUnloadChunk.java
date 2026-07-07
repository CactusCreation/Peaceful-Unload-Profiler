package carpet.commands;

import carpet.helpers.PeacefulProfilingManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandSetUnloadChunk extends CommandCarpetBase {

    @Override
    public String getName() {
        return "setUnloadChunk";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/setUnloadChunk x z";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        try {
            int chunkX = parseChunkPosition(args[0], sender.getPosition().getX());
            int chunkZ = parseChunkPosition(args[1], sender.getPosition().getZ());
            PeacefulProfilingManager.unloadChunk = new ChunkPos(chunkX,chunkZ);
        } catch (Exception e) {
            throw new WrongUsageException(getUsage(sender));
        }

    }
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 1)
        {
            int chunkX = sender.getPosition().getX() >> 4;
            return getListOfStringsMatchingLastWord(args,
                    String.valueOf(chunkX));
        }

        if (args.length == 2)
        {
            int chunkZ = sender.getPosition().getZ() >> 4;
            return getListOfStringsMatchingLastWord(args,
                    String.valueOf(chunkZ));
        }

        return Collections.emptyList();
    }
}
