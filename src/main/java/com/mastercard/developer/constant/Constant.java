package com.mastercard.developer.constant;

public final class Constant {

    private Constant() {
        throw new UnsupportedOperationException();
    }

    public static final String CLIENT_ID = "";
    public static final String UTC_OFFSET = "-07:00";
    public static final int OFFSET = 0;
    public static final int PAGE_NUMBER = 1;
    public static final int LIMIT_FIVE = 5;
    public static final int LIMIT_TEN = 10;

    public static final class Offers {

        private Offers() {
            throw new UnsupportedOperationException();
        }

        public static final Boolean ACTIVE = true;
        public static final String COUNTRY_USA = "USA";
        public static final String EN_HYPEN_US = "en-US";
        public static final String EN_US = "en_US";
        public static final String CATEGORY_SHOP = null;
        public static final String CATEGORY_DEPARTMENTSTORE = "DEPARTMENTSTORE";
        public static final String OFFER_TYPE_POSTPAIDCREDIT = "POSTPAIDCREDIT";
        public static final String MERCHANT_NAME = "Koalla";
    }

    public static final class OfferRating {

        private OfferRating() {
            throw new UnsupportedOperationException();
        }
        public static final Integer LIKE = 1;
        public static final String CURRENT_TRUE = Boolean.TRUE.toString();
        public static final String CURRENT_FALSE = Boolean.FALSE.toString();
    }

    public static final class UserAdjustments {

        private UserAdjustments() {
            throw new UnsupportedOperationException();
        }
        public static final String START_DATE = "2024-01-05";
        public static final String END_DATE = "2024-02-05";
        public static final String DATE_FILTER = "CREATED";
    }

    public static final class StatementCreditOffer {

        private StatementCreditOffer() {
            throw new UnsupportedOperationException();
        }
        public static final String PRESENTMENT_DATE = "2024-02-05";
        public static final String ACTIVATION_ID = "7";
    }

    public static final class UserFeedback {

        private UserFeedback() {
            throw new UnsupportedOperationException();
        }
        public static final Integer FEEDBACK = 1;
    }
}
