package com.mastercard.developer.integration.data;

import com.mastercard.api.model.Activation;
import com.mastercard.api.model.Activations;
import com.mastercard.api.model.Adjustments;
import com.mastercard.api.model.BrowseOffers;
import com.mastercard.api.model.DetailPostpaidCreditOfferDetailedOffers;
import com.mastercard.api.model.DetailedOfferDetailedOffers;
import com.mastercard.api.model.DetailedOffersResponseDetailedOffers;
import com.mastercard.api.model.DetailedRedeemedOfferListResponseRedeemedOffers;
import com.mastercard.api.model.Localization;
import com.mastercard.api.model.MatchedOfferDetailsMatchedOffers;
import com.mastercard.api.model.MatchedOfferDetailsResponseMatchedOffers;
import com.mastercard.api.model.MatchedOfferWrapperMatchedOffers;
import com.mastercard.api.model.MerchantDetailedOffers;
import com.mastercard.api.model.MerchantInfo;
import com.mastercard.api.model.MerchantMatchedOffers;
import com.mastercard.api.model.OfferDetails;
import com.mastercard.api.model.OfferInfo;
import com.mastercard.api.model.OfferRating;
import com.mastercard.api.model.RedemptionClassifiers;
import com.mastercard.api.model.RequestedAccessToken;
import com.mastercard.api.model.RequestedActivation;
import com.mastercard.api.model.RequestedOfferRating;
import com.mastercard.api.model.ResponseWrapperDetailedOffersResponseDetailedOffers;
import com.mastercard.api.model.ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers;
import com.mastercard.api.model.ResponseWrapperMatchedOfferDetailsResponseMatchedOffers;
import com.mastercard.api.model.ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation;
import com.mastercard.api.model.ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail;
import com.mastercard.api.model.ResponseWrapperUserFeedbackOutputList;
import com.mastercard.api.model.ResponseWrapperUserFeedbackOutputWrapper;
import com.mastercard.api.model.ResponseWrapperUserSavingsOutputWrapper;
import com.mastercard.api.model.ResponseWrapperUserTokenOutputWrapper;
import com.mastercard.api.model.Reward;
import com.mastercard.api.model.Savings;
import com.mastercard.api.model.StatementCreditOfferDetailsMatchedOffers;
import com.mastercard.api.model.StatementCreditOfferDetailsResponseStatementCreditOfferActivation;
import com.mastercard.api.model.StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail;
import com.mastercard.api.model.StatementCreditOfferDetailsStatementCreditOfferActivation;
import com.mastercard.api.model.StatementCreditOfferDetailsStatementCreditOfferActivationDetail;
import com.mastercard.api.model.Status;
import com.mastercard.api.model.StatusDetailedOffers;
import com.mastercard.api.model.StatusInfo;
import com.mastercard.api.model.StatusMatchedOffers;
import com.mastercard.api.model.StatusRedeemedOffers;
import com.mastercard.api.model.StatusStatementCreditOfferActivation;
import com.mastercard.api.model.StatusStatementCreditOfferActivationDetail;
import com.mastercard.api.model.UserAccessToken;
import com.mastercard.api.model.UserAdjustment;
import com.mastercard.api.model.UserAdjustments;
import com.mastercard.api.model.UserAssignment;
import com.mastercard.api.model.UserFeedbackOutput;
import com.mastercard.api.model.UserFeedbackOutputList;
import com.mastercard.api.model.UserFeedbackOutputMatchedOffers;
import com.mastercard.api.model.UserFeedbackOutputWrapper;
import com.mastercard.api.model.UserFeedbackWrapper;
import com.mastercard.api.model.UserOffer;
import com.mastercard.api.model.UserOfferDetails;
import com.mastercard.api.model.UserOfferRating;
import com.mastercard.api.model.UserOfferRatings;
import com.mastercard.api.model.UserOffers;
import com.mastercard.api.model.UserSavings;
import com.mastercard.api.model.UserSavingsOutputWrapper;
import com.mastercard.api.model.UserTokenOutput;
import com.mastercard.api.model.UserTokenOutputWrapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class PersonalizedOffersData {

  public static final int FEEDBACK = 1;
  public static final int FIVE = 5;

  public static final BigDecimal ADJUSTMENT_CASH_VALUE = new BigDecimal("123.5");
  public static final BigDecimal ADJUSTMENT_POINTS_VALUE = new BigDecimal("123.5");
  public static final BigDecimal POINTS_CONVERSION_RATE = new BigDecimal("123.5");
  public static final BigDecimal SPEND_THRESHOLD = new BigDecimal("123.5");
  public static final BigDecimal TRANSACTION_AMOUNT = new BigDecimal("123.5");

  public static final Boolean ACTIVE = Boolean.TRUE;
  public static final Boolean ADJUSTMENT_REVERSAL = Boolean.TRUE;
  public static final Boolean TRANSACTION_INSTALLMENT_PAYMENT = Boolean.TRUE;

  public static final Integer ADJUSTMENT_ACTIVATION_ID = 123;
  public static final Integer ADJUSTMENT_ID = 12345;
  public static final Integer CAMPAIGN_ID = 123;
  public static final Integer COUNT = 30;
  public static final Integer LIMIT = 25;
  public static final Integer OFFSET = 0;
  public static final Integer TOTAL = 100;
  public static final Integer TRANSACTION_ISSUER_ICA = 12345;
  public static final Integer TRANSACTION_SEQUENCE_NUMBER = 25032;

  public static final String ACTIVATED_DATE_TIME = "2019-09-25T09:43:11.000+0000";
  public static final String ACTIVATION_DATE = "2020-08-10";
  public static final String ACTIVATION_ID = "Test-Activation-Id";
  public static final String ACTIVATION_TYPE = "Test-Activation-Type";
  public static final String ACTIVE_SC = "Test-Active-Statement-Credit";
  public static final String ADJUSTMENT_ACCOUNT_ISU_ID = "Adjustment-Account-Iss-Id";
  public static final String ADJUSTMENT_BANK_ACCOUNT_NUMBER = "Adjustment-Bank-Account-Number";
  public static final String ADJUSTMENT_BANK_PRODUCT_CODE = "Adjustment-Bank-Product-Code";
  public static final String ADJUSTMENT_MERCHANT_CATEGORY_CODE = "AdjustmentMerchantCategoryCode";
  public static final String ADJUSTMENT_MODE = "CASH";
  public static final String ADJUSTMENT_POINTS_TYPE = "AdjustmentPointsType";
  public static final String ADJUSTMENT_VALUE_SIGN = "AdjustmentValueSign";
  public static final String ACCESS_TOKEN =
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaUlkIjoiOTk5OSIsInVzZXJSZWYiOiIwM2E2ZTY5OC0xZjZhLTRmNTctYjk3NC1kNTMyMzhiZDI3ZGUiLCJ1dGNPZmZzZXQiOiItMDQ6MzAiLCJpYXQiOjE2MDgyMjk4Mjc4MDV9.cE9VdkNSemFmOU9aclJRRHpRWktLaUVEVTROYXRzcDlOdzJzN084elM3QT0";
  public static final String BANK_CUSTOMER_NUMBER = "BankCustomerNumber";
  public static final String CASH_AMOUNT = "25";
  public static final String CASH_BACK = "10";
  public static final String CLIENT_ID = "";
  public static final String CREATED_DATE = "2019-09-25";
  public static final String CURRENCY_CODE = "USD";
  public static final String DATE_FILTER = "CREATED";
  public static final String DESCRIPTION_LONG = "Shop for Holiday Deals. Receive 100 points";
  public static final String DESCRIPTION_SHORT = "Shop for Holiday Deals";
  public static final String DISCOUNT = "20.0";
  public static final String DISCOUNT_TYPE = "PERCENTAGE";
  public static final String END_DATE = "2020-09-10";
  public static final String FID = "Test-FId";
  public static final String FIRST = "1";
  public static final String GOAL = "Test-Goal";
  public static final String HEADLINE = "Receive 100 points";
  public static final String ID = "a85093f3-7753-4eab-b963-c6c9d1974d1d";
  public static final String ITEMS_PER_PAGE = "25";
  public static final String LANGUAGE = "en-US";
  public static final String LAST_MODIFIED_DATE_TIME = "2020-09-10";
  public static final String MERCHANT = "Test-Merchant";
  public static final String OFFER_CATEGORY = "Test-Offer-Category";
  public static final String OFFER_COUNTRY = "USA";
  public static final String OFFER_ID = "Test-Offer-Id";
  public static final String OFFER_ID2 = "Test-Offer-Id2";
  public static final Integer OFFER_RATING = 1;
  public static final String OFFER_SOURCE = "Test-Offer-Source";
  public static final String OFFER_TYPE = "Test-Offer-Type";
  public static final String ONE = "1";
  public static final String ORIGINAL_OFFER_ID = "original-offer-id";
  public static final String POINTS_EARNED = "2";
  public static final String PRESENTMENT_DATE = "2020-09-10";
  public static final String PRICE = "0.0";
  public static final String REDEMPTION_CHANNEL = "Test-Redemption-Channel";
  public static final String REDEMPTION_DATE = "2020-08-09";
  public static final String REDEMPTION_DATETIME = "2019-09-25T09:43:11.000+0000";
  public static final String REDEMPTION_MODE = "CASH";
  public static final String REDEMPTION_STATUS_COMPLETED = "COMPLETED";
  public static final String REDEMPTION_TYPE = "ONLINE";
  public static final String SLOGAN = "Test-Slogan";
  public static final String START_DATE = "2020-10-10";
  public static final String STATEMENT_CREDIT_TYPE = "VISIT_PERCENTAGE";
  public static final String STATUS = "ACTIVATED";
  public static final String STATUS_CODE = "600";
  public static final String STATUS_MESSAGE = "Success";
  public static final String TERMS = "Test-Terms";
  public static final String TOTAL_COUNT = "2";
  public static final String TRANSACTION_ACCOUNT_ISU_ID = "Transaction-Account-Iss-Id";
  public static final String TRANSACTION_AUTHORIZATION_CODE = "TransactionAuthorizationCode";
  public static final String TRANSACTION_BANK_ACCOUNT_NUMBER = "Transaction-Bank-Account-Number";
  public static final String TRANSACTION_BANK_ACCOUNT_NUMBER_ALIAS =
      "Transaction-Bank-Account-Number-Alias";
  public static final String TRANSACTION_BANK_PRODUCT_CODE = "Transaction-Bank-Product-Code";
  public static final String TRANSACTION_CHANNEL = "Transaction-Channel";
  public static final String TRANSACTION_CITY = "TransactionCity";
  public static final String TRANSACTION_CLASSIFIER = "Test-Transaction-Classifier";
  public static final String TRANSACTION_CLASSIFIER_DISPLAY_NAME =
      "TransactionClassifierDisplayName";
  public static final String TRANSACTION_COUNTRY = "Transaction-Country";
  public static final String TRANSACTION_CURRENCY = "Transaction-Currency";
  public static final String TRANSACTION_DATE = "2019-09-25T09:43:11.000+0000";
  public static final String TRANSACTION_DESCRIPTION = "Transaction-Description";
  public static final String TRANSACTION_DESCRIPTION_ORIGINAL = "TransactionDescriptionOriginal";
  public static final String TRANSACTION_ISSUER_COUNTRY = "Transaction-Issuer-Country";
  public static final String TRANSACTION_MERCHANT_ID = "Transaction-Merchant-Id";
  public static final String TRANSACTION_TIME = "00:28:29.000";
  public static final String URL = "Test-URL";
  public static final String USER_ID = "Test-User-Id";
  public static final String USER_TOKEN = "Test-User-Token";
  public static final String UTC_OFFSET = "-04:30";
  public static final String VALUE = "Test-Value";

  public static ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers redeemedOffers() {
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

    final ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers redeemedOffersResponse =
        new ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers();

    redeemedOffersResponse.setResponse(redeemedOffers);

    return redeemedOffersResponse;
  }

  public static ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation
      activatedOffer() {
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

    ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation
        activationOfferResponse =
            new ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation();
    activationOfferResponse.setResponse(activatedOffer);

    return activationOfferResponse;
  }

  public static
  ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
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

    ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
        activationDetailResponse =
            new ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail();
    activationDetailResponse.setResponse(activationDetail);

    return activationDetailResponse;
  }

  public static ResponseWrapperUserFeedbackOutputWrapper sendUserFeedback() {
    final UserFeedbackOutputWrapper userFeedback = new UserFeedbackOutputWrapper();

    final Status status = new Status();
    status.setCode(STATUS_CODE);
    status.setMessage(STATUS_MESSAGE);
    userFeedback.setStatus(status);

    final UserFeedbackOutput userFeedbackOutput = new UserFeedbackOutput();
    userFeedbackOutput.setOfferId(OFFER_ID);
    userFeedbackOutput.setFeedback(String.valueOf(FEEDBACK));
    userFeedback.setUserFeedback(userFeedbackOutput);

    ResponseWrapperUserFeedbackOutputWrapper userFeedbackResponse =
        new ResponseWrapperUserFeedbackOutputWrapper();
    userFeedbackResponse.setResponse(userFeedback);

    return userFeedbackResponse;
  }

  public static ResponseWrapperUserFeedbackOutputList getUserFeedback() {
    final UserFeedbackOutputList userFeedback = new UserFeedbackOutputList();

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

    ResponseWrapperUserFeedbackOutputList userFeedbackResponse =
        new ResponseWrapperUserFeedbackOutputList();
    userFeedbackResponse.setResponse(userFeedback);

    return userFeedbackResponse;
  }

  public static ResponseWrapperMatchedOfferDetailsResponseMatchedOffers getMatchedOffers() {
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

    ResponseWrapperMatchedOfferDetailsResponseMatchedOffers matchedOffersResponse =
        new ResponseWrapperMatchedOfferDetailsResponseMatchedOffers();
    matchedOffersResponse.setResponse(matchedOffers);

    return matchedOffersResponse;
  }

  public static ResponseWrapperUserSavingsOutputWrapper getUserSavings() {
    final UserSavingsOutputWrapper userSavingsOutputWrapper = new UserSavingsOutputWrapper();

    final Status status = new Status();
    status.setCode(STATUS_CODE);
    status.setMessage(STATUS_MESSAGE);
    userSavingsOutputWrapper.setStatus(status);

    final UserSavings savings = getUserSavingsData();

    userSavingsOutputWrapper.setUserSavings(savings);

    ResponseWrapperUserSavingsOutputWrapper userSavingsResponse =
        new ResponseWrapperUserSavingsOutputWrapper();
    userSavingsResponse.setResponse(userSavingsOutputWrapper);

    return userSavingsResponse;
  }

  public static ResponseWrapperUserTokenOutputWrapper getUserToken() {
    final UserTokenOutputWrapper userToken = new UserTokenOutputWrapper();

    final Status status = new Status();
    status.setCode(STATUS_CODE);
    status.setMessage(STATUS_MESSAGE);
    userToken.setStatus(status);

    final UserTokenOutput token = new UserTokenOutput();
    token.setToken(USER_TOKEN);
    userToken.setUserToken(token);

    ResponseWrapperUserTokenOutputWrapper userTokenResponse =
        new ResponseWrapperUserTokenOutputWrapper();
    userTokenResponse.setResponse(userToken);

    return userTokenResponse;
  }

  public static ResponseWrapperDetailedOffersResponseDetailedOffers getOfferDetails() {
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

    ResponseWrapperDetailedOffersResponseDetailedOffers detailedOffersResponse =
        new ResponseWrapperDetailedOffersResponseDetailedOffers();
    detailedOffersResponse.setResponse(detailedOffers);

    return detailedOffersResponse;
  }

  public static BrowseOffers getOffers() {
    BrowseOffers browseOffersResponse = new BrowseOffers();
    browseOffersResponse.setOffset(FIVE);
    browseOffersResponse.setLimit(FIVE);
    browseOffersResponse.setTotal(FIVE);
    browseOffersResponse.setStatus(getStatusInfo());
    browseOffersResponse.setCount(FIVE);

    OfferInfo offerInfo = new OfferInfo();
    offerInfo.id(ONE);
    offerInfo.originalOfferId(ORIGINAL_OFFER_ID);
    offerInfo.type(OFFER_TYPE);
    offerInfo.redemptionChannel(REDEMPTION_CHANNEL);
    offerInfo.source(OFFER_SOURCE);
    offerInfo.category(OFFER_CATEGORY);
    offerInfo.currency(CURRENCY_CODE);
    offerInfo.merchant(getMerchantInfo());
    offerInfo.eventStartDate(START_DATE);
    offerInfo.eventEndDate(END_DATE);
    offerInfo.redemptionMaxPerUser(FIVE);
    offerInfo.offerCountry(OFFER_COUNTRY);
    offerInfo.matchStartDate(START_DATE);
    offerInfo.matchEndDate(END_DATE);
    offerInfo.activationType(ACTIVATION_TYPE);
    offerInfo.assignmentOnEnrollment(false);
    offerInfo.goal(GOAL);
    offerInfo.lastModified(END_DATE);
    offerInfo.matchMaxPerUser(FIVE);
    offerInfo.presentmentEndDate(END_DATE);
    offerInfo.status(STATUS);
    offerInfo.test(false);
    offerInfo.redemptionGracePeriodDays(FIVE);
    offerInfo.redemptionSpendMinPerTxn(TRANSACTION_AMOUNT);
    offerInfo.redemptionVisitThreshold(FIVE);
    offerInfo.redemptionStatementCreditType(STATEMENT_CREDIT_TYPE);

    browseOffersResponse.setOffers(Collections.singletonList(offerInfo));

    return browseOffersResponse;
  }

  public static UserAdjustment getAdjustments() {
    final UserAdjustment adjustmentResponse = new UserAdjustment();
    adjustmentResponse.setOffset(OFFSET);
    adjustmentResponse.setLimit(LIMIT);
    adjustmentResponse.setTotal(TOTAL);
    adjustmentResponse.setCount(COUNT);
    adjustmentResponse.setResponseLastModifiedDateTime(LAST_MODIFIED_DATE_TIME);
    adjustmentResponse.setStatus(getStatusInfo());

    Adjustments adjustments = getAdjustmentsData();

    adjustmentResponse.setAdjustments(Collections.singletonList(adjustments));
    return adjustmentResponse;
  }

  public static RequestedAccessToken getAccessTokenRequest() {
    RequestedAccessToken accessTokenRequest = new RequestedAccessToken();
    accessTokenRequest.setFiId(FID);
    accessTokenRequest.setUserId(USER_ID);
    accessTokenRequest.setUtcOffset(UTC_OFFSET);
    return accessTokenRequest;
  }

  public static UserAccessToken getAccessToken() {
    UserAccessToken accessTokenResponse = new UserAccessToken();
    accessTokenResponse.setAccessToken(ACCESS_TOKEN);
    return accessTokenResponse;
  }

  public static UserOffers getUserPresentmentOffers() {
    UserOffers offersResponse = new UserOffers();
    offersResponse.setOffset(OFFSET);
    offersResponse.setLimit(LIMIT);
    offersResponse.setTotal(TOTAL);
    offersResponse.setCount(COUNT);

    UserOffer offer = new UserOffer();
    offer.setId(OFFER_ID);
    offer.setOriginalOfferId(ORIGINAL_OFFER_ID);
    offer.setType(OFFER_TYPE);
    offer.setRedemptionChannel(REDEMPTION_CHANNEL);
    offer.setSource(OFFER_SOURCE);
    offer.setCategory(OFFER_CATEGORY);
    offer.setCurrency(CURRENCY_CODE);
    offer.setMerchant(getMerchantInfo());
    offer.setRedemptionSpendThreshold(SPEND_THRESHOLD);
    offer.setEventStartDate(START_DATE);
    offer.setEventEndDate(END_DATE);
    offer.setRedemptionMaxPerUser(FIVE);
    offer.setOfferCountry(OFFER_COUNTRY);
    offer.setMatchStartDate(START_DATE);
    offer.setMatchEndDate(END_DATE);
    offer.setActivationType(ACTIVATION_TYPE);
    offer.setAssignmentOnEnrollment(false);
    offer.setGoal(GOAL);
    offer.setLastModified(LAST_MODIFIED_DATE_TIME);
    offer.setMatchMaxPerUser(FIVE);
    offer.setPresentmentEndDate(PRESENTMENT_DATE);
    offer.setStatus(STATUS);
    offer.setTest(false);
    offer.setRedemptionGracePeriodDays(FIVE);
    offer.setRedemptionSpendMinPerTxn(TRANSACTION_AMOUNT);
    offer.setRedemptionVisitThreshold(FIVE);
    offer.setRedemptionStatementCreditType(STATEMENT_CREDIT_TYPE);
    offer.setRewards(Collections.singletonList(getReward()));
    offer.setLocalizations(Collections.singletonList(getLocalization()));
    offer.setRedemptionClassifiers(getRedemptionClassifiers());
    offer.setAdjustmentBankProductCodes(Collections.emptyList());

    offersResponse.setOffers(Collections.singletonList(offer));
    return offersResponse;
  }

  public static OfferDetails getUserOfferDetails() {
    UserOfferDetails offerDetails = new UserOfferDetails();
    offerDetails.setId(OFFER_ID);
    offerDetails.setOriginalOfferId(ORIGINAL_OFFER_ID);
    offerDetails.setType(OFFER_TYPE);
    offerDetails.setRedemptionChannel(REDEMPTION_CHANNEL);
    offerDetails.setSource(OFFER_SOURCE);
    offerDetails.setCategory(OFFER_CATEGORY);
    offerDetails.setCurrency(CURRENCY_CODE);
    offerDetails.setMerchant(getMerchantInfo());
    offerDetails.setRedemptionSpendThreshold(SPEND_THRESHOLD);
    offerDetails.setEventStartDate(START_DATE);
    offerDetails.setEventEndDate(END_DATE);
    offerDetails.setRedemptionMaxPerUser(FIVE);
    offerDetails.setOfferCountry(OFFER_COUNTRY);
    offerDetails.setMatchStartDate(START_DATE);
    offerDetails.setMatchEndDate(END_DATE);
    offerDetails.setActivationType(ACTIVATION_TYPE);
    offerDetails.setAssignmentOnEnrollment(false);
    offerDetails.setGoal(GOAL);
    offerDetails.setLastModified(LAST_MODIFIED_DATE_TIME);
    offerDetails.setMatchMaxPerUser(FIVE);
    offerDetails.setPresentmentEndDate(PRESENTMENT_DATE);
    offerDetails.setStatus(STATUS);
    offerDetails.setTest(false);
    offerDetails.setRedemptionGracePeriodDays(FIVE);
    offerDetails.setRedemptionSpendMinPerTxn(TRANSACTION_AMOUNT);
    offerDetails.setRedemptionVisitThreshold(FIVE);
    offerDetails.setRedemptionStatementCreditType(STATEMENT_CREDIT_TYPE);
    offerDetails.setRewards(Collections.singletonList(getReward()));
    offerDetails.setLocalizations(Collections.singletonList(getLocalization()));
    offerDetails.setRedemptionClassifiers(getRedemptionClassifiers());
    offerDetails.setAdjustmentBankProductCodes(Collections.emptyList());

    OfferDetails userOfferDetailsResponse = new OfferDetails();
    userOfferDetailsResponse.setUserOfferDetails(offerDetails);
    return userOfferDetailsResponse;
  }

  private static Localization getLocalization() {
    Localization localization = new Localization();
    localization.setDescriptionLong(DESCRIPTION_LONG);
    localization.setDescriptionShort(DESCRIPTION_SHORT);
    localization.setHeadline(HEADLINE);
    localization.setLang(LANGUAGE);
    localization.setMerchantDisplayName(MERCHANT);
    localization.setStoreLocatorURL(URL);
    localization.setTermsDetailed(TERMS);
    localization.setTermsKey(TERMS);
    localization.setWebsiteURL(URL);
    localization.marketingSlogan(SLOGAN);
    localization.name(MERCHANT);
    return localization;
  }

  private static MerchantInfo getMerchantInfo() {
    final MerchantInfo merchant = new MerchantInfo();
    merchant.setName(MERCHANT);
    return merchant;
  }

  private static RedemptionClassifiers getRedemptionClassifiers() {
    final RedemptionClassifiers redemptionClassifiers = new RedemptionClassifiers();
    redemptionClassifiers.setPrimaryValue(VALUE);
    redemptionClassifiers.setValues(Collections.singletonList(VALUE));
    return redemptionClassifiers;
  }

  private static Reward getReward() {
    final Reward reward = new Reward();
    reward.setPrimary(true);
    reward.setCashValueAbsolute(ADJUSTMENT_CASH_VALUE);
    reward.setDiscountType(DISCOUNT_TYPE);
    reward.setMode(REDEMPTION_MODE);
    reward.setType(STATEMENT_CREDIT_TYPE);
    return reward;
  }

  private static StatusInfo getStatusInfo() {
    final StatusInfo status = new StatusInfo();
    status.setCode(Integer.valueOf(STATUS_CODE));
    status.setMessage(STATUS_MESSAGE);
    return status;
  }

  public static UserSavings getUserPresentmentSavings() {
    final UserSavings userPresentmentSavingsResponse = new UserSavings();

    final Savings savings = getSavingsData();

    userPresentmentSavingsResponse.setSavings(savings);

    return userPresentmentSavingsResponse;
  }

  @NotNull
  private static UserSavings getUserSavingsData() {
    final Savings savings =
        new Savings();
    savings.setOffersRedeemed(Integer.valueOf(ONE));
    savings.setAdjustmentsCashTotal(new BigDecimal(CASH_BACK));
    savings.setOffersAvailable(Integer.valueOf(ONE));
    savings.setPotentialCash(new BigDecimal(ONE));
    savings.setPotentialPoints(new BigDecimal(POINTS_EARNED));
    savings.setAdjustmentsPointsTotal(new BigDecimal(POINTS_EARNED));

    final UserSavings userSavings = new UserSavings();
    userSavings.setSavings(savings);

    return userSavings;
  }

  public static UserAdjustments getUserAdjustments() {
    final UserAdjustments adjustmentResponse = new UserAdjustments();
    adjustmentResponse.setOffset(OFFSET);
    adjustmentResponse.setLimit(LIMIT);
    adjustmentResponse.setTotal(TOTAL);
    adjustmentResponse.setCount(COUNT);
    adjustmentResponse.setResponseLastModifiedDateTime(LAST_MODIFIED_DATE_TIME);

    Adjustments adjustments = getAdjustmentsData();

    adjustmentResponse.setAdjustments(Collections.singletonList(adjustments));
    return adjustmentResponse;
  }

  @NotNull
  private static Adjustments getAdjustmentsData() {
    Adjustments adjustments = new Adjustments();
    adjustments.setId(UUID.fromString(ID));
    adjustments.setAdjustmentId(ADJUSTMENT_ID);
    adjustments.setActivationId(ADJUSTMENT_ACTIVATION_ID);
    adjustments.setActivatedDateTime(ACTIVATED_DATE_TIME);
    adjustments.setTransactionClassifier(TRANSACTION_CLASSIFIER);
    adjustments.setOfferId(OFFER_ID);
    adjustments.setAdjustmentMode(ADJUSTMENT_MODE);
    adjustments.setTransactionBankAccountNumber(TRANSACTION_BANK_ACCOUNT_NUMBER);
    adjustments.setTransactionAmount(TRANSACTION_AMOUNT);
    adjustments.setTransactionDate(TRANSACTION_DATE);
    adjustments.setTransactionTime(TRANSACTION_TIME);
    adjustments.setTransactionDescription(TRANSACTION_DESCRIPTION);
    adjustments.setTransactionSequenceNumber(TRANSACTION_SEQUENCE_NUMBER);
    adjustments.setAdjustmentCashValue(ADJUSTMENT_CASH_VALUE);
    adjustments.setRedemptionDateTime(REDEMPTION_DATETIME);
    adjustments.setStatus(REDEMPTION_STATUS_COMPLETED);
    adjustments.setAdjustmentPointsValue(ADJUSTMENT_POINTS_VALUE);
    adjustments.setTransactionMerchantId(TRANSACTION_MERCHANT_ID);
    adjustments.setTransactionChannel(TRANSACTION_CHANNEL);
    adjustments.setOfferType(OFFER_TYPE);
    adjustments.setTransactionAccountISUID(TRANSACTION_ACCOUNT_ISU_ID);
    adjustments.setTransactionBankProductCode(TRANSACTION_BANK_PRODUCT_CODE);
    adjustments.setAdjustmentBankAccountNumber(ADJUSTMENT_BANK_ACCOUNT_NUMBER);
    adjustments.setAdjustmentBankProductCode(ADJUSTMENT_BANK_PRODUCT_CODE);
    adjustments.setAdjustmentAccountISUID(ADJUSTMENT_ACCOUNT_ISU_ID);
    adjustments.setTransactionCountry(TRANSACTION_COUNTRY);
    adjustments.setTransactionCurrency(TRANSACTION_CURRENCY);
    adjustments.setTransactionBankAccountNumberAlias(TRANSACTION_BANK_ACCOUNT_NUMBER_ALIAS);
    adjustments.setTransactionInstallmentPayment(TRANSACTION_INSTALLMENT_PAYMENT);
    adjustments.setTransactionIssuerCountry(TRANSACTION_ISSUER_COUNTRY);
    adjustments.setCreatedDate(CREATED_DATE);
    adjustments.setLastModifiedDateTime(LAST_MODIFIED_DATE_TIME);
    adjustments.setBankCustomerNumber(BANK_CUSTOMER_NUMBER);
    adjustments.setTransactionAuthorizationCode(TRANSACTION_AUTHORIZATION_CODE);
    adjustments.setTransactionDescriptionOriginal(TRANSACTION_DESCRIPTION_ORIGINAL);
    adjustments.setTransactionCity(TRANSACTION_CITY);
    adjustments.setAdjustmentReversal(ADJUSTMENT_REVERSAL);
    adjustments.setAdjustmentValueSign(ADJUSTMENT_VALUE_SIGN);
    adjustments.setAdjustmentMerchantCategoryCode(ADJUSTMENT_MERCHANT_CATEGORY_CODE);
    adjustments.setPointsConversionRate(POINTS_CONVERSION_RATE);
    adjustments.setTransactionClassifierDisplayName(TRANSACTION_CLASSIFIER_DISPLAY_NAME);
    adjustments.setCampaignId(CAMPAIGN_ID);
    adjustments.setTransactionIssuerICA(TRANSACTION_ISSUER_ICA);
    adjustments.setAdjustmentPointsType(ADJUSTMENT_POINTS_TYPE);
    return adjustments;
  }

  public static RequestedActivation getRequestedActivation() {
    RequestedActivation requestedActivation = new RequestedActivation();
    requestedActivation.setOfferId(OFFER_ID);
    return requestedActivation;
  }

  public static Activations getActivations() {
    Activations activations = new Activations();
    Activation activation = new Activation();
    activation.setId(ACTIVATION_ID);
    activation.setOfferId(OFFER_ID);
    activation.setStatus(STATUS);
    UserAssignment assignment = new UserAssignment();
    assignment.setStatus(STATUS);
    assignment.setRedemptionEndDate(END_DATE);
    assignment.setRedemptionStartDate(START_DATE);
    assignment.setActivationDateTime(START_DATE);
    assignment.setId(ID);
    List<UserAssignment> assignments = new ArrayList<>();
    assignments.add(assignment);
    activation.setAssignments(assignments);
    List<Activation> activationList = new ArrayList<>();
    activationList.add(activation);
    activations.setActivations(activationList);
    return activations;
  }

  public static RequestedOfferRating getOfferRatingRequest() {
    RequestedOfferRating offerRatingRequest = new RequestedOfferRating();
    offerRatingRequest.setLike(OFFER_RATING);
    return offerRatingRequest;
  }

  public static UserOfferRating getUserOfferRating() {
    UserOfferRating offerRatingResponse = new UserOfferRating();
    OfferRating offerRating = getOfferRating();
    offerRatingResponse.setOfferRating(offerRating);
    return offerRatingResponse;
  }

  public static UserOfferRatings getUserOfferRatings() {
    UserOfferRatings offerRatingsResponse = new UserOfferRatings();
    offerRatingsResponse.setOffset(OFFSET);
    offerRatingsResponse.setLimit(LIMIT);
    offerRatingsResponse.setTotal(TOTAL);
    offerRatingsResponse.setCount(COUNT);

    OfferRating offerRating = getOfferRating();
    List<OfferRating> offerRatings = new ArrayList<>();
    offerRatings.add(offerRating);
    offerRatingsResponse.setOfferRatings(offerRatings);
    return offerRatingsResponse;
  }

  @NotNull
  private static OfferRating getOfferRating() {
    OfferRating offerRating = new OfferRating();
    offerRating.setOfferId(OFFER_ID);
    offerRating.setLike(OFFER_RATING);
    return offerRating;
  }

  @NotNull
  private static Savings getSavingsData() {
    final Savings savings = new Savings();
    savings.setOffersAvailable(Integer.valueOf(ONE));
    savings.setPotentialCash(new BigDecimal(CASH_BACK));
    savings.setOffersRedeemed(Integer.valueOf(ONE));
    savings.setPotentialPoints(new BigDecimal(POINTS_EARNED));
    savings.setAdjustmentsPointsTotal(new BigDecimal(POINTS_EARNED));
    savings.setAdjustmentsCashTotal(new BigDecimal(CASH_BACK));
    return savings;
  }
}
