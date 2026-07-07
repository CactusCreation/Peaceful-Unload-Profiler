package carpet.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PeacefulProfilingManager {
        public static ChunkPos unloadChunk;
        private static int nextId = 1;

        private static ProfileEntry activeEntry;

        private static final Map<Integer, ProfileEntry> entries = new LinkedHashMap<>();

        public static void beginTrace(EntityPlayer player, World world, BlockPos pos) {

            if (activeEntry != null) {
                return;
            }

            activeEntry = new ProfileEntry(
                    nextId++,
                    player.getName(),
                    world.provider.getDimensionType().getId(),
                    pos
            );
            System.out.println("Started profile #" + activeEntry.getId());

        }

        public static ProfileEntry getActive() {
            return activeEntry;
        }

        public static void finishTrace() {
            if (activeEntry == null) {
                System.out.println("No active profile to finish");
                return;
            }

            System.out.println("Finishing profile #" + activeEntry.getId());

            activeEntry.finish();
            entries.put(activeEntry.getId(), activeEntry);

            activeEntry = null;
        }

        public static ProfileEntry get(int id) {
            return entries.get(id);
        }
        public static Map<Integer, ProfileEntry> getEntries() {
            return entries;
        }
}
