package com.mastercard.developer.integration.data;

import com.mastercard.api.model.DetailPostpaidCreditOfferDetailedOffers;
import com.mastercard.api.model.DetailedOfferDetailedOffers;
import com.mastercard.api.model.DetailedOffersResponseDetailedOffers;
import com.mastercard.api.model.DetailedRedeemedOfferListResponseRedeemedOffers;
import com.mastercard.api.model.MatchedOfferDetailsMatchedOffers;
import com.mastercard.api.model.MatchedOfferDetailsResponseMatchedOffers;
import com.mastercard.api.model.MatchedOfferWrapperMatchedOffers;
import com.mastercard.api.model.MerchantDetailedOffers;
import com.mastercard.api.model.MerchantMatchedOffers;
import com.mastercard.api.model.StatementCreditOfferDetailsMatchedOffers;
import com.mastercard.api.model.StatementCreditOfferDetailsResponseStatementCreditOfferActivation;
import com.mastercard.api.model.StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail;
import com.mastercard.api.model.StatementCreditOfferDetailsStatementCreditOfferActivation;
import com.mastercard.api.model.StatementCreditOfferDetailsStatementCreditOfferActivationDetail;
import com.mastercard.api.model.StatementCreditOffersSavings;
import com.mastercard.api.model.Status;
import com.mastercard.api.model.StatusDetailedOffers;
import com.mastercard.api.model.StatusMatchedOffers;
import com.mastercard.api.model.StatusRedeemedOffers;
import com.mastercard.api.model.StatusStatementCreditOfferActivation;
import com.mastercard.api.model.StatusStatementCreditOfferActivationDetail;
import com.mastercard.api.model.UserFeedbackOutput;
import com.mastercard.api.model.UserFeedbackOutputListResponse;
import com.mastercard.api.model.UserFeedbackOutputMatchedOffers;
import com.mastercard.api.model.UserFeedbackOutputResponse;
import com.mastercard.api.model.UserFeedbackWrapper;
import com.mastercard.api.model.UserSavings;
import com.mastercard.api.model.UserSavingsResponse;
import com.mastercard.api.model.UserTokenOutput;
import com.mastercard.api.model.UserTokenOutputResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonalizedOffersData {

  public static final int FEEDBACK = 1;

  public static final LocalDate ACTIVATION_DATE = LocalDate.of(2020, 8, 10);
  public static final LocalDate END_DATE = LocalDate.of(2020, 9, 10);
  public static final LocalDate REDEMPTION_DATE = LocalDate.of(2020, 8, 9);
  public static final LocalDate START_DATE = LocalDate.of(2020, 10, 10);

  public static final String ACTIVATION_ID = "Test-Activation-Id";
  public static final String ACTIVE_SC = "Test-Active-Statement-Credit";
  public static final String CASH_AMOUNT = "25";
  public static final String CASH_BACK = "10";
  public static final String CURRENCY_CODE = "USD";
  public static final String DISCOUNT = "20.0";
  public static final String DISCOUNT_TYPE = "PERCENTAGE";
  public static final String FID = "Test-FId";
  public static final String FIRST = "1";
  public static final String ITEMS_PER_PAGE = "25";
  public static final String LANGUAGE = "English";
  public static final String MERCHANT = "Test-Merchant";
  public static final String OFFER_COUNTRY = "USA";
  public static final String OFFER_ID = "Test-Offer-Id";
  public static final String OFFER_ID2 = "Test-Offer-Id2";
  public static final String OFFER_SOURCE = "Test-Offer-Source";
  public static final String OFFER_TYPE = "Test-Offer-Type";
  public static final String ONE = "1";
  public static final String POINTS_EARNED = "2";
  public static final String PRICE = "0.0";
  public static final String REDEMPTION_MODE = "CASH";
  public static final String REDEMPTION_TYPE = "ONLINE";
  public static final String STATEMENT_CREDIT_TYPE = "VISIT_PERCENTAGE";
  public static final String STATUS = "ACTIVATED";
  public static final String STATUS_CODE = "600";
  public static final String STATUS_MESSAGE = "Success";
  public static final String TOTAL_COUNT = "2";
  public static final String USER_TOKEN = "Test-User-Token";

  public static DetailedRedeemedOfferListResponseRedeemedOffers redeemedOffers() {
    final DetailedRedeemedOfferListResponseRedeemedOffers redeemedOffers =
        new DetailedRedeemedOfferListResponseRedeemedOffers();

    final StatusRedeemedOffers status = new StatusRedeemedOffers();
    status.code(STATUS_CODE);
    status.message(STATUS_MESSAGE);

    redeemedOffers.setStatus(status);
    redeemedOffers.setCurrentPage(FIRST);
    redeemedOffers.setItemsPerPage(ITEMS_PER_PAGE);
    redeemedOffers.setNumberOfPages(ONE);
    redeemedOffers.setTotalCount(ONE);

    return redeemedOffers;
  }

  public static StatementCreditOfferDetailsResponseStatementCreditOfferActivation activatedOffer() {
    final StatementCreditOfferDetailsResponseStatementCreditOfferActivation activatedOffer =
        new StatementCreditOfferDetailsResponseStatementCreditOfferActivation();

    final StatusStatementCreditOfferActivation status = new StatusStatementCreditOfferActivation();
    status.code(PersonalizedOffersData.STATUS_CODE);
    status.message(PersonalizedOffersData.STATUS_MESSAGE);
    activatedOffer.setStatus(status);

    final StatementCreditOfferDetailsStatementCreditOfferActivation activation =
        new StatementCreditOfferDetailsStatementCreditOfferActivation();
    activation.setActivationId(ACTIVATION_ID);
    activation.setStatus(STATUS);
    activation.setOfferId(OFFER_ID);
    activation.setActivationDate(ACTIVATION_DATE);
    activation.setRedemptionDate(REDEMPTION_DATE);
    activation.setRedemptionEndDate(END_DATE);
    activation.setRedemptionStartDate(START_DATE);
    activation.setRedemptionMode(REDEMPTION_MODE);
    activation.setPointsEarned(POINTS_EARNED);
    activation.setMerchant(MERCHANT);
    activation.setCashBack(CASH_BACK);

    activatedOffer.setScActivation(activation);

    return activatedOffer;
  }

  public static StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
  activationDetails() {
    final StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail activationDetail =
        new StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail();

    final StatusStatementCreditOfferActivationDetail status =
        new StatusStatementCreditOfferActivationDetail();
    status.setCode(STATUS_CODE);
    status.setMessage(STATUS_MESSAGE);
    activationDetail.setStatus(status);

    final StatementCreditOfferDetailsStatementCreditOfferActivationDetail activation =
        new StatementCreditOfferDetailsStatementCreditOfferActivationDetail();
    activation.setActivationId(ACTIVATION_ID);
    activation.setStatus(STATUS);
    activation.setOfferId(OFFER_ID);
    activation.setActivationDate(ACTIVATION_DATE);
    activation.setRedemptionDate(REDEMPTION_DATE);
    activation.setRedemptionStartDate(START_DATE);
    activation.setRedemptionMode(REDEMPTION_MODE);
    activation.setPointsEarned(POINTS_EARNED);
    activation.setMerchant(MERCHANT);
    activation.setCashBack(CASH_BACK);

    activationDetail.setScActivation(activation);

    return activationDetail;
  }

  public static UserFeedbackOutputResponse sendUserFeedback() {
    final UserFeedbackOutputResponse userFeedback = new UserFeedbackOutputResponse();

    final Status status = new Status();
    status.setCode(STATUS_CODE);
    status.setMessage(STATUS_MESSAGE);
    userFeedback.setStatus(status);

    final UserFeedbackOutput userFeedbackOutput = new UserFeedbackOutput();
    userFeedbackOutput.setOfferId(OFFER_ID);
    userFeedbackOutput.setFeedback(String.valueOf(FEEDBACK));
    userFeedback.setUserFeedback(userFeedbackOutput);

    return userFeedback;
  }

  public static UserFeedbackOutputListResponse getUserFeedback() {
    final UserFeedbackOutputListResponse userFeedback = new UserFeedbackOutputListResponse();

    final Status status = new Status();
    status.setCode(STATUS_CODE);
    status.setMessage(STATUS_MESSAGE);
    userFeedback.setStatus(status);

    final List<UserFeedbackOutput> items = new ArrayList<>();

    final UserFeedbackOutput userFeedbackOutput = new UserFeedbackOutput();
    userFeedbackOutput.setOfferId(OFFER_ID);
    userFeedbackOutput.setFeedback(String.valueOf(FEEDBACK));
    items.add(userFeedbackOutput);

    final UserFeedbackOutput userFeedbackOutput2 = new UserFeedbackOutput();
    userFeedbackOutput2.setOfferId(OFFER_ID2);
    userFeedbackOutput2.setFeedback(String.valueOf(FEEDBACK));
    items.add(userFeedbackOutput2);

    final UserFeedbackWrapper userFeedbackItems = new UserFeedbackWrapper();
    userFeedbackItems.setUserFeedback(items);
    userFeedback.setItems(userFeedbackItems);

    userFeedback.setCurrentPage(FIRST);
    userFeedback.setItemsPerPage(ITEMS_PER_PAGE);
    userFeedback.setTotalCount(TOTAL_COUNT);

    return userFeedback;
  }

  public static MatchedOfferDetailsResponseMatchedOffers getMatchedOffers() {
    final MatchedOfferDetailsResponseMatchedOffers matchedOffers =
        new MatchedOfferDetailsResponseMatchedOffers();

    matchedOffers.setTotalCount(ONE);
    matchedOffers.currentPage(FIRST);
    matchedOffers.numberOfPages(ONE);
    matchedOffers.itemsPerPage(ITEMS_PER_PAGE);

    final StatusMatchedOffers status = new StatusMatchedOffers();
    status.setCode(STATUS_CODE);
    status.setMessage(STATUS_MESSAGE);
    matchedOffers.setStatus(status);

    final MatchedOfferWrapperMatchedOffers items = new MatchedOfferWrapperMatchedOffers();

    final MatchedOfferDetailsMatchedOffers matchedOffer = new MatchedOfferDetailsMatchedOffers();
    matchedOffer.setOfferId(OFFER_ID);
    matchedOffer.setOfferType(OFFER_TYPE);
    matchedOffer.setOfferSource(OFFER_SOURCE);
    matchedOffer.setLanguage(LANGUAGE);
    matchedOffer.setCurrencyCode(CURRENCY_CODE);

    final MerchantMatchedOffers merchant = new MerchantMatchedOffers();
    merchant.setName(MERCHANT);
    matchedOffer.setMerchant(merchant);

    matchedOffer.setRedemptionMode(REDEMPTION_MODE);
    matchedOffer.setEventStartDate(START_DATE);
    matchedOffer.setEventEndDate(END_DATE);
    matchedOffer.setOfferCountry(OFFER_COUNTRY);
    matchedOffer.setActivationDate(ACTIVATION_DATE);

    final StatementCreditOfferDetailsMatchedOffers activation =
        new StatementCreditOfferDetailsMatchedOffers();
    activation.setActivationId(ACTIVATION_ID);
    activation.setRedemptionStartDate(START_DATE);
    activation.setRedemptionEndDate(END_DATE);
    matchedOffer.setScActivation(activation);

    matchedOffer.setPrice(PRICE);
    matchedOffer.setDiscount(DISCOUNT);
    matchedOffer.setDiscountType(DISCOUNT_TYPE);
    matchedOffer.setActiveSc(ACTIVE_SC);

    final UserFeedbackOutputMatchedOffers userFeedback = new UserFeedbackOutputMatchedOffers();
    userFeedback.setFeedback(String.valueOf(FEEDBACK));
    matchedOffer.setUserFeedback(userFeedback);

    items.addMatchedOfferItem(matchedOffer);
    matchedOffers.setItems(items);

    return matchedOffers;
  }

  public static UserSavingsResponse getUserSavings() {
    final UserSavingsResponse userSavings = new UserSavingsResponse();

    final Status status = new Status();
    status.setCode(STATUS_CODE);
    status.setMessage(STATUS_MESSAGE);
    userSavings.setStatus(status);

    final StatementCreditOffersSavings statementCreditOffersSavings =
        new StatementCreditOffersSavings();
    statementCreditOffersSavings.setNumRedeemed(ONE);
    statementCreditOffersSavings.setEarnedCashback(CASH_BACK);
    statementCreditOffersSavings.setNumAvailable(ONE);
    statementCreditOffersSavings.setPotentialSavings(ONE);
    statementCreditOffersSavings.setEarnedPoints(POINTS_EARNED);
    statementCreditOffersSavings.setPotentialPoints(POINTS_EARNED);

    final UserSavings savings = new UserSavings();
    savings.setStatementCreditOffersSavings(statementCreditOffersSavings);

    savings.setTotalAmountSaved(CASH_AMOUNT);
    savings.setTotalOffersUsed(ONE);
    savings.setTotalEarnedPoints(POINTS_EARNED);

    userSavings.setUserSavings(savings);

    return userSavings;
  }

  public static UserTokenOutputResponse getUserToken() {
    final UserTokenOutputResponse userToken = new UserTokenOutputResponse();

    final Status status = new Status();
    status.setCode(STATUS_CODE);
    status.setMessage(STATUS_MESSAGE);
    userToken.setStatus(status);

    final UserTokenOutput token = new UserTokenOutput();
    token.setToken(USER_TOKEN);
    userToken.setUserToken(token);

    return userToken;
  }

  public static DetailedOffersResponseDetailedOffers getOfferDetails() {
    final DetailedOffersResponseDetailedOffers detailedOffers =
        new DetailedOffersResponseDetailedOffers();

    final StatusDetailedOffers status = new StatusDetailedOffers();
    status.setCode(STATUS_CODE);
    status.setMessage(STATUS_MESSAGE);
    detailedOffers.setStatus(status);

    final DetailedOfferDetailedOffers offersDetails = new DetailedOfferDetailedOffers();
    offersDetails.setOfferId(OFFER_ID);
    offersDetails.setOfferType(OFFER_TYPE);
    offersDetails.setRedemptionType(REDEMPTION_TYPE);
    offersDetails.setOfferSource(OFFER_SOURCE);
    offersDetails.setLanguage(LANGUAGE);
    offersDetails.setCurrencyCode(CURRENCY_CODE);

    final MerchantDetailedOffers merchant = new MerchantDetailedOffers();
    merchant.setName(MERCHANT);
    offersDetails.setMerchant(merchant);

    final DetailPostpaidCreditOfferDetailedOffers detailedPostPaidCreditOffer =
        new DetailPostpaidCreditOfferDetailedOffers();
    detailedPostPaidCreditOffer.setCashBack(CASH_BACK);
    detailedPostPaidCreditOffer.setStatementCreditType(STATEMENT_CREDIT_TYPE);
    detailedPostPaidCreditOffer.setVisitThreshold(ONE);
    detailedPostPaidCreditOffer.setMinTransactionAmountToRedeem(CASH_AMOUNT);
    offersDetails.setDetailPostpaidCreditOffer(detailedPostPaidCreditOffer);

    offersDetails.setRedemptionMode(REDEMPTION_MODE);
    offersDetails.setEventStartDate(START_DATE);
    offersDetails.setEventEndDate(END_DATE);
    offersDetails.setMaxUserRedemptions(ONE);
    offersDetails.setOfferCountry(OFFER_COUNTRY);
    offersDetails.setActivationDate(ACTIVATION_DATE);

    detailedOffers.setOfferDetails(offersDetails);

    return detailedOffers;
  }
}
