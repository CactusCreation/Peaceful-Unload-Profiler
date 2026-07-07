package carpet.helpers;

import net.minecraft.util.math.BlockPos;

public class ProfileEntry
{
    private final int id;
    private final String player;
    private final int dimension;
    private final BlockPos triggerPos;

    private final long traceStart;

    public long playerPhaseEnd = -1;
    public long mobSpawnEnd = -1;
    public volatile long chunkUnloadStart = -1;
    public volatile long chunkUnloadEnd = -1;
    public volatile long beaconThreadStart = -1;
    public volatile long beaconThreadEnd = -1;

    private boolean complete = false;

    public ProfileEntry(int id, String player, int dimension, BlockPos triggerPos)
    {
        this.id = id;
        this.player = player;
        this.dimension = dimension;
        this.triggerPos = triggerPos.toImmutable();

        this.traceStart = System.nanoTime();
    }


    public void playerPhaseFinished()
    {
        playerPhaseEnd = System.nanoTime();
        System.out.println("Player phase finished");

    }

    public void mobSpawnFinished()
    {
        mobSpawnEnd = System.nanoTime();
        System.out.println("Mob spawn phase finished");

    }

    public void chunkUnloadStarted()
    {
        chunkUnloadStart = System.nanoTime();
        System.out.println("Chunk unload started");

    }

    public void chunkUnloadFinished()
    {
        chunkUnloadEnd = System.nanoTime();
        System.out.println("Chunk unload finished");

    }

    public void beaconThreadStarted()
    {
        beaconThreadStart = System.nanoTime();
        System.out.println("Beacon thread started");

    }

    public void beaconThreadFinished()
    {
        beaconThreadEnd = System.nanoTime();
        System.out.println("Beacon thread ended ");

    }

    public void finish()
    {
        complete = true;
    }


    public int getId()
    {
        return id;
    }

    public String getPlayer()
    {
        return player;
    }

    public int getDimension()
    {
        return dimension;
    }

    public BlockPos getTriggerPos()
    {
        return triggerPos;
    }

    public boolean isComplete()
    {
        return playerPhaseEnd != -1
                && mobSpawnEnd != -1
                && chunkUnloadEnd != -1
                && beaconThreadEnd != -1;
    }


    public long getPlayerPhaseNs()
    {
        return playerPhaseEnd - traceStart;
    }

    public long getMobSpawnNs()
    {
        return mobSpawnEnd - playerPhaseEnd;
    }

    public long getChunkUnloadNs()
    {
        return chunkUnloadEnd - chunkUnloadStart;
    }

    public long getBeaconLifetimeNs()
    {
        return beaconThreadEnd - beaconThreadStart;
    }



    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Profile #").append(id).append('\n');
        sb.append("Player: ").append(player).append('\n');
        sb.append("Dimension: ").append(dimension).append('\n');
        sb.append("Trigger: ").append(triggerPos).append('\n');
        sb.append('\n');

        if (playerPhaseEnd != -1)
            sb.append(String.format("Player Phase: %f", (getPlayerPhaseNs())));

        if (mobSpawnEnd != -1)
            sb.append(String.format("Mob Spawn: %f", (getMobSpawnNs())));

        if (chunkUnloadEnd != -1)
            sb.append(String.format("Chunk Unload: %f", (getChunkUnloadNs())));

        if (beaconThreadEnd != -1)
            sb.append(String.format("Beacon Thread: %f", getBeaconLifetimeNs()));

        sb.append("You suck or the thread didn't live long enough: ").append((getPlayerPhaseNs() + getMobSpawnNs() + getChunkUnloadNs() > getBeaconLifetimeNs()));


        sb.append("Complete: ").append(complete);

        return sb.toString();
    }

}