package net.disaapse.ripalt.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.disaapse.ripalt.bungee.BungeeRIPalt;

public class ManagerBungee extends Manager implements PluginMessageListener {

	public ManagerBungee(BukkitRIPalt plugin) {
		super(plugin);

		this.listener();
	}

	public void listener() {
		this.plugin.getServer().getMessenger().registerOutgoingPluginChannel(this.plugin, "BungeeCord");
	    this.plugin.getServer().getMessenger().registerIncomingPluginChannel(this.plugin, "BungeeCord", this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals(BungeeRIPalt.CHANNEL)) return;

		ByteArrayDataInput in = ByteStreams.newDataInput(message);
	    String subchannel = in.readUTF();
	    if (subchannel.equals(BungeeRIPalt.SUBCHANNEL)) {
	    	final String name = in.readUTF();
	    	final boolean result = in.readBoolean();

	    	// S'il est déjà connu
	    	if (this.caches.getIfPresent(name) != null) return;

	    	this.caches.put(name, result);
	    	// Si les 2 IP correspondent
	    	if (result) {
				this.plugin.debug("The player " + name + " doesn't use alt account.");

			// Les 2 IP ne correspondent pas
			} else {
				// Si le joueur est déjà connecté
				if (this.plugin.getServer().getPlayer(name) != null) {
					this.executeCommands(name, 100);
				}
			}
	    }

	}
}
