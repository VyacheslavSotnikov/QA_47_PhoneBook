package utils;

public enum HeaderMenuItem {
    LOGIN("//a[text()='LOGIN']"),
    ADD("//a[text()='ADD']"),
    ABOUT("//a[text()='ABOUT']"),
    HOME("//a[text()='HOME']"),
    CONTACTS("//a[text()='CONTACTS']"),
    SIGNOUT("//a[text()='Sign Out']");

    private final String locator;
    HeaderMenuItem(String locator){
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }
}
