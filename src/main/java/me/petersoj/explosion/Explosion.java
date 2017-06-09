package me.petersoj.explosion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.petersoj.controller.BlocksController;

public class Explosion {
	
	private BlocksController blockController;
	
	private ArrayList<BlockState> explodedBlocks;
	private ArrayList<BlockState> dropTypeBlocks;
	private ArrayList<FallingBlock> fallingBlocks;
	private Location center;
	private double radius = 1.0;
	
	public Explosion(BlocksController blockController, List<Block> blockList, Location center){
		this.blockController = blockController;
		
		this.explodedBlocks = new ArrayList<BlockState>();
		this.dropTypeBlocks = new ArrayList<BlockState>();
		this.fallingBlocks = new ArrayList<FallingBlock>();
		this.center = center;
		
		this.setupExplosion(blockList);
	}
	
	// Adds blockStates to list and then sorts
	private void setupExplosion(List<Block> blockList){
		double maxRadius = 1;
		
		for(int i = 0; i < blockList.size(); i++){
			Block block = blockList.get(i);
			
			BlockState state = block.getState();
			
			double distanceSquared = state.getLocation().distanceSquared(center);
			if(distanceSquared > maxRadius){
				maxRadius = distanceSquared;
			}
			
			radius = Math.sqrt(maxRadius);
			
			block.setType(Material.AIR, false);
			
			explodedBlocks.add(state);
		}
		
		// Sort exploded blocks for Y values
		Collections.sort(explodedBlocks, new Comparator<BlockState>() {
			
			// Compares the block Y's cause that's all we care about
			public int compare(BlockState blockState1, BlockState blockState2) {
				return Integer.compare(blockState1.getY(), blockState2.getY());
			}
		});
	}
	
	public void explode(boolean particles, int delay, int interval,int spawnChance, boolean randomRespawn, boolean regenTerrain, boolean spawnFallingBlocks){
		if(spawnFallingBlocks){
			createFallingBlocks(spawnChance);
		}
		if(regenTerrain){
			startBlockRegen(particles, delay, interval, randomRespawn);
		}
	}
	
	private void createFallingBlocks(int spawnChance){
		ThreadLocalRandom random = ThreadLocalRandom.current();
		
		for(BlockState blockState : explodedBlocks){
			if(blockState.getType().isBlock() && random.nextInt(1, 101) <= spawnChance){
				Location fallingBlockLoc = blockState.getLocation();
				
				FallingBlock fallingBlock = (FallingBlock) fallingBlockLoc.getWorld().spawnFallingBlock(fallingBlockLoc, blockState.getData());
				fallingBlock.setVelocity(getNewFallingBlockVector(fallingBlockLoc));
				fallingBlock.setDropItem(false);
				fallingBlock.setHurtEntities(false);
				fallingBlock.setInvulnerable(true);
				fallingBlock.setSilent(true);
				
				fallingBlocks.add(fallingBlock);
			}
		}
	}
	
	private void startBlockRegen(boolean particles, int delay, int interval, boolean randomSpawn){
		new BukkitRunnable() {
			int index = 0;
			ThreadLocalRandom random = ThreadLocalRandom.current();
			
			public void run() {
				if(index >= explodedBlocks.size()){
					
					for(BlockState state : dropTypeBlocks){
						state.update(true, false);
					}
					
					fallingBlocks.clear();
					removeExplosion();
					
					this.cancel();
					return;
				}
				
				if(randomSpawn){
					if(random.nextInt(1, 11) < 7){ // 70 percent chance
						regenBlock(explodedBlocks.get(index++));
					}
				}else{
					regenBlock(explodedBlocks.get(index++));
				}
			}
		}.runTaskTimer(blockController.getPlugin(), delay * 20, interval);
	}
	
	private Vector getNewFallingBlockVector(Location blockLocation){
		Vector blockDirection = blockLocation.toVector().subtract(center.toVector());
		
		double divide = radius / blockDirection.lengthSquared();
		blockDirection.divide(new Vector(divide, divide, divide));
		
		blockDirection.setY(Math.abs(blockDirection.getY()));
		
		return blockDirection.normalize();
	}
	
	@SuppressWarnings("deprecation")
	private void regenBlock(BlockState blockState){
		if(isDropType(blockState.getType())){
			dropTypeBlocks.add(blockState);
		}else{
			Location stateLocation = blockState.getLocation();
			blockState.update(true, false);
			stateLocation.getWorld().playEffect(stateLocation, Effect.STEP_SOUND, blockState.getTypeId());
		}
	}
	
	private boolean isDropType(Material type){
		for(Material dropType : DROP_TYPES){
			if(dropType == type){
				return true;
			}
		}
		return false;
	}
	
	// Method needed for outside reference of Runnable
	private void removeExplosion(){
		blockController.getExplosions().remove(this);
	}
	
	public ArrayList<FallingBlock> getFallingBlocks(){
		return fallingBlocks;
	}
	
	
	private static final Material[] DROP_TYPES = {
			Material.SAPLING,
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
