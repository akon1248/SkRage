package com.akon.skrage.utils.signeditor;

import com.akon.skrage.SkRage;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

public class SignEditorListener extends PacketAdapter implements Listener {

    public static final SignEditorListener INSTANCE = new SignEditorListener();

    private SignEditorListener() {
        super(SkRage.getInstance(), PacketType.Play.Client.UPDATE_SIGN, PacketType.Play.Server.CUSTOM_PAYLOAD, PacketType.Play.Server.OPEN_SIGN_EDITOR, PacketType.Play.Server.OPEN_WINDOW, PacketType.Play.Server.RESPAWN);
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        if (e.getPacketType() != PacketType.Play.Server.CUSTOM_PAYLOAD || e.getPacket().getStrings().read(0).equals("MC|BOpen")) {
            this.closeSignEditor(e.getPlayer());
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent e) {
        Optional.ofNullable(SignEditor.getSignEditor(e.getPlayer())).ifPresent(signEditor -> Bukkit.getScheduler().runTask(SkRage.getInstance(), () -> signEditor.done(e.getPacket().getStringArrays().read(0))));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        this.closeSignEditor(e.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        this.closeSignEditor(e.getEntity());
    }

    private void closeSignEditor(Player player) {
        Optional.ofNullable(SignEditor.getSignEditor(player)).ifPresent(SignEditor::onClose);
    }

}
