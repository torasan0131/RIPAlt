
package com.comphenix.packetwrapper;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;

public abstract class AbstractPacket {
	// The packet we will be modifying
	protected PacketContainer handle;

	protected AbstractPacket(PacketContainer handle, PacketType type) {
		// Make sure we're given a valid packet
		if (handle == null)
			throw new IllegalArgumentException("Packet handle cannot be NULL.");
		if (!Objects.equal(handle.getType(), type))
			throw new IllegalArgumentException(handle.getHandle()
					+ " is not a packet of type " + type);

		this.handle = handle;
	}

	public PacketContainer getHandle() {
		return handle;
	}

	public void sendPacket(Player receiver) {
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(receiver,
					getHandle());
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet.", e);
		}
	}

	@Deprecated
	public void recievePacket(Player sender) {
		try {
			ProtocolLibrary.getProtocolManager().recieveClientPacket(sender,
					getHandle());
		} catch (Exception e) {
			throw new RuntimeException("Cannot recieve packet.", e);
		}
	}

	public void receivePacket(Player sender) {
		try {
			ProtocolLibrary.getProtocolManager().recieveClientPacket(sender,
					getHandle());
		} catch (Exception e) {
			throw new RuntimeException("Cannot recieve packet.", e);
		}
	}
}
