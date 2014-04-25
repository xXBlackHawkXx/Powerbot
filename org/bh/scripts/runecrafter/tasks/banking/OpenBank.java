package org.bh.scripts.runecrafter.tasks.banking;

import org.bh.scripts.runecrafter.HawksRunecrafter;
import org.bh.scripts.runecrafter.data.Runes;
import org.bh.scripts.runecrafter.gui.Gui;
import org.bh.util.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;

import java.util.concurrent.Callable;

public class OpenBank extends Task {

	private HawksRunecrafter hr;
	private Runes rune;
	private Gui gui;

	public OpenBank(ClientContext ctx, Runes rune, Gui gui) {
		super(ctx);
		this.rune = rune;
		this.gui = gui;
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().id(rune.getEssId()).first().isEmpty()
				&& ctx.players.local().animation() == -1
				&& !ctx.bank.opened()
				&& ctx.movement.distance(ctx.bank.nearest(), ctx.players.local().tile()) < 10
				&& ctx.bank.inViewport();
	}

	@Override
	public void execute() {
		//System.out.println("OPEN BANK");
		if (!ctx.bank.inViewport()) {
			ctx.camera.turnTo(new Tile((ctx.bank.nearest().tile().x() + -Random.nextInt(1, 10)),
					(ctx.bank.nearest().tile().y() + -Random.nextInt(1, 10)),
					(ctx.bank.nearest().tile().floor() + -Random.nextInt(1, 10))));
		} else {
			if (!ctx.bank.opened()) {
				ctx.bank.open();
				hr.status = "Banking";
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ctx.bank.opened();
					}
				}, 500, 2);
			}
		}

	}

}