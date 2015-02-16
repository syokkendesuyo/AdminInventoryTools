package net.jp.minecraft.plugin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * よく使うコマンドやアクションをチェスト画面を利用して操作しよう！ってプラグイン
 * AdminInventoryTools
 * @author syokkendesuyo
 */


public class AdminInventoryTools extends JavaPlugin implements Listener {


	/**
	 * プラグインが有効になったときに呼び出されるメソッド
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	//コマンドで杖を渡す処理
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ait")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please excute this /ait command on a game!");
				sender.sendMessage("/ait コマンドはゲーム内で実行してください。");
			}
			else {
				Player player = (Player) sender;
				if(player.hasPermission("ait.give")||player.isOp()){
					//palyerがait.giveまたはopであれば杖を渡す

					ItemStack item = new ItemStack(Material.STICK);
					ItemMeta itemmeta = item.getItemMeta();
					itemmeta.setDisplayName(ChatColor.GOLD + "AdminInventoryTools");
					itemmeta.setLore(Arrays.asList(ChatColor.YELLOW + "魔法の杖:", ChatColor.WHITE + "この杖を空気に向かってクリックすると", ChatColor.WHITE + "スバラシイ画面が現れるだろう。"));
					itemmeta.addEnchant(Enchantment.SILK_TOUCH , 1, true);
					item.setItemMeta(itemmeta);
					player.getInventory().addItem(item);

					player.sendMessage(ChatColor.AQUA + "[情報]AdminInventoryToolsを与えました。");
				}
			}
			return true;
		}

		else if (cmd.getName().equalsIgnoreCase("skull")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please excute this /skull command on a game!");
				sender.sendMessage("/skull コマンドはゲーム内で実行してください。");
			}
			else {
				Player player = (Player) sender;
				if(player.hasPermission("skull.give")||player.isOp()){
					//palyerがskull.giveまたはopであればここを抜ける
					if(args.length == 0){
						sender.sendMessage(ChatColor.AQUA + "[情報]/skull <player> でプレイヤーの頭を取得できます。");
					}
					else{
					 ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1);
					 SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
					 skull.setDurability((short) 3);
					 skullMeta.setDisplayName(ChatColor.GOLD + args[0] + "の頭");
					 skullMeta.setOwner(args[0]);
					 skull.setItemMeta(skullMeta);

					 skull.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
					 player.getInventory().addItem(skull);

					player.sendMessage(ChatColor.AQUA + "[情報]"+ args[0] + "の頭を与えました。");
					}
				}
			}
			return true;
		}
		return false;
	}


	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event){
		final Player p = event.getPlayer();
		Block block = event.getClickedBlock();
		if(p.hasPermission("ait.open")||p.isOp()){
			if(p.getItemInHand().getType()==Material.AIR){
				//何でもなかった場合無視
			}
			else if(p.getItemInHand().getItemMeta().getDisplayName()==null){
				//通常の棒だった場合無視する
			}
			else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "AdminInventoryTools")){
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
						itemmeta3.setLore(Arrays.asList(ChatColor.YELLOW + "ホワイトリストをトグルします。", ChatColor.WHITE + "コマンド:", ChatColor.WHITE + "/whitelist on・offと同様です。"));
						item3.setItemMeta(itemmeta3);

						//インベントリGUI5つ目の設定
						ItemStack item4 = new ItemStack(Material.SKULL_ITEM);
						ItemMeta itemmeta4 = item4.getItemMeta();
						itemmeta4.setDisplayName(ChatColor.GOLD  + "自分の頭を取得");
						itemmeta4.setLore(Arrays.asList(ChatColor.YELLOW + "MobHeadを自分の頭にして取得。"));
						item4.setItemMeta(itemmeta4);

						//インベントリGUI6つ目の設定
						ItemStack item5 = new ItemStack(Material.APPLE);
						ItemMeta itemmeta5 = item5.getItemMeta();
						itemmeta5.setDisplayName(ChatColor.GOLD  + "オペレータ権限変更");
						itemmeta5.setLore(Arrays.asList(ChatColor.YELLOW + "オペレータ権限をトグルします。", ChatColor.WHITE + "ait.openのパーミッションを保有していない場合", ChatColor.WHITE + "この画面を再度開けなくなります。"));
						item5.setItemMeta(itemmeta5);

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
			else if(block.getType().equals(Material.SIGN_POST )|| block.getType().equals(Material.SIGN)){

			Sign sign = (Sign) block.getState();
			String[] lines = sign.getLines();
			Player player = event.getPlayer();
			World world = event.getPlayer().getWorld();

			player.sendMessage("[情報]看板クリックしたよ！");
			if(lines[0].equalsIgnoreCase("*Teleport*")){
				if(!lines[2].isEmpty()){
					String[] lineThreeEx = lines[2].split(",");
					Location location = new Location(world, Double.parseDouble(lineThreeEx[0]), Double.parseDouble(lineThreeEx[1]), Double.parseDouble(lineThreeEx[2]));
					player.teleport(location);
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
				if(event.isRightClick() || event.isLeftClick()|| event.getAction()==InventoryAction.HOTBAR_SWAP || event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || event.getAction() == InventoryAction.DROP_ONE_SLOT ||event.getAction() == InventoryAction.DROP_ALL_SLOT){
					StackTraceElement[] ste = (new Throwable()).getStackTrace();
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
							Bukkit.setWhitelist(true);
							player.sendMessage(ChatColor.AQUA + "[情報]ホワイトリストを有効化しました。");
						}
						else{
							player.sendMessage(ChatColor.AQUA + "[情報]不明なエラーが発生しました。メインクラス:" + ste[0].getLineNumber());
						}
						player.closeInventory();
					}
					if(event.getRawSlot()==4){

						 ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1);
						 SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
						 skull.setDurability((short) 3);
						 skullMeta.setDisplayName(ChatColor.GOLD + player.getName() + "の頭");
						 skullMeta.setOwner(player.getName());
						 skull.setItemMeta(skullMeta);

						 skull.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
						 player.getInventory().addItem(skull);

						player.closeInventory();
					}

					if(event.getRawSlot()==5){
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
