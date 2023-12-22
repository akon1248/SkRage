package com.akon.skrage.skript.syntaxes.ProtocolLib;

import com.akon.skrage.event.*;
import com.akon.skrage.skript.syntaxes.ExpressionFactory;
import com.akon.skrage.utils.anvilgui.AnvilGUI;
import com.akon.skrage.utils.signeditor.SignEditor;
import com.akon.skrage.utils.skin.Skin;
import com.akon.skrage.utils.skin.SkinManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.inventory.ItemStack;

public class ProtocolLibSimpleExpressions {

    static {
        ExpressionFactory.registerExpression("ExprAnvilGUI", "[opened] anvil[ ]gui", 0b111, Player.class, AnvilGUI.class, AnvilGUI::getAnvilGUI, null, "プレイヤーに表示されているAnvilGUI");
        ExpressionFactory.registerExpression("ExprAnvilGUIName", "anvil[ ]gui name", 0b011, AnvilGUI.class, String.class, AnvilGUI::getName, null, "AnvilGUIの名前");
        ExpressionFactory.registerExpression("ExprReceivedItem", "receive[d] item[stack]", 0, CreativeItemReceiveEvent.class, ItemStack.class, CreativeItemReceiveEvent::getItemStack, CreativeItemReceiveEvent::setItemStack, "creative item receiveイベントでクライアント側から送られたアイテム");
        ExpressionFactory.registerExpression("ExprShownItem", "show[n] item[stack]", 0, ItemStackShowEvent.class, ItemStack.class, ItemStackShowEvent::getItemStack, ItemStackShowEvent::setItemStack, "show itemイベントで表示されるアイテム");
        ExpressionFactory.registerExpression("ExprSignEditor", "[opened] sign[ ]editor", 0b111, Player.class, SignEditor.class, SignEditor::getSignEditor, null, "プレイヤーに表示されているSignEditor");
        ExpressionFactory.registerExpression("ExprSkin", "[player] skin", 0b111, OfflinePlayer.class, Skin.class, SkinManager::getSkin, null, "プレイヤーが使用しているスキン");
        ExpressionFactory.registerExpression("ExprTrackedEntity", "track[ed] entity", 0, EntityAddTrackingPlayerEvent.class, Entity.class, EntityEvent::getEntity, null);
        ExpressionFactory.registerExpression("ExprUntrackedEntity", "untrack[ed] entity", 0, EntityRemoveTrackingPlayerEvent.class, Entity.class, EntityEvent::getEntity, null);
    }

}
