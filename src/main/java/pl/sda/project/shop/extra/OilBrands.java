package pl.sda.project.shop.extra;

public enum OilBrands {
    CASTROL("Castrol"),
    MOBIL("Mobil"),
    MOTUL("Motul"),
    ELF("Elf");

    private final String name;

    OilBrands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
