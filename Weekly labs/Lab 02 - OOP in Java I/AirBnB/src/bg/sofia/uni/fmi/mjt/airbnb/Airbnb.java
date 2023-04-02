package bg.sofia.uni.fmi.mjt.airbnb;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;
import bg.sofia.uni.fmi.mjt.airbnb.filter.Criterion;


public class Airbnb implements AirbnbAPI {
    public Bookable[] accommodations;

    public Airbnb(Bookable[] accommodations) {
        this.accommodations = accommodations;
    }

    @Override
    public Bookable findAccommodationById(String id) {
        if(id == null || id.isBlank()) return null;
        for (Bookable accommodation : this.accommodations) {
            if (accommodation.getId().equalsIgnoreCase(id)) {
                return accommodation;
            }
        }
        return null;
    }

    @Override
    public double estimateTotalRevenue() {
        double sumOfAllRevenues = 0.0;
        for (Bookable accommodation : this.accommodations) {
            if(accommodation.isBooked()) {
                sumOfAllRevenues += accommodation.getTotalPriceOfStay();
            }
        }
        return sumOfAllRevenues;
    }

    @Override
    public long countBookings() {
        long counterOfBookings = 0;
        for(Bookable accommodation : this.accommodations) {
            if(accommodation.isBooked()) counterOfBookings++;
        }
        return counterOfBookings;
    }

    @Override
    public Bookable[] filterAccommodations(Criterion... criteria) {
        if(this.accommodations == null || this.accommodations.length == 0) return new Bookable[0];
        if(criteria == null || criteria.length == 0) return this.accommodations;

        int sizeOfNewBookableArray = 0;

        for(Bookable accommodation : this.accommodations) {
            boolean fulfilsCriteria = true;
            for(Criterion criterion : criteria) {
                if(criterion == null) break;
                if(!criterion.check(accommodation)) {
                    fulfilsCriteria = false;
                    break;
                }
            }
            if(fulfilsCriteria) sizeOfNewBookableArray++;
        }

        if(sizeOfNewBookableArray == 0) return new Bookable[0];
        Bookable[] bookableAccommodations = null;
        bookableAccommodations = new Bookable[sizeOfNewBookableArray];
        int index = 0;

        for(Bookable accommodation : this.accommodations) {
            boolean fulfilsCriteria = true;
            for(Criterion criterion : criteria) {
                if(criterion == null) break;
                if(!criterion.check(accommodation)) {
                    fulfilsCriteria = false;
                    break;
                }
            }
            if(fulfilsCriteria) bookableAccommodations[index++] = accommodation;
        }
        return bookableAccommodations;
    }

}
