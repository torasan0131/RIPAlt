package net.disaapse.ripalt.bukkit;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;


public final class BukkitRIPalt extends JavaPlugin {

	public static final String PERMISSION_RELOAD = "ripalt.reload";
	public static final String PERMISSION_BYPASS = "ripalt.bypass";

	private static final Logger LOG = Logger.getLogger("Minecraft");

	private ProtocolManager protocolManager;
	private Manager manager;
	private boolean debug;
	private boolean bungeecord;

	public void onLoad() {
		// Spigot : config
		this.initBungeecord();

		// ProtocolLib
		this.protocolManager = ProtocolLibrary.getProtocolManager();
		if (this.protocolManager == null) {
			this.warn("ProtocolLib is not enabled");
			this.setEnabled(false);
			return;
		}
	}

	public void onEnable() {
		// Commands
		Commands command = new Commands(this);
		this.getCommand("RIPalt").setExecutor(command);
		this.getCommand("RIPalt").setTabCompleter(command);

		if (!this.isEnabled()) return;

		// Configs
		this.initConfig();

		if (this.isBungee()) {
			this.info("Mode BungeeCord : Enable");
		} else if (!this.getServer().getOnlineMode()) {
			this.warn("####################################################");
			this.warn("Plugin disabled : This server is in offline mode !!!");
			this.warn("####################################################");
			this.setEnabled(false);
			return;
		}

		try {
			if (!this.isBungee()) {
				this.manager = new ManagerBukkit(this);
			} else {
				this.manager = new ManagerBungee(this);
			}
			this.getServer().getPluginManager().registerEvents(this.manager, this);
		} catch (Exception e) {
			this.warn("The server version is incompatible");
			this.setEnabled(false);
			e.printStackTrace();
			return;
		}
	}

	public void onReload() {
		if (!this.isEnabled()) return;

		// Configs
		this.initConfig();
		this.manager.reload();
		this.info("Reloaded");
	}

	/*
	 * Initialization
	 */

	public void initConfig() {
		this.saveDefaultConfig();
		this.reloadConfig();

		this.bungeecord = this.isBungee() || this.getConfig().getBoolean("bungeecord");
		this.getConfig().set("bungeecord", this.isBungee());
		this.getConfig().addDefault("commands", Arrays.asList("kick <player> &cRIPalt"));

		this.getConfig().options().copyDefaults(true);
		this.getConfig().options().copyHeader(true);
		this.saveConfig();
		this.reloadConfig();

		this.debug = this.getConfig().getBoolean("debug");
	}

	public void initBungeecord() {
		try {
			Field field = Class.forName("org.spigotmc.SpigotConfig").getDeclaredField("bungee");
			field.setAccessible(true);
			this.bungeecord = field.getBoolean(null);
		} catch (Exception e) {
			this.bungeecord = false;
		}
	}


	/*
	 * Log
	 */

	public void info(String message) {
		LOG.info("[" + this.getName() + "] " + message);
	}

	public void warn(String message) {
		LOG.warning("[" + this.getName() + "] " + message);
	}

	public void debug(String message) {
		if (this.debug) LOG.info("[" + this.getName() + "] " + message);
	}

	/*
	 * Accesseurs
	 */

	public boolean isDebug() {
		return this.debug;
	}

	public boolean isBungee() {
		return this.bungeecord;
	}

	public ProtocolManager getProtocolManager() {
		return this.protocolManager;
	}
}
