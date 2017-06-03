package me.petersoj.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import me.petersoj.FallingBlocks;
import me.petersoj.model.Explosion;

public class BlocksController {
	
	private FallingBlocks plugin;
	
	private ArrayList<UUID> fallingBlocks;
	private boolean particles;
	private int delay;
	private int interval;
	private boolean randomRespawn;
	private boolean drops;
	private boolean spawnFallingBlocks;
	
	public BlocksController(FallingBlocks plugin){
		this.plugin = plugin;
		this.fallingBlocks = new ArrayList<UUID>();
	}
	
	public void start(){
		
		plugin.saveDefaultConfig(); // Copys default config if it does not exist
		
		FileConfiguration config = plugin.getConfig();
		this.particles = config.getBoolean("particles");
		this.delay = config.getInt("delay");
		this.interval = config.getInt("interval");
		this.randomRespawn = config.getBoolean("random respawn rate");
		this.drops = config.getBoolean("drops");
		this.spawnFallingBlocks = config.getBoolean("falling blocks");
	}
	
	public void createExplosion(List<Block> blockList, Location center){
		Explosion explosion = new Explosion(blockList, center);
		explosion.explode(particles, delay, interval, randomRespawn, spawnFallingBlocks);
	}
	
	public ArrayList<UUID> getFallingBlocks(){
		return fallingBlocks;
	}
	
	public boolean doDrops(){
		return drops;
	}
}
