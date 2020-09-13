package com.akon.skrage.utils.signeditor;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class SignEditor {

    private static final HashMap<UUID, SignEditor> SIGN_EDITOR_MAP = Maps.newHashMap();

    private Location fakeSignLoc;
    @Getter
    private final String name;
    private final String[] initLines = new String[4];
    @Getter
    private Player viewer;

    public SignEditor(String name, String line1, String line2, String line3, String line4) {
        this.name = name;
        this.initLines[0] = line1 == null ? StringUtils.EMPTY : line1;
        this.initLines[1] = line2 == null ? StringUtils.EMPTY : line2;
        this.initLines[2] = line3 == null ? StringUtils.EMPTY : line3;
        this.initLines[3] = line4 == null ? StringUtils.EMPTY : line4;
    }

    public String[] getInitLines() {
        String[] arr = new String[4];
        System.arraycopy(this.initLines, 0, arr, 0, 4);
        return arr;
    }

    public String getInitLine(int line) {
        return this.initLines[line];
    }

    public boolean open(Player player) {
        if (this.viewer != null) {
            throw new IllegalStateException("既に他のプレイヤーにGUIを表示しています");
        }
        SignEditorOpenEvent event = new SignEditorOpenEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            this.fakeSignLoc = player.getLocation().getBlock().getLocation();
            this.fakeSignLoc.setY(255);
            player.sendBlockChange(this.fakeSignLoc, Material.SIGN_POST, (byte)0);
            PacketContainer signData = new PacketContainer(PacketType.Play.Server.TILE_ENTITY_DATA);
            BlockPosition pos = new BlockPosition(this.fakeSignLoc.toVector());
            signData.getBlockPositionModifier().write(0, pos);
            signData.getIntegers().write(0, 9);
            signData.getNbtModifier().writeDefaults();
            NbtCompound compound = (NbtCompound)signData.getNbtModifier().read(0);
            for (int i = 0; i < 4; i++) {
                compound.put("Text" + (i + 1), WrappedChatComponent.fromText(this.initLines[i]).getJson());
            }
            compound.put("x", pos.getX());
            compound.put("y", pos.getY());
            compound.put("z", pos.getZ());
            PacketContainer openEditor = new PacketContainer(PacketType.Play.Server.OPEN_SIGN_EDITOR);
            openEditor.getBlockPositionModifier().write(0, pos);
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, signData);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, openEditor);
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
            SIGN_EDITOR_MAP.put(player.getUniqueId(), this);
            this.viewer = player;
            return true;
        }
        return false;
    }

    public void onClose() {
        Optional.ofNullable(this.viewer).orElseThrow(() -> new IllegalStateException("GUIを表示しているプレイヤーがいません"));
        SIGN_EDITOR_MAP.remove(this.getViewer().getUniqueId());
        if (this.viewer.getWorld() == this.fakeSignLoc.getWorld()) {
            this.fakeSignLoc.getBlock().getState().update(true, false);
        }
        this.viewer = null;
    }

    public void done(String[] strArr) {
        String[] lines = new String[4];
        for (int i = 0; i < 4; i++) {
            lines[i] = StringUtils.EMPTY;
        }
        System.arraycopy(strArr, 0, lines, 0, strArr.length);
        SignEditorDoneEvent event = new SignEditorDoneEvent(this.getViewer(), this, lines[0], lines[1], lines[2], lines[3]);
        Bukkit.getPluginManager().callEvent(event);
        this.onClose();
    }

    @Nullable
    public static SignEditor getSignEditor(Player player) {
        return SIGN_EDITOR_MAP.get(player.getUniqueId());
    }

}
