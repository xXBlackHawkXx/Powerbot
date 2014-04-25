package org.bh.scripts.runecrafter.data;

public enum Runes {

	AIR("Air", 1, "rune", 556, 1438, 5527, 2452, 2478, 2465),
	MIND("Mind", 1, "rune", 558, 1448, 5529, 0, 0, 0),
	WATER("Water", 5, "rune", 555, 1444, 5531, 0, 0, 0),
	EARTH("Earth", 9, "rune", 557, 1440, 5535, 0, 0, 0),
	FIRE("Fire", 14, "rune", 554, 1442, 5537, 0, 0, 0),
	BODY("Body", 20, "rune", 559, 1446, 5533, 0, 0, 0),
	CHAOS("Chaos", 27, "pure", 562, 1452, 5543, 0, 0, 0),
	COSMIC("Cosmic", 35, "pure", 564, 1454, 5539, 0, 0, 0),
	ASTRAL("Astral", 40, "pure", 9075, 0, 0, 0, 0, 0),
	NATURE("Nature", 44, "pure", 561, 1462, 5541, 0, 0, 0),
	LAW("Law", 54, "pure", 563, 1458, 5545, 0, 0, 0),
	DEATH("Death", 65, "pure", 560, 1456, 5547, 0, 0, 0),
	BLOOD("Blood", 77, "pure", 565, 1450, 5549, 0, 0, 0);

	private final String name;
	private final int lvlRequirement;
	private final String essType;
	private final int runeId;
	private final int tallyId;
	private final int tiaraId;
	private final int ruinsId;
	private final int altarId;
	private final int portalId;

	private Runes(String name, int lvlRequirement, String essType, int runeId, int tallyId,
	              int tiaraId, int ruinsId, int altarId, int portalId) {
		this.name = name;
		this.lvlRequirement = lvlRequirement;
		this.essType = essType;
		this.runeId = runeId;
		this.tallyId = tallyId;
		this.tiaraId = tiaraId;
		this.ruinsId = ruinsId;
		this.altarId = altarId;
		this.portalId = portalId;
	}

	public String getName() {
		return name;
	}

	public int getLvlRequirement() {
		return lvlRequirement;
	}

	public int getEssId() {
		if (essType.equals("rune")) {
			return 1436;
		} else if (essType.equals("pure")) {
			return 7936;
		}
		return 0;
	}

	public int getRuneId() {
		return runeId;
	}

	public int getTallyId() {
		return tallyId;
	}

	public int getTiaraId() {
		return tiaraId;
	}

	public int getRuinsId() {
		return ruinsId;
	}

	public int getAltarId() {
		return altarId;
	}

	public int getPortalId() {
		return portalId;
	}


}
