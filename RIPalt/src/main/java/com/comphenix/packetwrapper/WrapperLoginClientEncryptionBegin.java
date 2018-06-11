
package com.comphenix.packetwrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperLoginClientEncryptionBegin extends AbstractPacket {
	public static final PacketType TYPE =
			PacketType.Login.Client.ENCRYPTION_BEGIN;

	public WrapperLoginClientEncryptionBegin() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
	}

	public WrapperLoginClientEncryptionBegin(PacketContainer packet) {
		super(packet, TYPE);
	}

	public byte[] getSharedSecret() {
		return handle.getByteArrays().read(0);
	}

	public void setSharedSecret(byte[] value) {
		handle.getByteArrays().write(0, value);
	}

	public byte[] getVerifyToken() {
		return handle.getByteArrays().read(1);
	}

	public void setVerifyToken(byte[] value) {
		handle.getByteArrays().write(1, value);
	}
}
