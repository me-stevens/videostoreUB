import java.util.Vector;
import java.util.Enumeration;

public class Statement {
    private String customerName;
    private Vector rentals;
    private double totalAmount;
    private int frequentRenterPoints;

    public Statement(String name) {
        this.customerName = name;
        rentals   = new Vector();
    }

    public void addRental(Rental rental) {
        rentals.addElement(rental);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getFrequentRenterPoints() {
        return frequentRenterPoints;
    }

    public String generate() {
        String statementText = header();
        statementText += rentalCalculation();
        statementText += footer();
        return statementText;
    }

    private String header() {
        return String.format("Rental Record for %s\n", customerName);
    }

    private String rentalCalculation() {
        totalAmount          = 0;
        frequentRenterPoints = 0;
        String statementText = "";
        Enumeration rentals  = this.rentals.elements();

        while (rentals.hasMoreElements()) {
            Rental rental         = (Rental) rentals.nextElement();
            frequentRenterPoints += calculateFrequentRenterPoints(rental);
            double rentalAmount   = rentalLine(rental);
            statementText        += formatRentalLine(rental, rentalAmount);
            totalAmount          += rentalAmount;
        }

        return statementText;
    }

    private double rentalLine(Rental rental) {
        return rental.getRentalAmount();
    }

    private int calculateFrequentRenterPoints(Rental rental) {
        if (hasBonusPoints(rental))
            return 2;
        return 1;
    }

    private boolean hasBonusPoints(Rental rental) {
        return rental.getMovie().getPriceCode() == Movie.NEW_RELEASE && rental.getDaysRented() > 1;
    }

    private String formatRentalLine(Rental rental, double rentalAmount) {
        return String.format("\t%s\t%.1f\n", rental.getTitle(), rentalAmount);
    }

    private String footer() {
        return String.format("You owed %.1f\nYou earned %d frequent renter points\n", totalAmount, frequentRenterPoints);
    }
}
