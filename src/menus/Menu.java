package menus;

/**
 * Defines the contract for all menu classes within the application.
 * <p>
 * Any class implementing this interface is responsible for handling a specific
 * set of user interactions and displaying related options to the console.
 */
public interface Menu {
    /**
     * Executes the main logic for displaying the menu options and managing the
     * user's interaction loop (e.g., prompting for input, handling selections).
     * <p>
     * This method is the entry point for all menu-driven flows.
     */
    void exibir();
}
