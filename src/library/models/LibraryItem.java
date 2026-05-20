package library.models;

public abstract class LibraryItem {
    private String id;
    private String title;
    private String author;
    private boolean isAvailable;

    public LibraryItem(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public abstract String getItemType();
    public abstract int getMaxLoanDays();

    public void displayInfo() {
        System.out.printf("%-6s | %-10s | %-25s | %-20s | %s%n",
                id, getItemType(), title, author, isAvailable ? "Available" : "Borrowed");
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
}
