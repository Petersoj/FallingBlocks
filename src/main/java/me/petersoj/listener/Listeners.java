package me.petersoj.listener;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.petersoj.FallingBlocks;
import me.petersoj.controller.BlocksController;
import me.petersoj.explosion.Explosion;

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

	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true) // Happens very last to check if cancelled
	public void onEntityChangeBlock(EntityChangeBlockEvent e){
		Block block = e.getBlock();
		
		for(Explosion explosion : plugin.getBlocksController().getExplosions()){
			if(explosion.getFallingBlocks().contains(e.getEntity())){
				explosion.getFallingBlocks().remove(e.getEntity());
				block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, ((FallingBlock)e.getEntity()).getBlockId());
				e.setCancelled(true);
			}
		}
	}
}
