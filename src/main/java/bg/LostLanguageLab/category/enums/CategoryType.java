package bg.LostLanguageLab.category.enums;

public enum CategoryType {
    DIALECT("Диалектна"),
    CHURCH_SLAVIC("Църковнославянска"),
    OLD_BULGARIAN("Старобългарска"),
    FOLKLORE("Фолклор"),
    MEDIEVAL("Средновековие"),
    RENAISSANCE("Възраждане");

    private final String categoryName;

    CategoryType(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
