package com.akon.skrage.utils.oldaiskeleton;

import com.akon.skrage.SkRage;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;

//OldAIが有効だった場合腕が常に上がっているように見せる
public class OldAISkeletonListener extends PacketAdapter {

	public static final OldAISkeletonListener INSTANCE = new OldAISkeletonListener();

	private OldAISkeletonListener() {
		super(SkRage.getInstance(), PacketType.Play.Server.ENTITY_METADATA);
	}

	@Override
	public void onPacketSending(PacketEvent e) {
		Entity maybeSkeleton = e.getPacket().getEntityModifier(e).read(0);
		if (e.getPacketType() == PacketType.Play.Server.ENTITY_METADATA && maybeSkeleton instanceof Skeleton && OldAISkeletonManager.isOldAI((Skeleton)maybeSkeleton)) {
			WrappedDataWatcher dataWatcher = new WrappedDataWatcher(e.getPacket().getWatchableCollectionModifier().read(0));
			if (dataWatcher.hasIndex(12)) {
				dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(12, WrappedDataWatcher.Registry.get(Boolean.class)), true);
				e.getPacket().getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());
			}
		}
	}

}
