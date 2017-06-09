package me.petersoj.controller;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import me.petersoj.FallingBlocks;
import me.petersoj.explosion.Explosion;

public class BlocksController {
	
	private FallingBlocks plugin;
	
	private ArrayList<Explosion> explosions;
	private boolean particles;
	private int delay;
	private int interval;
	private int spawnChance;
	private boolean randomRespawn;
	private boolean drops;
	private boolean regenTerrain;
	private boolean spawnFallingBlocks;
	
	public BlocksController(FallingBlocks plugin){
		this.plugin = plugin;
		this.explosions = new ArrayList<Explosion>();
	}
	
	public void start(){
		
		plugin.saveDefaultConfig(); // Copys default config if it does not exist
		
		FileConfiguration config = plugin.getConfig();
		
		this.particles = config.getBoolean("particles", true);
		this.delay = config.getInt("delay", 5);
		this.interval = config.getInt("interval", 20);
		this.spawnChance = config.getInt("spawn chance", 30);
		this.randomRespawn = config.getBoolean("random respawn rate", true);
		this.drops = config.getBoolean("drops", true);
		this.regenTerrain = config.getBoolean("regen terrain", true);
		this.spawnFallingBlocks = config.getBoolean("falling blocks", true);
	}
	
	public void createExplosion(List<Block> blockList, Location center){
		Explosion explosion = new Explosion(this, blockList, center);
		explosions.add(explosion);
		explosion.explode(particles, delay, interval, spawnChance, randomRespawn, regenTerrain, spawnFallingBlocks);
	}
	
	public FallingBlocks getPlugin(){
		return plugin;
	}
	
	public ArrayList<Explosion> getExplosions(){
		return explosions;
	}
	
	public boolean doDrops(){
		return drops;
	}
}
