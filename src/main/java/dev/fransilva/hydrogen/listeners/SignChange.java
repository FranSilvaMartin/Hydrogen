package dev.fransilva.hydrogen.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import dev.fransilva.hydrogen.Hydrogen;
import dev.fransilva.hydrogen.utils.TextUtils;

public class SignChange implements Listener {

	private String[] signLines;
	
	@SuppressWarnings("unused")
	private Hydrogen hydrogen;
	
    public SignChange(Hydrogen plugin) {
        this.hydrogen = plugin;
    }
	
	@EventHandler
	public void signEventColors(SignChangeEvent event) {
		signLines = event.getLines();
		for (int i = 0; i < signLines.length; i++) {
			event.setLine(i, TextUtils.colorize(signLines[i]));
		}
	}
	
	@EventHandler
	public void signEventDisposal(SignChangeEvent event) {
		signLines = event.getLines();
		
		for (int i = 0; i < signLines.length; i++) {
			if (signLines[i].startsWith("[") && signLines[i].endsWith("]") && signLines[i].toLowerCase().contains("disposal")) {
				event.setLine(1, TextUtils.colorize("&0[&3Disposal&0]"));
				event.setLine(2, TextUtils.colorize("Trash"));
			}
		}
	}
}
