package com.akon.skrage.utils.skin;

import com.akon.skrage.utils.ReflectionUtil;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Data
public class Skin {

	public static final Skin EMPTY = new Skin(null, null);

	private final String value;
	private final String signature;

	public WrappedGameProfile toGameProfile(UUID uuid, String name) {
		WrappedGameProfile profile = new WrappedGameProfile(uuid, name);
		if (this.value != null && this.signature != null) {
			profile.getProperties().put("textures", new WrappedSignedProperty("textures", this.value, this.signature));
		}
		return profile;
	}

	@Nullable
	public static Skin fromGameProfile(WrappedGameProfile profile) {
		for (WrappedSignedProperty property: profile.getProperties().get("textures")) {
			return new Skin(property.getValue(), property.getSignature());
		}
		return null;
	}

	public ItemStack toPlayerHead() {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta meta = (SkullMeta)head.getItemMeta();
		WrappedGameProfile profile = this.toGameProfile(UUID.nameUUIDFromBytes(this.value.getBytes(StandardCharsets.UTF_8)), StringUtils.EMPTY);
		ReflectionUtil.DEFAULT.setField(meta, "profile", profile.getHandle());
		head.setItemMeta(meta);
		return head;
	}

}
