package me.smaks6.bedsteal.utilis;

public enum Config {
    VictimBedTitle("VictimBedTitle"),
    VictimBedDescription("VictimBedDescription"),
    BedsHologramText("BedsHologramText"),
    ShurikenTitle("ShurikenTitle"),
    ShurikenDescription("ShurikenDescription"),
    ShurikenItem("ShurikenItem"),
    PoisonSword("PoisonSword"),
    PoisonSwordDescription("PoisonSwordDescription"),
    PoisonSwordPoisonDuration("PoisonDuration"),
    BedHologramXMove("BedHologramXMove"),
    BedHologramYMove("BedHologramYMove"),
    BedHologramZMove("BedHologramZMove");



    private final String configName;

    Config(String configName) {
        this.configName = configName;
    }

    public String getConfigName() {
        return configName;
    }
}
