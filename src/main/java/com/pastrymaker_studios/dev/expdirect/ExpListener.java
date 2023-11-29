package com.pastrymaker_studios.dev.expdirect;

import com.bergerkiller.bukkit.common.conversion.DuplexConversion;
import com.bergerkiller.bukkit.common.internal.CommonNMS;
import com.bergerkiller.bukkit.common.utils.EntityUtil;
import com.bergerkiller.bukkit.common.utils.MathUtil;
import com.bergerkiller.bukkit.common.utils.PlayerUtil;
import com.bergerkiller.bukkit.common.utils.WorldUtil;
import com.bergerkiller.generated.net.minecraft.server.level.EntityPlayerHandle;
import com.bergerkiller.generated.net.minecraft.world.entity.EntityHandle;
import com.bergerkiller.mountiplex.conversion.util.ConvertingList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;

public class ExpListener implements Listener {

    private final double SEARCH_RADIUS = 25;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawnEvent(EntitySpawnEvent event) {
        if(!Objects.equals(event.getEntityType().getEntityClass(), ExperienceOrb.class)) {
            return;
        }

        ExperienceOrb orb = (ExperienceOrb) event.getEntity();
        int exp = orb.getExperience();
        Player closestPlayer = null;
        double closestPlayerDist = SEARCH_RADIUS;

        for( Player p : getNearbyPlayers(orb, SEARCH_RADIUS)) {
            Vector playerPos = p.getLocation().toVector(), orbPos = orb.getLocation().toVector();
            double playerDist = Math.abs(MathUtil.distance(playerPos.getX(), playerPos.getY(), playerPos.getZ(), orbPos.getX(), orbPos.getY(), orbPos.getZ()));

            if(closestPlayer == null) {
                closestPlayer = p;
                closestPlayerDist = playerDist;
            } else if(playerDist < closestPlayerDist) {
                    closestPlayer = p;
                    closestPlayerDist = playerDist;
            }

        }

        if(closestPlayer != null && exp > 0) {
            closestPlayer.giveExp(exp);
            orb.remove();
            event.setCancelled(true);
        }

    }

    /**
     * Gets a (referenced) list of all players nearby another Player
     *
     * @param entity to get the nearby players of
     * @param radius to look around the player for other players
     * @return list of nearby players
     */
    public static List<Player> getNearbyPlayers(Entity entity, double radius) {
        EntityHandle handle = CommonNMS.getHandle(entity);
        List<?> nearbyPlayerHandles = handle.getWorld().getRawEntitiesOfType(
            EntityPlayerHandle.T.getType(),
            handle.getBoundingBox().grow(radius, radius, radius));
        return new ConvertingList<>(nearbyPlayerHandles, DuplexConversion.player);
    }


}
