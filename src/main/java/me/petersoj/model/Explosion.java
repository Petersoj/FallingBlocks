package me.petersoj.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Location;
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
}
