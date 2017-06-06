package me.petersoj.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class Explosion {
	
	private ArrayList<BlockState> explodedBlocks;
	private Location center;
	
	public Explosion(List<Block> blockList, Location center){
		this.explodedBlocks = new ArrayList<BlockState>();
		this.center = center;
		
		this.sortExplodedBlocks(blockList);
	}
	
	// Adds blockStates to list and then sorts
	private void sortExplodedBlocks(List<Block> blockList){
		for(int i = 0; i < blockList.size(); i++){
			explodedBlocks.add(blockList.get(i).getState());
		}
		
		Collections.sort(explodedBlocks, new Comparator<BlockState>() {
			
			// Compares the block Y's cause that's all we care about
			public int compare(BlockState blockState1, BlockState blockState2) {
				return Integer.compare(blockState1.getY(), blockState2.getY());
			}
		});
	}
	
	public void explode(boolean particles, int delay, int interval, boolean randomRespawn, boolean spawnFallingBlocks){
		removeAndCreateFallingBlocks(particles, spawnFallingBlocks);
		startBlockRegen(particles, delay, interval, randomRespawn);
	}
	
	private void removeAndCreateFallingBlocks(boolean particles, boolean spawnFallingBlocks){
		
	}
	
	private void startBlockRegen(boolean particles, int delay, int interval, boolean randomSpawn){
		
	}
	
	
	private static Material[] dropTypes = {
			Material.SAPLING,
			Material.GRASS,
			Material.LONG_GRASS,
			Material.DEAD_BUSH,
			Material.YELLOW_FLOWER,
			Material.RED_ROSE,
			Material.BROWN_MUSHROOM,
			Material.RED_MUSHROOM,
			Material.TORCH,
			Material.LADDER,
			Material.SNOW,
			Material.VINE,
			Material.WATER_LILY,
			Material.CARPET,
			Material.DOUBLE_PLANT,
			Material.PAINTING,
			Material.ITEM_FRAME,
			Material.SIGN,
			Material.FLOWER_POT,
			Material.LEVER,
			Material.STONE_PLATE,
			Material.WOOD_PLATE,
			Material.REDSTONE_TORCH_ON,
			Material.REDSTONE_TORCH_OFF,
			Material.STONE_BUTTON,
			Material.TRAP_DOOR,
			Material.TRIPWIRE_HOOK,
			Material.WOOD_BUTTON,
			Material.GOLD_PLATE,
			Material.IRON_PLATE,
			Material.IRON_TRAPDOOR,
			Material.IRON_DOOR_BLOCK,
			Material.IRON_DOOR,
			Material.WOODEN_DOOR,
			Material.WOOD_DOOR,
			Material.ACACIA_DOOR,
			Material.BIRCH_DOOR,
			Material.DARK_OAK_DOOR,
			Material.JUNGLE_DOOR,
			Material.SPRUCE_DOOR,
			Material.REDSTONE,
			Material.REDSTONE_COMPARATOR,
			Material.REDSTONE_COMPARATOR_ON,
			Material.REDSTONE_COMPARATOR_OFF,
			Material.REDSTONE_WIRE,
			Material.RAILS,
			Material.ACTIVATOR_RAIL,
			Material.POWERED_RAIL,
			Material.DETECTOR_RAIL,
	};
}
