package Teken.VoteComp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.vexsoftware.votifier.model.*;

public class main extends JavaPlugin implements VoteListener{
	public static Plugin instance;
	String name = "NBVoteComp";
	String textName = "[NBVoteComp] ";
	boolean voteCompRunning = false;
	int peopleLeft = 0;
	int blockID = 0;
	//int blockMeta = 0;
	int number = 0;

	public main(){
		instance = this;
	}

	@Override
	public void onEnable(){
		getLogger().info(name+" has been enabled");
	}

	@Override
	public void onDisable(){
		getLogger().info(name+" has been disabled");
	}

	public void voteMade(Vote vote){
		if(this.voteCompRunning){
			if(this.peopleLeft > 0){
				Player p = Bukkit.getPlayerExact(vote.getUsername());
				Material item = Material.getMaterial(blockID);
				p.getInventory().addItem(new ItemStack(item, number));
				if(p.isOnline())Bukkit.broadcastMessage(textName+vote.getUsername()+" was given "+number+" "+item.name());
				this.peopleLeft--;
				if(this.peopleLeft <= 0)this.voteCompRunning = false;
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player p = (Player) sender;
		if(p.isOp()){
			if(cmd.getName().equalsIgnoreCase("voc") || cmd.getName().equalsIgnoreCase("votecomp")){
				if(!voteCompRunning){
					try{
						this.peopleLeft = Integer.parseInt(args[0]);
					}catch(Exception e){
						p.sendMessage(this.textName+"Problem with number of people");
						return true;
					}
					try{
						this.blockID = Integer.parseInt(args[1]);
					}catch(Exception e){
						p.sendMessage(this.textName+"Problem with block selection");
						return true;
					}
					try{
						this.number = Integer.parseInt(args[2]);
					}catch(Exception e){
						p.sendMessage(this.textName+"Problem with number of blocks/items");
						return true;
					}
					p.sendMessage(textName+"Vote Compertition for "+this.peopleLeft+" people for "+Material.getMaterial(this.blockID).name()+" * "+this.number);
					return true;
				}else{
					p.sendMessage(textName+"A competition is already running");
					return true;
				}
			}else{
				p.sendMessage(textName+"You are not an OP");
				return true;
			}
		}
		return false;
	}
}