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
        totalAmount = 0;
        frequentRenterPoints = 0;

        String statementText = header();
        statementText += rentalCalculation();
        statementText += footer();
        return statementText;
    }

    private String header() {
        return String.format("Rental Record for %s\n", customerName);
    }

    private String rentalCalculation() {
        String statementText = "";
        Enumeration rentals = this.rentals.elements();

        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement();

            // determines the amount for each line
            switch (each.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (each.getDaysRented() > 2)
                        thisAmount += (each.getDaysRented() - 2) * 1.5;
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += each.getDaysRented() * 3;
                    break;
                case Movie.CHILDRENS:
                    thisAmount += 1.5;
                    if (each.getDaysRented() > 3)
                        thisAmount += (each.getDaysRented() - 3) * 1.5;
                    break;
            }

            frequentRenterPoints++;

            if (each.getMovie().getPriceCode() == Movie.NEW_RELEASE
                    && each.getDaysRented() > 1)
                frequentRenterPoints++;

            statementText += String.format("\t%s\t%.1f\n", each.getMovie().getTitle(), thisAmount);
            totalAmount += thisAmount;

        }
        return statementText;
    }

    private String footer() {
        return String.format("You owed %.1f\nYou earned %d frequent renter points\n", totalAmount, frequentRenterPoints);
    }
}
