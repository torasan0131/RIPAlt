
package com.comphenix.packetwrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

public class WrapperLoginClientStart extends AbstractPacket {
	public static final PacketType TYPE = PacketType.Login.Client.START;

	public WrapperLoginClientStart() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
	}

	public WrapperLoginClientStart(PacketContainer packet) {
		super(packet, TYPE);
	}

	public WrappedGameProfile getProfile() {
		return handle.getGameProfiles().read(0);
	}
	public void setProfile(WrappedGameProfile value) {
		handle.getGameProfiles().write(0, value);
	}
}
