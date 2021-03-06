package org.bh.scripts.runecrafter.tasks.air;

import org.bh.scripts.runecrafter.HawksRunecrafter;
import org.bh.scripts.runecrafter.data.Runes;
import org.bh.util.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import java.util.concurrent.Callable;

public class ToAirAlter extends Task {

	private HawksRunecrafter hr;
	private Runes rune;

	Tile[] toAirAlter = new Tile[] { new Tile(3184, 3433, 0), new Tile(3183, 3429, 0), new Tile(3178, 3428, 0),
			new Tile(3173, 3427, 0), new Tile(3168, 3427, 0), new Tile(3164, 3424, 0),
			new Tile(3161, 3420, 0), new Tile(3156, 3418, 0), new Tile(3152, 3415, 0),
			new Tile(3147, 3414, 0), new Tile(3143, 3411, 0), new Tile(3139, 3408, 0),
			new Tile(3135, 3405, 0), new Tile(3131, 3402, 0) };

	public ToAirAlter(ClientContext ctx, Runes rune) {
		super(ctx);
		this.rune = rune;
	}

	@Override
	public boolean activate() {
		return !ctx.backpack.select().id(rune.getEssId()).isEmpty()
				&& ctx.players.local().animation() == -1
				&& !ctx.bank.opened()
				&& !(ctx.varpbits.varpbit(82) == 109);
	}

	@Override
	public void execute() {
		//System.out.println("TO AIR ALTAR");
		final GameObject ruins = ctx.objects.select().id(rune.getRuinsId()).nearest().first().poll();
		if (ctx.movement.newTilePath(toAirAlter).randomize(1,1).traverse()) {
			hr.status = "To air altar";
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ruins.inViewport()
					&& ctx.movement.distance(ruins, ctx.players.local().tile()) <= 6;
				}
			}, 1500, 3);
		}

	}
}
