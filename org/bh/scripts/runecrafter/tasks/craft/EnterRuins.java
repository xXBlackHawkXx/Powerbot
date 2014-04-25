package org.bh.scripts.runecrafter.tasks.craft;

import org.bh.scripts.runecrafter.HawksRunecrafter;
import org.bh.scripts.runecrafter.data.Runes;
import org.bh.scripts.runecrafter.gui.Gui;
import org.bh.util.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;

import java.util.concurrent.Callable;

public class EnterRuins extends Task {

	private HawksRunecrafter hr;
	private Runes rune;
	private Gui gui;

	private int ess;

	public EnterRuins(ClientContext ctx, Runes rune) {
		super(ctx);
		this.rune = rune;
	}

	@Override
	public boolean activate() {
		final GameObject ruins = ctx.objects.select().id(rune.getRuinsId()).poll();
		ess = rune.getEssId();
		return !ctx.backpack.select().id(ess).isEmpty()
				&& ctx.players.local().animation() == -1
				&& !ctx.objects.select().id(ruins).isEmpty()
				&& ruins.inViewport();
	}

	@Override
	public void execute() {
		//System.out.println("ENTER RUINS");
		final GameObject ruins = ctx.objects.select().id(rune.getRuinsId()).nearest().first().poll();
		if (ruins.inViewport()) {
			if (ruins.interact("Enter")) {
				hr.status = "Entering ruins";
				Condition.wait(new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
						//return !ruins.inViewport();
						return ctx.varpbits.varpbit(82) == 109;
					}
					}, 1250, 3);
			}
		} else {
			ctx.camera.turnTo(ruins, 20);
			ctx.camera.pitch(99);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ruins.inViewport();
				}
			}, 500, 2);
		}
	}
}
