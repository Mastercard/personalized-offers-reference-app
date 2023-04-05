package com.mastercard.developer.usecases;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mastercard.ApiException;
import com.mastercard.api.model.OfferDetails;
import com.mastercard.api.model.OfferRating;
import com.mastercard.api.model.RequestedAccessToken;
import com.mastercard.api.model.RequestedActivation;
import com.mastercard.api.model.RequestedOfferRating;
import com.mastercard.api.model.UserAccessToken;
import com.mastercard.api.model.UserAdjustments;
import com.mastercard.api.model.UserOffer;
import com.mastercard.api.model.UserOfferRating;
import com.mastercard.api.model.UserOfferRatings;
import com.mastercard.api.model.UserOffers;
import com.mastercard.api.model.UserSavings;
import com.mastercard.developer.constant.Constant;
import com.mastercard.developer.service.PersonalizedOffersService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserPresentmentServiceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserPresentmentServiceTest.class);
  private static final String API_CALL_FAILED_WITH_ERROR_MSG =
      "The API call failed with error msg : {}";

  @Autowired private PersonalizedOffersService personalizedOffersService;

  @Value("${test.data.f-id}")
  private String fId;

  @Value("${test.data.user-id}")
  private String userId;

  private static String accessToken;

  private static String offerId;

  /** Use case 1. Retrieve Access Token */
  @Test
  @DisplayName("Retrieve access-token for the user")
  @Order(1)
  void retrieveAccessToken() {

    RequestedAccessToken requestedAccessToken =
        new RequestedAccessToken().fiId(fId).userId(userId).utcOffset(Constant.UTC_OFFSET);

    try {

      UserAccessToken userAccessToken = personalizedOffersService.getToken(requestedAccessToken);

      assertNotNull(userAccessToken);
      assertNotNull(userAccessToken.getAccessToken());
      LOGGER.info("Retrieve access-token for the user : {}", userAccessToken);

      setAccessToken(userAccessToken.getAccessToken());

    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 2. Retrieve User Offers */
  @Test
  @DisplayName("Retrieve user offers")
  @Order(2)
  void getUserOffers() {

    try {
      UserOffers userOffers =
          personalizedOffersService.getOffers(
                  Constant.Offers.EN_HYPEN_US,
                  Constant.Offers.OFFER_TYPE_POSTPAIDCREDIT,
                  Constant.Offers.CATEGORY_SHOP,
                  Constant.Offers.COUNTRY_USA,
                  Constant.OFFSET, Constant.LIMIT_FIVE,
                  accessToken);

      assertNotNull(userOffers);
      assertNotNull(userOffers.getOffers());
      LOGGER.info("Retrieve user offers : {}", userOffers);

      // Using the first user matched offer in subsequent use cases
      setOfferId(userOffers.getOffers().get(0).getId());

    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /**
   * Use case 3. Activate Offer
   *
   * <p>A user can activate a matchedOffer, an offer can be activated only once by a user the sample
   * offer used in this scenario is already activated and the expected response is - 400 Bad Request
   * with "Offer has already been activated" message
   */
  @Test
  @DisplayName("Activate Offer")
  @Order(3)
  void activateOffer() {

    RequestedActivation requestedActivation = new RequestedActivation().offerId(offerId);

    try {
      personalizedOffersService.processActivations(requestedActivation, accessToken);

    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      assertTrue(e.getResponseBody().contains("Offer is already ACTIVATED"));
    }
  }

  /** Use case 4. Offer Rating */
  @Test
  @DisplayName("Offer Rating")
  @Order(4)
  void offerRating() {

    RequestedOfferRating requestedOfferRating = new RequestedOfferRating().like(Constant.OfferRating.LIKE);

    try {
      UserOfferRating userOfferRating =
          personalizedOffersService.createOfferRating(offerId, requestedOfferRating, accessToken);
      assertNotNull(userOfferRating);
      LOGGER.info("Create user offer rating : {}", userOfferRating);

      UserOfferRating userOfferRatingOutput =
          personalizedOffersService.getOfferRating(offerId, accessToken);
      assertNotNull(userOfferRatingOutput);
      LOGGER.info("Retrieve the user offer rating : {}", userOfferRatingOutput);

    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 5. Retrieve Offer Ratings */
  @Test
  @DisplayName("Retrieve offer ratings")
  @Order(5)
  void getRatings() {

    try {
      UserOfferRatings userOfferRatings =
          personalizedOffersService.getOfferRatings(
                  Constant.OfferRating.CURRENT_FALSE,
                  Constant.OFFSET,
                  Constant.LIMIT_TEN,
                  accessToken);

      assertNotNull(userOfferRatings);

      LOGGER.info("Retrieve the user offer ratings : {}", userOfferRatings);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 6. Retrieve Offer */
  @Test
  @DisplayName("Retrieve user offer")
  @Order(6)
  void getOfferDetails() {

    try {
      OfferDetails offerDetails =
          personalizedOffersService.getOfferDetails(offerId, accessToken, null);

      assertNotNull(offerDetails);

      LOGGER.info("Retrieve the user offer : {}", offerDetails);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 7. Retrieve User Adjustments */
  @Test
  @DisplayName("Retrieve user adjustments")
  @Order(7)
  void getUserAdjustments() {

    try {
      UserAdjustments userAdjustments =
          personalizedOffersService.getUserPresentmentAdjustments(
                  Constant.UserAdjustments.START_DATE,
                  Constant.UserAdjustments.END_DATE,
                  Constant.UserAdjustments.DATE_FILTER,
                  Constant.OFFSET,
                  Constant.LIMIT_FIVE,
                  accessToken);

      assertNotNull(userAdjustments);

      LOGGER.info("Retrieve the user adjustments : {}", userAdjustments);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 8. Retrieve User Savings */
  @Test
  @DisplayName("Retrieve user savings")
  @Order(8)
  void getUserSavings() {

    try {
      UserSavings userPresentmentSavingsResponse =
          personalizedOffersService.getUserPresentmentSavings(accessToken);

      assertNotNull(userPresentmentSavingsResponse);

      LOGGER.info("Retrieve the user savings : {}", userPresentmentSavingsResponse);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /**
   * Use case - Retrieve Matched, Liked and Active offers
   *
   * <p>A use case showing how to retrieve matched offers that are liked (rated good) and currently
   * active offers
   */
  @Test
  @DisplayName("Retrieve matched, liked and current offers")
  @Order(9)
  void getMatchedLikedCurrentOffers() {

    try {
      UserOffers userOffers =
          personalizedOffersService.getOffers(
                  Constant.Offers.EN_HYPEN_US,
                  Constant.Offers.OFFER_TYPE_POSTPAIDCREDIT,
                  Constant.Offers.CATEGORY_SHOP,
                  Constant.Offers.COUNTRY_USA,
                  Constant.OFFSET,
                  Constant.LIMIT_FIVE,
                  accessToken);
      assertNotNull(userOffers.getOffers());

      UserOfferRatings userOfferRatings =
          personalizedOffersService.getOfferRatings(
                  Constant.OfferRating.CURRENT_TRUE,
                  Constant.OFFSET,
                  Constant.LIMIT_TEN,
                  accessToken);
      assertNotNull(userOfferRatings.getOfferRatings());

      Set<String> likedOffers = getLikedOffers(userOfferRatings.getOfferRatings());
      List<UserOffer> likedMatchedOffers =
          userOffers.getOffers().stream()
              .filter(offerInfo -> likedOffers.contains(offerInfo.getId()))
              .collect(Collectors.toList());
      assertNotNull(likedMatchedOffers);

      LOGGER.info(
          "Retrieve the matched, liked and currently active offers : {}", likedMatchedOffers);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  private Set<String> getLikedOffers(List<OfferRating> offerRatings) {
    return offerRatings.stream()
        .filter(c -> Integer.valueOf(1).equals(c.getLike()))
        .map(OfferRating::getOfferId)
        .collect(Collectors.toSet());
  }

  private static void setAccessToken(String accessToken) {
    UserPresentmentServiceTest.accessToken = accessToken;
  }

  private static void setOfferId(String offerId) {
    UserPresentmentServiceTest.offerId = offerId;
  }
}
