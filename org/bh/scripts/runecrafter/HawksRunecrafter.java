package org.bh.scripts.runecrafter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.bh.util.Task;
import org.powerbot.script.*;
import org.powerbot.script.Script.Manifest;
import org.powerbot.script.rt6.Skills;
import org.bh.scripts.runecrafter.gui.Gui;

@Manifest(description = "Crafts runes", name = "Hawks runecrafter", properties = "xXBlackHawkXx")
public class HawksRunecrafter extends PollingScript<org.powerbot.script.rt6.ClientContext> implements PaintListener{

	public static ArrayList<Task> tasks = new ArrayList<Task>();

	DecimalFormat formatter = new DecimalFormat("#,###");

	private int startExp;
	private int gainedExp;
	private int startLvl;
	private int gainedLvl;
	private int currentLvl;
	private int currentExp;
	private int expToNextLevel;
	private int expPerHour;
	private long startTime;
	public static String status = "Initializing";
	public static int runePrice;
	public static int essPrice;
	private int profit;
	public static int tripsDone;
	public static int runesMade;
	private int tripsDonePerHour;
	private int runesMadePerHour;
	public static int essUsed;
	private int profitPerHour;

	@Override
	public void start() {
		if (ctx.game.loggedIn()) {
			startExp = ctx.skills.experience(Skills.RUNECRAFTING);
			startLvl = ctx.skills.realLevel(Skills.RUNECRAFTING);
			startTime = System.currentTimeMillis();
			tasks.clear();

			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					new Gui(ctx);
				}
			});
		} else {
			System.out.println("Start script logged in!");
			ctx.bot().close();
		}

	}

	@Override
	public void poll() {
		for (Task task : tasks) {
			if (task.activate()) {
				task.execute();
			}
		}
	}

	public int perHour(int value) {
		return (int) ((value) * 3600000D / (System.currentTimeMillis() - startTime));
	}

	public static String formatTime(final long time) {
		final int sec = (int) (time / 1000), hour = sec / 3600, minute = sec / 60 % 60, s = sec % 60;
		return (hour < 10 ? "0" + hour : hour) + ":"
				+ (minute < 10 ? "0" + minute : minute) + ":"
				+ (s < 10 ? "0" + s : s);
	}

	@Override
	public void repaint(Graphics g1) {
		currentLvl = ctx.skills.realLevel(Skills.RUNECRAFTING);
		gainedLvl = currentLvl - startLvl;
		currentExp = ctx.skills.experience(Skills.RUNECRAFTING);
		gainedExp = currentExp - startExp;
		expToNextLevel = (ctx.skills.experienceAt(ctx.skills.realLevel(Skills.RUNECRAFTING) + 1)) - currentExp;
		expPerHour = perHour(gainedExp);
		runesMadePerHour = perHour(runesMade);
		tripsDonePerHour = perHour(tripsDone);
		profit = (runesMade * runePrice) - (essUsed * essPrice);
		profitPerHour = perHour(profit);

		Graphics2D g = (Graphics2D) g1;

		final BufferedImage paint = downloadImage("http://i61.tinypic.com/2rmtukl.png");
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(paint, 0, 390, null);

		g.setColor(Color.BLACK);
		g.drawString("" + formatTime(getTotalRuntime()), 105, 467);
		g.drawString("" + currentLvl + " (" + gainedLvl + ")", 138, 493);
		g.drawString("" + formatter.format(gainedExp), 120, 519);
		g.drawString("" + formatter.format(expToNextLevel), 168, 543);
		g.drawString("" + formatter.format(expPerHour), 144, 568);
		g.drawString("" + Gui.rune, 367 ,467);
		g.drawString("" + formatter.format(runesMade) + " (" + formatter.format(runesMadePerHour) + ")", 426, 493);
		g.drawString("" + formatter.format(tripsDone) + " (" + formatter.format(tripsDonePerHour) + ")", 420, 519);
		g.drawString("" + formatter.format(profit) + "gp" + " (" + formatter.format(profitPerHour) + "gp)", 385, 543);
		g.drawString("" + status, 390, 568);

		Point m = ctx.mouse.getLocation();
		g.setColor(Color.BLACK);
		g.fillRect(0, m.y - 2, ctx.game.dimensions().width, 5);
		g.fillRect(m.x - 2, 0, 5, ctx.game.dimensions().height);
		g.setColor(new Color(128,0,128));
		g.drawLine(0, m.y, ctx.game.dimensions().width, m.y);
		g.drawLine(m.x, 0, m.x, ctx.game.dimensions().height);
	}
}
