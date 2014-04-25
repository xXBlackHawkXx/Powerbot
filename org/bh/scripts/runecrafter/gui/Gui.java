package org.bh.scripts.runecrafter.gui;

import org.bh.scripts.runecrafter.data.Runes;
import org.bh.scripts.runecrafter.HawksRunecrafter;
import org.bh.scripts.runecrafter.tasks.air.*;
import org.bh.scripts.runecrafter.tasks.banking.*;
import org.bh.scripts.runecrafter.tasks.craft.*;
import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends ClientAccessor {

	private HawksRunecrafter hr;

	public static Runes rune;

	public boolean useTiara = false;
	public boolean useTally = false;

	public Gui(ClientContext ctx) {
		super(ctx);
		init();
	}

	private final JFrame frame = new JFrame("Hawks Cooker");
	private final JPanel panelMain = new JPanel();
	private final JButton cmdStart = new JButton("Start");
	private final JLabel lblRune = new JLabel("Rune:");
	private final JComboBox<Runes> cbRune = new JComboBox<Runes>(Runes.values());
	private final JCheckBox xbTiara = new JCheckBox("Tiara");
	private final JCheckBox xbTally = new JCheckBox("Talisman");

	private void init() {
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(270, 170);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(panelMain);
		panelMain();
	}

	private void panelMain() {
		panelMain.setLayout(new GroupLayout(panelMain));

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(xbTiara);
		buttonGroup.add(xbTally);

		panelMain.add(cmdStart);
		panelMain.add(lblRune);
		panelMain.add(cbRune);
		panelMain.add(xbTiara);
		panelMain.add(xbTally);

		xbTiara.setSelected(true);

		lblRune.setBounds(10, 10, 100, 20);
		cbRune.setBounds(90, 10, 160, 20);
		xbTiara.setBounds(10, 45, 70, 20);
		xbTally.setBounds(80, 45, 70, 20);
		cmdStart.setBounds(10, 115, 240, 20);

		cmdStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				rune = (Runes) cbRune.getSelectedItem();
				hr.runePrice = GeItem.price(Gui.rune.getRuneId());
				hr.essPrice = GeItem.price(Gui.rune.getEssId());

				hr.tasks.add(new OpenBank(ctx, rune, Gui.this));
				hr.tasks.add(new Banking(ctx, rune, Gui.this));
				hr.tasks.add(new CraftRunes(ctx, rune));
				hr.tasks.add(new EnterRuins(ctx, rune));
				hr.tasks.add(new LeaveRuins(ctx, rune));

				if (xbTiara.isSelected()) {
					useTiara = true;
				} else if (xbTally.isSelected()) {
					useTally = true;
				}

				if (cbRune.getSelectedItem().equals(Runes.AIR)) {
					hr.tasks.add(new ToAirAlter(ctx, rune));
					hr.tasks.add(new FromAirAlter(ctx, rune));
				} else if (cbRune.getSelectedItem().equals(Runes.MIND)) {

				} else if (cbRune.getSelectedItem().equals(Runes.WATER)) {

				} else if (cbRune.getSelectedItem().equals(Runes.EARTH)) {

				} else if (cbRune.getSelectedItem().equals(Runes.FIRE)) {

				} else if (cbRune.getSelectedItem().equals(Runes.BODY)) {

				} else if (cbRune.getSelectedItem().equals(Runes.CHAOS)) {

				} else if (cbRune.getSelectedItem().equals(Runes.COSMIC)) {

				} else if (cbRune.getSelectedItem().equals(Runes.ASTRAL)) {

				} else if (cbRune.getSelectedItem().equals(Runes.NATURE)) {

				} else if (cbRune.getSelectedItem().equals(Runes.LAW)) {

				} else if (cbRune.getSelectedItem().equals(Runes.DEATH)) {

				} else if (cbRune.getSelectedItem().equals(Runes.BLOOD)) {

				}
				frame.dispose();

				System.out.println("Tiara: " + useTiara);
				System.out.println("Talisman: " + useTally);
			}

		});

	}
}