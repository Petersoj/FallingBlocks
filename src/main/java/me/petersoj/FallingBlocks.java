package me.petersoj;

import org.bukkit.plugin.java.JavaPlugin;

import me.petersoj.controller.BlocksController;
import me.petersoj.listener.Listeners;

public class FallingBlocks extends JavaPlugin {
	
	private BlocksController blocksController;
	private Listeners listeners;
	
	@Override
	public void onEnable() {
		this.blocksController = new BlocksController(this);
		this.listeners = new Listeners(this);
		
		this.blocksController.start();
		this.listeners.start();
	}

	public BlocksController getBlocksController() {
		return blocksController;
	}

	public Listeners getListeners() {
		return listeners;
	}
}
