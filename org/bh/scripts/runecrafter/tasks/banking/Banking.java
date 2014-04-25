package org.bh.scripts.runecrafter.tasks.banking;

import org.bh.scripts.runecrafter.HawksRunecrafter;
import org.bh.scripts.runecrafter.data.Runes;
import org.bh.scripts.runecrafter.gui.Gui;
import org.bh.util.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.Bank;
import org.powerbot.script.rt6.ClientContext;

import java.util.concurrent.Callable;

public class Banking extends Task {

	private HawksRunecrafter hr;
	private Runes rune;
	private Gui gui;

	public Banking(ClientContext ctx, Runes rune, Gui gui) {
		super(ctx);
		this.rune = rune;
		this.gui = gui;
	}

	@Override
	public boolean activate() {
		return ctx.players.local().animation() == -1
				&& ctx.bank.opened();
	}

	@Override
	public void execute() {
		//System.out.println("BANKING");
		if (!ctx.backpack.select().id(rune.getRuneId()).isEmpty()) {
			ctx.bank.deposit(rune.getRuneId(), Bank.Amount.ALL);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.backpack.select().id(rune.getRuneId()).isEmpty();
				}
			}, 500, 2);
		}
		if (gui.useTally) {
			if (ctx.backpack.select().id(rune.getTallyId()).isEmpty()) {
				ctx.bank.withdraw(rune.getTallyId(), 1);
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return !ctx.backpack.select().id(rune.getTallyId()).isEmpty();
					}
				}, 500, 2);
			} else {
				ctx.bank.withdraw(rune.getEssId(), Bank.Amount.ALL);
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return !ctx.backpack.select().id(rune.getEssId()).isEmpty();
					}
				}, 500, 2);
			}
		} else if (gui.useTiara) {
			ctx.bank.withdraw(rune.getEssId(), Bank.Amount.ALL);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.backpack.select().id(rune.getEssId()).isEmpty();
				}
			}, 500, 2);
		}
		if (!ctx.backpack.select().id(rune.getEssId()).isEmpty()){
			ctx.bank.close();
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.bank.opened();
				}
			}, 500, 2);
		}
	}

}