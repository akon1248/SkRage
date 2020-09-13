package com.akon.skrage.skript.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.shampaggon.crackshot.CSDirector;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Description({"アイテムからCrackShotの武器としての登録名を取得します","値が返ってきたかどうかでアイテムがCrackShotの武器かどうかの判別も可能です"})
public class ExprCSWeaponName extends SimpleExpression<String> {
    
    static {
        if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) Skript.registerExpression(ExprCSWeaponName.class, String.class, ExpressionType.COMBINED, "(cs|crackshot) weapon (name|title) (from|of) %item%");
    }
    
    private Expression<ItemStack> item;
    
    @Override
    protected String[] get(Event e) {
        if (this.item != null) {
            return new String[]{getWeaponTitle(this.item.getSingle(e))};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.item = (Expression<ItemStack>)exprs[0];
        return true;
    }
    
    private static String getWeaponTitle(ItemStack item)  {
        CSDirector csDirector = (CSDirector)Bukkit.getPluginManager().getPlugin("CrackShot"); 
        String weaponName = null;
        if (item == null) {
            return null;
        } else {
            String parentNode;
            if (csDirector.itemIsSafe(item)) {
                parentNode = csDirector.isSkipNameItem(item);
                if (parentNode == null) {
                    parentNode = csDirector.parentlist.get(csDirector.getPureName(item.getItemMeta().getDisplayName()));
                }

                if (parentNode != null) {
                    if (item.getItemMeta().getDisplayName().contains(String.valueOf('▶'))) {
                        weaponName = csDirector.getAttachment(parentNode, item)[1];
                    } else {
                        weaponName = parentNode;
                    }
                }
            }
            return weaponName;
        }
    }
    
}
