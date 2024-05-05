package nl.dionnek.ffs.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AmsceUtils {

    private final JavaPlugin plugin;

    public AmsceUtils(JavaPlugin plugin) {
        this.plugin = plugin;
    }



    /**
     * Creates an explode circle effect centered at the given location.
     *
     * @param center The center location of the explode circle.
     */
    public void createExplodeCircle(Location center) {
        double radius = 3;
        int numArmorStands = 10;
        List<ArmorStand> armorStands = spawnArmorStands(center, radius, numArmorStands);

        final Iterator<ArmorStand> iterator = armorStands.iterator();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!iterator.hasNext()) {
                    cancel();
                    return;
                }

                ArmorStand armorStand = iterator.next();
                createExplosionTask(armorStand, center);
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }



    /**
     * Spawns armorstands in a circle around the given center location.
     *
     * @param center       The center location of the circle.
     * @param radius       The radius of the circle.
     * @param numArmorStands The number of armor stands to spawn.
     * @return A list of spawned armor stands.
     */
    private List<ArmorStand> spawnArmorStands(Location center, double radius, int numArmorStands) {
        double increment = 2 * Math.PI / numArmorStands;
        List<ArmorStand> armorStands = new ArrayList<>();

        World world = center.getWorld();
        if (world == null) return armorStands;

        for (int i = 0; i < numArmorStands; i++) {
            double angle = i * increment;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            Location location = new Location(world, x, center.getY(), z);

            ArmorStand armorStand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setVisible(true);
            armorStands.add(armorStand);
        }

        return armorStands;
    }



    /**
     * Creates a task to animate an armor stand explosion effect.
     *
     * @param armorStand The armor stand to animate.
     * @param center     The center location for the explosion.
     */
    private void createExplosionTask(ArmorStand armorStand, Location center) {
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks++;
                World world = center.getWorld();
                if (world != null) {
                    Location location = armorStand.getLocation();
                    double y = center.getY() + (ticks * 0.5);
                    location.setY(y);
                    armorStand.teleport(location);

                    if (ticks >= 20) {
                        world.createExplosion(location, 4);
                        armorStand.remove();
                        cancel();
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
}
