package com.akon.skrage.utils.spectator;

import com.akon.skrage.SkRage;
import lombok.experimental.UtilityClass;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

@UtilityClass
public class SpectatorManager {

    public boolean setSpectator(Player player, boolean flag) {
        GameMode previousMode = getPreviousGameMode(player);
        if ((previousMode != null) == flag) {
            return false;
        }
        if (flag) {
            GameMode mode = player.getGameMode();
            player.setMetadata("Spectator", new FixedMetadataValue(SkRage.getInstance(), mode));
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(player.getLocation().add(0, 0.01, 0));
        } else {
            player.removeMetadata("Spectator", SkRage.getInstance());
            player.setGameMode(previousMode);
        }
        return true;
    }

    private GameMode getPreviousGameMode(Player player) {
        return player.getMetadata("Spectator")
            .stream()
            .filter(metadata -> metadata.getOwningPlugin() == SkRage.getInstance())
            .map(MetadataValue::value)
            .filter(GameMode.class::isInstance)
            .findAny()
            .map(GameMode.class::cast)
            .orElse(null);
    }

    public boolean isSpectator(Player player) {
        return getPreviousGameMode(player) != null;
    }
}
