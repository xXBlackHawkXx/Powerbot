package org.bh.scripts.runecrafter.tasks.craft;

import org.bh.scripts.runecrafter.HawksRunecrafter;
import org.bh.scripts.runecrafter.data.Runes;
import org.bh.util.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import java.util.concurrent.Callable;

public class LeaveRuins extends Task {

	private HawksRunecrafter hr;
	private Runes rune;

	private int ess;

	public LeaveRuins(ClientContext ctx, Runes rune) {
		super(ctx);
		this.rune = rune;
	}

	@Override
	public boolean activate() {
		final GameObject portal = ctx.objects.select().id(rune.getPortalId()).poll();
		ess = rune.getEssId();
		return ctx.backpack.select().id(ess).isEmpty()
				&& ctx.players.local().animation() == -1
				&& !ctx.objects.select().id(portal).isEmpty();
	}

	@Override
	public void execute() {
		//System.out.println("LEAVE RUINS");
		final GameObject portal = ctx.objects.select().id(rune.getPortalId()).nearest().first().poll();
		if (portal.inViewport()) {
			ctx.camera.turnTo(portal);
			if (portal.interact("Enter")) {
				hr.status = "Leaving ruins";
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return !portal.inViewport();
					}
				}, 1500, 3);
			}
		} else {
			ctx.movement.findPath(portal);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					//return portal.inViewport();
					return ctx.varpbits.varpbit(82) != 109;
				}
			}, 1000, 2);
			ctx.camera.turnTo(portal, 20);
			ctx.camera.pitch(99);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return portal.inViewport();
				}
			}, 500, 2);
		}
	}
}
