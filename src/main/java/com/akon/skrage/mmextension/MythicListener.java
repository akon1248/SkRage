package com.akon.skrage.mmextension;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicListener implements Listener {

    @EventHandler
    public void onLoadMechanics(MythicMechanicLoadEvent e) {
        if (e.getMechanicName().equalsIgnoreCase("skript_function")) {
            e.register(new SkriptFunctionMechanic(e.getMechanicName(), e.getConfig()));
        }
    }
}
