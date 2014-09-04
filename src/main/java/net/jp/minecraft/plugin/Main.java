package net.jp.minecraft.plugin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * よく使うコマンドやアクションをチェスト画面を利用して操作しよう！ってプラグイン
 * AdminInventoryTools
 * @author syokkendesuyo
 */


public class Main extends JavaPlugin implements Listener {


	/**
	 * プラグインが有効になったときに呼び出されるメソッド
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String [] args){
		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("ait")){
			if ((sender instanceof Player) || (sender instanceof ConsoleCommandSender)){
				if(player.hasPermission("ait.give")){

					ItemStack item0 = new ItemStack(Material.STICK);
					ItemMeta itemmeta0 = item0.getItemMeta();
					itemmeta0.setDisplayName(ChatColor.GOLD + "AdminInventoryTools");
					itemmeta0.setLore(Arrays.asList(ChatColor.YELLOW + "魔法の杖:", ChatColor.WHITE + "この杖を空気に向かってクリックすると", ChatColor.WHITE + "スバラシイ画面が現れるだろう。"));
					item0.setItemMeta(itemmeta0);
					player.getInventory().addItem(item0);


					player.sendMessage(ChatColor.AQUA + "[情報]AdminInventoryToolsを与えました。");
					return true;
				}
			}
		}
		return false;
	}


	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event){
		final Player p = event.getPlayer();
		if(p.hasPermission("ait.open")){
			if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "AdminInventoryTools")){
				if(event.getAction() == Action.LEFT_CLICK_AIR){
					if(p.getItemInHand().getType() == Material.STICK){
						//ここの内容はインベントリGUIのアイテム設定です

						//インベントリGUI1つ目の設定
						ItemStack item0 = new ItemStack(Material.WATCH);
						ItemMeta itemmeta0 = item0.getItemMeta();
						itemmeta0.setDisplayName(ChatColor.GOLD  + "時間を早朝にする");
						itemmeta0.setLore(Arrays.asList(ChatColor.YELLOW + "コマンド:", ChatColor.WHITE + "/time set 0と同様です。"));
						item0.setItemMeta(itemmeta0);


						//インベントリGUI2つ目の設定
						ItemStack item1 = new ItemStack(Material.TORCH);
						ItemMeta itemmeta1 = item1.getItemMeta();
						itemmeta1.setDisplayName(ChatColor.GOLD  + "時間を夜にする");
						itemmeta1.setLore(Arrays.asList(ChatColor.YELLOW + "コマンド:", ChatColor.WHITE + "/time set 12500と同様です。"));
						item1.setItemMeta(itemmeta1);

						//インベントリGUI3つ目の設定
						ItemStack item2 = new ItemStack(Material.BOOK_AND_QUILL);
						ItemMeta itemmeta2 = item2.getItemMeta();
						itemmeta2.setDisplayName(ChatColor.GOLD  + "ゲームモード変更");
						itemmeta2.setLore(Arrays.asList(ChatColor.YELLOW + "ゲームモードをトグルします。", ChatColor.WHITE + "クリエイティブ、サバイバル以外であった" , ChatColor.WHITE + "場合はサバイバルになります。"));
						item2.setItemMeta(itemmeta2);

						//インベントリGUI4つ目の設定
						ItemStack item3 = new ItemStack(Material.COBBLE_WALL);
						ItemMeta itemmeta3 = item3.getItemMeta();
						itemmeta3.setDisplayName(ChatColor.GOLD  + "ホワイトリスト変更");
						itemmeta3.setLore(Arrays.asList(ChatColor.YELLOW + "ホワイトリストをトグルします。", ChatColor.WHITE + "コマンド:", ChatColor.WHITE + "/whitelist on・offと同様です"));
						item3.setItemMeta(itemmeta3);

						//インベントリGUI5つ目の設定
						ItemStack item4 = new ItemStack(Material.APPLE);
						ItemMeta itemmeta4 = item4.getItemMeta();
						itemmeta4.setDisplayName(ChatColor.GOLD  + "オペレータ権限変更");
						itemmeta4.setLore(Arrays.asList(ChatColor.YELLOW + "オペレータ権限をトグルします。", ChatColor.WHITE + "ait.openのパーミッションを保有していない場合", ChatColor.WHITE + "この画面を再度開けなくなります。"));
						item4.setItemMeta(itemmeta4);

						//インベントリを閉じるだけの操作
						ItemStack close = new ItemStack(Material.STICK);
						ItemMeta closemeta = close.getItemMeta();
						closemeta.setDisplayName(ChatColor.GOLD  + "画面を閉じる");
						closemeta.setLore(Arrays.asList(ChatColor.YELLOW + "この画面を閉じます。", ChatColor.WHITE + "作者:syokkendesuyo", ChatColor.WHITE + "ご利用ありがとうございます。"));
						close.setItemMeta(closemeta);

						//インベントリに配置する
						Inventory inv = Bukkit.createInventory(p, 9 ," □Admin Menu□ ");
						inv.setItem(0, item0);
						inv.setItem(1, item1);
						inv.setItem(2, item2);
						inv.setItem(3, item3);
						inv.setItem(4, item4);
						inv.setItem(8, close);

						//インベントリを開ける
						p.openInventory(inv);
					}
				}
			}
		}
	}




	@EventHandler
	public void inventoryclick(InventoryClickEvent event){
		if (event.getInventory().getName().equalsIgnoreCase(" □Admin Menu□ ")){
			if (event.getRawSlot() < 54 && event.getRawSlot() > -1){
				Player player = (Player) event.getWhoClicked();
				World world = player.getWorld();
				if(event.isRightClick() || event.isLeftClick()){
					if(event.getRawSlot()==0){
						world.setTime(0);
						player.sendMessage(ChatColor.AQUA + "[情報]時間を0に設定しました。");
						player.closeInventory();
					}

					if(event.getRawSlot()==1){
						world.setTime(12500);
						player.sendMessage(ChatColor.AQUA + "[情報]時間を12500に設定しました。");
						player.closeInventory();
					}

					if(event.getRawSlot()==2){
						if(player.getGameMode() == GameMode.SURVIVAL){
							player.setGameMode(GameMode.CREATIVE);
							player.sendMessage(ChatColor.AQUA + "[情報]ゲームモードをクリエイティブにしました。");
						}
						else if(player.getGameMode() == GameMode.CREATIVE){
							player.setGameMode(GameMode.SURVIVAL);
							player.sendMessage(ChatColor.AQUA + "[情報]ゲームモードをサバイバルにしました。");
						}
						//サバイバル・クリエイティブでないゲームモードはあまり使わないよね？ってことで他はとりあえずサバイバルに設定
						else{
							player.setGameMode(GameMode.SURVIVAL);
							player.sendMessage(ChatColor.AQUA + "[情報]ゲームモードをサバイバルにしました。");
						}
						player.closeInventory();
					}

					if(event.getRawSlot()==3){
						if(Bukkit.hasWhitelist()==true){
							Bukkit.setWhitelist(false);
							player.sendMessage(ChatColor.AQUA + "[情報]ホワイトリストを無効化しました。");
						}
						else if(Bukkit.hasWhitelist()==false){
							player.sendMessage(ChatColor.AQUA + "[情報]ホワイトリストを有効化しました。");
							Bukkit.setWhitelist(true);
						}
						else{
							player.sendMessage(ChatColor.AQUA + "[情報]不明なエラーが発生しました。メインクラス:155");
						}
						player.closeInventory();
					}

					if(event.getRawSlot()==4){
						if(player.isOp()==true){
							player.sendMessage(ChatColor.AQUA + "[情報]オペレータ権限を剥奪しました。");
							player.setOp(false);
						}
						else if(player.isOp()==false){
							player.sendMessage(ChatColor.AQUA + "[情報]オペレータ権限を取得しました。");
							player.setOp(true);
						}
						player.closeInventory();
					}

					if(event.getRawSlot()==8){
						player.closeInventory();
					}

					event.setCancelled(true);
				}
			}
		}
	}
}
