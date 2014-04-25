package org.bh.scripts.runecrafter.tasks.craft;

import org.bh.scripts.runecrafter.HawksRunecrafter;
import org.bh.scripts.runecrafter.data.Runes;
import org.bh.util.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;

import java.util.concurrent.Callable;

public class CraftRunes extends Task {

	private HawksRunecrafter hr;
	private Runes rune;

	private int ess;

	public CraftRunes(ClientContext ctx, Runes rune) {
		super(ctx);
		this.rune = rune;
	}

	@Override
	public boolean activate() {
		final GameObject altar = ctx.objects.select().id(rune.getAltarId()).poll();
		ess = rune.getEssId();
		return !ctx.backpack.select().id(ess).isEmpty()
				&& ctx.players.local().animation() == -1
				&& !ctx.objects.select().id(altar).isEmpty();
	}

	@Override
	public void execute() {
		//System.out.println("CRAFT RUNES");
		final GameObject altar = ctx.objects.select().id(rune.getAltarId()).nearest().first().poll();
		hr.essUsed = hr.essUsed + ctx.backpack.select().id(rune.getEssId()).count();
		if (altar.inViewport()) {
			if (altar.interact("Craft-rune")) {
				hr.status = "Crafting runes";
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ctx.backpack.select().id(rune.getEssId()).isEmpty();
					}
				}, 750, 3);
				hr.tripsDone++;
				Item runeStack = ctx.backpack.select().id(rune.getRuneId()).poll();
				int stackSize = runeStack.stackSize();
				hr.runesMade = hr.runesMade + stackSize;
			}
		} else {
			ctx.movement.findPath(altar);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return altar.inViewport();
				}
			}, 1000, 2);
			ctx.camera.turnTo(altar, 20);
			ctx.camera.pitch(99);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return altar.inViewport();
				}
			}, 500, 2);
		}
	}
}
