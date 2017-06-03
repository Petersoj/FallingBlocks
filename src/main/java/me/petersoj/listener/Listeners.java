package me.petersoj.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.petersoj.FallingBlocks;
import me.petersoj.controller.BlocksController;

public class Listeners implements Listener {
	
	private FallingBlocks plugin;
	
	public Listeners(FallingBlocks plugin){
		this.plugin = plugin;
	}
	
	public void start(){
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true) // Happens very last to check if cancelled
	public void onEntityExplode(EntityExplodeEvent e){
		BlocksController controller = plugin.getBlocksController();
		if(!controller.doDrops()){
			e.setYield(0);
		}
		
		controller.createExplosion(e.blockList(), e.getLocation());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true) // Happens very last to check if cancelled
	public void onBlockExplode(BlockExplodeEvent e){
		BlocksController controller = plugin.getBlocksController();
		if(!controller.doDrops()){
			e.setYield(0);
		}
		
		controller.createExplosion(e.blockList(), e.getBlock().getLocation());
	}

}
