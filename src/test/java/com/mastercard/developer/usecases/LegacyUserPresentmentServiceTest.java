package com.mastercard.developer.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mastercard.ApiException;
import com.mastercard.api.model.ActivateSCOfferInputStatementCreditOfferActivation;
import com.mastercard.api.model.ResponseWrapperDetailedOffersResponseDetailedOffers;
import com.mastercard.api.model.ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers;
import com.mastercard.api.model.ResponseWrapperMatchedOfferDetailsResponseMatchedOffers;
import com.mastercard.api.model.ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation;
import com.mastercard.api.model.ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail;
import com.mastercard.api.model.ResponseWrapperUserFeedbackOutputList;
import com.mastercard.api.model.ResponseWrapperUserFeedbackOutputWrapper;
import com.mastercard.api.model.ResponseWrapperUserSavingsOutputWrapper;
import com.mastercard.api.model.ResponseWrapperUserTokenOutputWrapper;
import com.mastercard.api.model.UserFeedbackInput;
import com.mastercard.developer.constant.Constant;
import com.mastercard.developer.service.PersonalizedOffersService;
import com.mastercard.developer.service.domain.GenericOffersCriterion;
import com.mastercard.developer.service.domain.MatchedOffersCriterion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * The following APIs are deprecated version of the current User Presentment APIs that are listed under UserPresentmentServiceTest.java
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LegacyUserPresentmentServiceTest {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LegacyUserPresentmentServiceTest.class);
  private static final String API_CALL_FAILED_WITH_ERROR_MSG =
      "The API call failed with error msg : {}";

  @Autowired private PersonalizedOffersService personalizedOffersService;

  @Value("${test.data.f-id}")
  private String fId;

  private static String userToken;

  private static String offerId;

  /** Use case 6. Retrieve User Token */
  @Test
  @DisplayName("Return user-token for the user")
  @Order(1)
  void getUserToken() {

    try {

      ResponseWrapperUserTokenOutputWrapper userToken = personalizedOffersService.getUserToken(fId);

      assertNotNull(userToken.getResponse());
      assertNotNull(userToken.getResponse().getUserToken());
      LOGGER.info("Retrieve user-token for the user : {}", userToken);

      setUserToken(userToken.getResponse().getUserToken().getToken());

    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 4. Retrieve Personalized Offers */
  @Test
  @DisplayName("Retrieve the user matched offers")
  @Order(2)
  void getMatchedOffers() {

    MatchedOffersCriterion matchedOffersCriterion =
        MatchedOffersCriterion.builder()
            .fid(fId)
            .merchantName(Constant.Offers.MERCHANT_NAME)
            .category(Constant.Offers.CATEGORY_SHOP)
            .offerType(Constant.Offers.OFFER_TYPE_POSTPAIDCREDIT)
            .pageNumber(Constant.PAGE_NUMBER)
            .itemsPerPage(Constant.LIMIT_FIVE)
            .lang(Constant.Offers.EN_US)
            .userToken(userToken)
            .build();

    try {
      ResponseWrapperMatchedOfferDetailsResponseMatchedOffers matchedOffers =
          personalizedOffersService.getMatchedOffers(matchedOffersCriterion);

      assertNotNull(matchedOffers.getResponse());
      assertNotNull(matchedOffers.getResponse().getItems());
      assertNotNull(matchedOffers.getResponse().getItems().getMatchedOffer());
      LOGGER.info("Retrieve the user matched offers : {}", matchedOffers);

      // Using the first user matched offer in subsequent use cases
      setOfferId(matchedOffers.getResponse().getItems().getMatchedOffer().get(0).getOfferId());

    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /**
   * Use case 2. Activate an Offer
   *
   * <p>A user can activate a matchedOffer, an offer can be activated only once by a user the offer
   * used in this scenario is already activated and gives response containing status message "Bad
   * Request" A user can also retrieve extended information about the specified activated Postpaid
   * Credit offer
   */
  @Test
  @DisplayName("Activate a Statement Credit Offer")
  @Order(3)
  void activateStatementCreditOffer() {

    ActivateSCOfferInputStatementCreditOfferActivation activationStatementCredit =
        new ActivateSCOfferInputStatementCreditOfferActivation()
            .fid(fId)
            .offerId(offerId)
            .lang(Constant.Offers.EN_US)
            .presentmentDate(Constant.StatementCreditOffer.PRESENTMENT_DATE)
            .userToken(userToken);

    try {
      ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation
          activateOffer =
              personalizedOffersService.activateStatementCreditOffer(activationStatementCredit);

      assertNotNull(activateOffer.getResponse());
      assertNotNull(activateOffer.getResponse().getStatus());
      assertEquals("Bad Request", activateOffer.getResponse().getStatus().getMessage());
      LOGGER.info("Activate a Statement Credit Offer : {}", activateOffer);

      ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
          activatedOffer =
              personalizedOffersService.getStatementCreditActivationDetail(
                  GenericOffersCriterion.builder()
                      .fid(fId)
                      .activationId(Constant.StatementCreditOffer.ACTIVATION_ID)
                      .lang(Constant.Offers.EN_US)
                      .userToken(userToken)
                      .build());
      assertNotNull(activatedOffer);
      LOGGER.info("Retrieve the activated redeemable Postpaid Credit Offer : {}", activatedOffer);

    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 3. User Feedback */
  @Test
  @DisplayName("User feedback")
  @Order(4)
  void userFeedback() {

    UserFeedbackInput userFeedbackInput =
        new UserFeedbackInput().fid(fId).userToken(userToken).offerId(offerId).feedback(Constant.UserFeedback.FEEDBACK);

    try {
      ResponseWrapperUserFeedbackOutputWrapper userFeedback =
          personalizedOffersService.sendUserFeedback(userFeedbackInput);
      assertNotNull(userFeedback);
      LOGGER.info("Create the user feedback : {}", userFeedback);

      ResponseWrapperUserFeedbackOutputList userFeedbackOutput =
          personalizedOffersService.getUserFeedback(
              GenericOffersCriterion.builder()
                  .fid(fId)
                  .userToken(userToken)
                  .offerId(offerId)
                  .feedback(Constant.UserFeedback.FEEDBACK.toString())
                  .build());
      assertNotNull(userFeedbackOutput);
      LOGGER.info("Retrieve the user feedback : {}", userFeedbackOutput);

    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 3. Retrieve Offer Details */
  @Test
  @DisplayName("Retrieve the offer details")
  @Order(5)
  void getOfferDetails() {

    GenericOffersCriterion offerDetailsCriterion =
        GenericOffersCriterion.builder()
            .fid(fId)
            .offerId(offerId)
            .lang(Constant.Offers.EN_US)
            .userToken(userToken)
            .build();

    try {
      ResponseWrapperDetailedOffersResponseDetailedOffers offerDetails =
          personalizedOffersService.getOfferDetails(offerDetailsCriterion);

      assertNotNull(offerDetails);

      LOGGER.info("Retrieve the offer details : {}", offerDetails);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 1. Retrieve Redeemed Offers */
  @Test
  @DisplayName("Retrieve the redeemed offers")
  @Order(6)
  void getRedeemedOffers() {

    GenericOffersCriterion genericOffersCriterion =
        GenericOffersCriterion.builder()
            .fid(fId)
            .itemsPerPage(Constant.LIMIT_FIVE)
            .pageNumber(Constant.PAGE_NUMBER)
            .lang(Constant.Offers.EN_US)
            .userToken(userToken)
            .build();

    try {
      ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers redeemedOffers =
          personalizedOffersService.getRedeemedOffers(genericOffersCriterion);

      assertNotNull(redeemedOffers);

      LOGGER.info("Retrieve the redeemed offers : {}", redeemedOffers);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 5. Show the Total User Savings */
  @Test
  @DisplayName("Show the total user savings")
  @Order(7)
  void getUserSavings() {

    GenericOffersCriterion genericOffersCriterion =
        GenericOffersCriterion.builder().fid(fId).userToken(userToken).build();

    try {
      ResponseWrapperUserSavingsOutputWrapper userSavings =
          personalizedOffersService.getUserSavings(genericOffersCriterion);

      assertNotNull(userSavings);

      LOGGER.info("Show the total user savings : {}", userSavings);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  private static void setUserToken(String userToken) {
    LegacyUserPresentmentServiceTest.userToken = userToken;
  }

  private static void setOfferId(String offerId) {
    LegacyUserPresentmentServiceTest.offerId = offerId;
  }
}
