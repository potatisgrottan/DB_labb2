package se.kth.olof.beyar.labb.model;

import se.kth.olof.beyar.labb.common.Views;

/**
 * Represents the model for the navigation bar, holding the current view selected by the user.
 */
public class NavbarModel
{
    Views chosenView;

    /**
     * Constructs a NavbarModel with the specified chosen view.
     * @param chosenView the view that is currently chosen
     **/
    public NavbarModel(Views chosenView) {
        this.chosenView = chosenView;
    }

    /**
     * Constructs a NavbarModel with the default view set to SEARCH.
     */
    public NavbarModel() {
        this(Views.SEARCH);
    }

    /**
     * Returns the currently chosen view.
     * @return the currently chosen view
     */
    public Views getChosenView()
    {
        return chosenView;
    }

    /**
     * Sets the chosen view to the specified view.
     * @param chosenView the view to be set as chosen
     */
    public void setChosenView(Views chosenView)
    {
        this.chosenView = chosenView;
    }
}
