package library.util;

import library.models.LibraryItem;

import java.util.List;

public class SearchResult<T extends LibraryItem> {
    private List<T> results;
    private String searchTerm;

    public SearchResult(List<T> results, String searchTerm) {
        this.results = results;
        this.searchTerm = searchTerm;
    }

    public void display() {
        if (results.isEmpty()) {
            System.out.println("No results found for: \"" + searchTerm + "\"");
            return;
        }
        System.out.println("Found " + getCount() + " result(s) for: \"" + searchTerm + "\"");
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-6s | %-10s | %-25s | %-20s | %s%n",
                "ID", "Type", "Title", "Author", "Status");
        System.out.println("----------------------------------------------------------------------");
        results.forEach(LibraryItem::displayInfo);
    }

    public int getCount() { return results.size(); }

    public List<T> getResults() { return results; }

    public boolean isEmpty() { return results.isEmpty(); }
}
