package com.mastercard.developer.integration;

import com.google.gson.Gson;
import com.mastercard.api.model.ActivateSCOfferInputStatementCreditOfferActivation;
import com.mastercard.api.model.UserFeedbackInput;
import com.mastercard.developer.controller.PersonalizedOffersController;
import com.mastercard.developer.integration.data.PersonalizedOffersData;
import com.mastercard.developer.service.PersonalizedOffersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(
        classes = {PersonalizedOffersController.class, PersonalizedOffersService.class})
@WebMvcTest
class PersonalizedOffersControllerTest {

  private final Gson gson;
  private final MockMvc mockMvc;

  @MockBean PersonalizedOffersService referenceApplicationGateway;

  @Autowired
  PersonalizedOffersControllerTest(final Gson gson, final MockMvc mockMvc) {
    this.gson = gson;
    this.mockMvc = mockMvc;
  }

  @Test
  @DisplayName("GET /redeem-offers")
  void redeemOffers() throws Exception {
    when(referenceApplicationGateway.getRedeemedOffers(anyString()))
            .thenReturn(PersonalizedOffersData.redeemedOffers());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/redeem-offers")
                            .param("f_id", PersonalizedOffersData.FID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response.status.code", is(PersonalizedOffersData.STATUS_CODE)))
            .andExpect(jsonPath("$.response.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
            .andExpect(jsonPath("$.response.numberOfPages", is("1")))
            .andExpect(jsonPath("$.response.itemsPerPage", is("25")))
            .andExpect(jsonPath("$.response.totalCount", is("1")));
  }

  @Test
  @DisplayName("POST /activate-offers")
  void activateOffers() throws Exception {
    ActivateSCOfferInputStatementCreditOfferActivation inputStatementCreditOfferActivation =
            new ActivateSCOfferInputStatementCreditOfferActivation();
    inputStatementCreditOfferActivation.setFid(PersonalizedOffersData.FID);
    inputStatementCreditOfferActivation.setOfferId(PersonalizedOffersData.OFFER_ID);
    inputStatementCreditOfferActivation.setUserToken(PersonalizedOffersData.USER_TOKEN);

    when(referenceApplicationGateway.activateStatementCreditOffer(any()))
            .thenReturn(PersonalizedOffersData.activatedOffer());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.post("/activate-offers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(inputStatementCreditOfferActivation)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response.status.code", is(PersonalizedOffersData.STATUS_CODE)))
            .andExpect(jsonPath("$.response.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.activationId", is(PersonalizedOffersData.ACTIVATION_ID)))
            .andExpect(jsonPath("$.response.scActivation.status", is(PersonalizedOffersData.STATUS)))
            .andExpect(jsonPath("$.response.scActivation.offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.activationDate",
                            is(PersonalizedOffersData.ACTIVATION_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.redemptionDate",
                            is(PersonalizedOffersData.REDEMPTION_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.redemptionEndDate", is(PersonalizedOffersData.END_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.redemptionStartDate",
                            is(PersonalizedOffersData.START_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.redemptionMode",
                            is(PersonalizedOffersData.REDEMPTION_MODE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.pointsEarned", is(PersonalizedOffersData.POINTS_EARNED)))
            .andExpect(
                    jsonPath("$.response.scActivation.merchant", is(PersonalizedOffersData.MERCHANT)))
            .andExpect(
                    jsonPath("$.response.scActivation.cashBack", is(PersonalizedOffersData.CASH_BACK)));
  }

  @Test
  @DisplayName("GET /activation-details")
  void activationDetails() throws Exception {
    when(referenceApplicationGateway.getStatementCreditActivationDetail(anyString(), anyString()))
            .thenReturn(PersonalizedOffersData.activationDetails());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/activation-details")
                            .param("f_id", PersonalizedOffersData.FID)
                            .param("activation_id", PersonalizedOffersData.ACTIVATION_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response.status.code", is(PersonalizedOffersData.STATUS_CODE)))
            .andExpect(jsonPath("$.response.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.activationId", is(PersonalizedOffersData.ACTIVATION_ID)))
            .andExpect(jsonPath("$.response.scActivation.status", is(PersonalizedOffersData.STATUS)))
            .andExpect(jsonPath("$.response.scActivation.offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.activationDate",
                            is(PersonalizedOffersData.ACTIVATION_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.redemptionDate",
                            is(PersonalizedOffersData.REDEMPTION_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.redemptionStartDate",
                            is(PersonalizedOffersData.START_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.redemptionMode",
                            is(PersonalizedOffersData.REDEMPTION_MODE)))
            .andExpect(
                    jsonPath(
                            "$.response.scActivation.pointsEarned", is(PersonalizedOffersData.POINTS_EARNED)))
            .andExpect(
                    jsonPath("$.response.scActivation.merchant", is(PersonalizedOffersData.MERCHANT)))
            .andExpect(
                    jsonPath("$.response.scActivation.cashBack", is(PersonalizedOffersData.CASH_BACK)));
  }

  @Test
  @DisplayName("POST /user-feedbacks")
  void sendFeedback() throws Exception {
    UserFeedbackInput userFeedbackInput = new UserFeedbackInput();
    userFeedbackInput.setFid(PersonalizedOffersData.FID);
    userFeedbackInput.setOfferId(PersonalizedOffersData.OFFER_ID);
    userFeedbackInput.setUserToken(PersonalizedOffersData.USER_TOKEN);
    userFeedbackInput.setFeedback(PersonalizedOffersData.FEEDBACK);

    when(referenceApplicationGateway.sendUserFeedback(any()))
            .thenReturn(PersonalizedOffersData.sendUserFeedback());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.post("/user-feedbacks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(userFeedbackInput)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response.status.code", is(PersonalizedOffersData.STATUS_CODE)))
            .andExpect(jsonPath("$.response.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
            .andExpect(jsonPath("$.response.userFeedback.offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(
                    jsonPath(
                            "$.response.userFeedback.feedback",
                            is(String.valueOf(PersonalizedOffersData.FEEDBACK))));
  }

  @Test
  @DisplayName("GET /user-feedbacks")
  void userFeedback() throws Exception {
    when(referenceApplicationGateway.getUserFeedback(anyString()))
            .thenReturn(PersonalizedOffersData.getUserFeedback());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/user-feedbacks")
                            .param("f_id", PersonalizedOffersData.FID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response.status.code", is(PersonalizedOffersData.STATUS_CODE)))
            .andExpect(jsonPath("$.response.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.userFeedback[0].offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(
                    jsonPath(
                            "$.response.items.userFeedback[0].feedback",
                            is(String.valueOf(PersonalizedOffersData.FEEDBACK))))
            .andExpect(
                    jsonPath(
                            "$.response.items.userFeedback[1].offerId", is(PersonalizedOffersData.OFFER_ID2)))
            .andExpect(
                    jsonPath(
                            "$.response.items.userFeedback[1].feedback",
                            is(String.valueOf(PersonalizedOffersData.FEEDBACK))))
            .andExpect(jsonPath("$.response.currentPage", is(PersonalizedOffersData.FIRST)))
            .andExpect(jsonPath("$.response.itemsPerPage", is(PersonalizedOffersData.ITEMS_PER_PAGE)))
            .andExpect(jsonPath("$.response.totalCount", is(PersonalizedOffersData.TOTAL_COUNT)));
  }

  @Test
  @DisplayName("GET /matched-offers")
  void matchedOffers() throws Exception {
    when(referenceApplicationGateway.getMatchedOffers(anyString()))
            .thenReturn(PersonalizedOffersData.getMatchedOffers());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/matched-offers")
                            .param("f_id", PersonalizedOffersData.FID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response.totalCount", is(PersonalizedOffersData.ONE)))
            .andExpect(jsonPath("$.response.currentPage", is(PersonalizedOffersData.FIRST)))
            .andExpect(jsonPath("$.response.numberOfPages", is(PersonalizedOffersData.ONE)))
            .andExpect(jsonPath("$.response.itemsPerPage", is(PersonalizedOffersData.ITEMS_PER_PAGE)))
            .andExpect(jsonPath("$.response.status.code", is(PersonalizedOffersData.STATUS_CODE)))
            .andExpect(jsonPath("$.response.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].offerType",
                            is(PersonalizedOffersData.OFFER_TYPE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].offerSource",
                            is(PersonalizedOffersData.OFFER_SOURCE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].language", is(PersonalizedOffersData.LANGUAGE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].currencyCode",
                            is(PersonalizedOffersData.CURRENCY_CODE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].merchant.name",
                            is(PersonalizedOffersData.MERCHANT)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].redemptionMode",
                            is(PersonalizedOffersData.REDEMPTION_MODE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].eventStartDate",
                            is(PersonalizedOffersData.START_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].eventEndDate",
                            is(PersonalizedOffersData.END_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].offerCountry",
                            is(PersonalizedOffersData.OFFER_COUNTRY)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].activationDate",
                            is(PersonalizedOffersData.ACTIVATION_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].scActivation.activationId",
                            is(PersonalizedOffersData.ACTIVATION_ID)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].scActivation.redemptionStartDate",
                            is(PersonalizedOffersData.START_DATE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].scActivation.redemptionEndDate",
                            is(PersonalizedOffersData.END_DATE)))
            .andExpect(
                    jsonPath("$.response.items.matchedOffer[0].price", is(PersonalizedOffersData.PRICE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].discount", is(PersonalizedOffersData.DISCOUNT)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].discountType",
                            is(PersonalizedOffersData.DISCOUNT_TYPE)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].activeSc", is(PersonalizedOffersData.ACTIVE_SC)))
            .andExpect(
                    jsonPath(
                            "$.response.items.matchedOffer[0].userFeedback.feedback",
                            is(String.valueOf(PersonalizedOffersData.FEEDBACK))));
  }

  @Test
  @DisplayName("GET /user-savings")
  void userSavings() throws Exception {
    when(referenceApplicationGateway.getUserSavings(anyString()))
            .thenReturn(PersonalizedOffersData.getUserSavings());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/user-savings")
                            .param("f_id", PersonalizedOffersData.FID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response.status.code", is(PersonalizedOffersData.STATUS_CODE)))
            .andExpect(jsonPath("$.response.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
            .andExpect(
                    jsonPath(
                            "$.response.userSavings.statementCreditOffersSavings.numRedeemed",
                            is(PersonalizedOffersData.ONE)))
            .andExpect(
                    jsonPath(
                            "$.response.userSavings.statementCreditOffersSavings.earnedCashback",
                            is(PersonalizedOffersData.CASH_BACK)))
            .andExpect(
                    jsonPath(
                            "$.response.userSavings.statementCreditOffersSavings.numAvailable",
                            is(PersonalizedOffersData.ONE)))
            .andExpect(
                    jsonPath(
                            "$.response.userSavings.statementCreditOffersSavings.potentialSavings",
                            is(PersonalizedOffersData.ONE)))
            .andExpect(
                    jsonPath(
                            "$.response.userSavings.statementCreditOffersSavings.earnedPoints",
                            is(PersonalizedOffersData.POINTS_EARNED)))
            .andExpect(
                    jsonPath(
                            "$.response.userSavings.statementCreditOffersSavings.potentialPoints",
                            is(PersonalizedOffersData.POINTS_EARNED)))
            .andExpect(
                    jsonPath(
                            "$.response.userSavings.totalAmountSaved", is(PersonalizedOffersData.CASH_AMOUNT)))
            .andExpect(
                    jsonPath("$.response.userSavings.totalOffersUsed", is(PersonalizedOffersData.ONE)))
            .andExpect(
                    jsonPath(
                            "$.response.userSavings.totalEarnedPoints",
                            is(PersonalizedOffersData.POINTS_EARNED)));
  }

  @Test
  @DisplayName("GET /user-tokens")
  void userToken() throws Exception {
    when(referenceApplicationGateway.getUserToken(anyString()))
            .thenReturn(PersonalizedOffersData.getUserToken());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/user-tokens")
                            .param("f_id", PersonalizedOffersData.FID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response.status.code", is(PersonalizedOffersData.STATUS_CODE)))
            .andExpect(jsonPath("$.response.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
            .andExpect(jsonPath("$.response.userToken.token", is(PersonalizedOffersData.USER_TOKEN)));
  }

  @Test
  @DisplayName("GET /offer-details")
  void offerDetails() throws Exception {
    when(referenceApplicationGateway.getOfferDetails(anyString(), anyString()))
            .thenReturn(PersonalizedOffersData.getOfferDetails());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/offer-details")
                            .param("f_id", PersonalizedOffersData.FID)
                            .param("offer_id", PersonalizedOffersData.OFFER_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.response.status.code", is(PersonalizedOffersData.STATUS_CODE)))
            .andExpect(jsonPath("$.response.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
            .andExpect(jsonPath("$.response.offerDetails.offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(
                    jsonPath("$.response.offerDetails.offerType", is(PersonalizedOffersData.OFFER_TYPE)))
            .andExpect(
                    jsonPath(
                            "$.response.offerDetails.redemptionType",
                            is(PersonalizedOffersData.REDEMPTION_TYPE)))
            .andExpect(
                    jsonPath(
                            "$.response.offerDetails.offerSource", is(PersonalizedOffersData.OFFER_SOURCE)))
            .andExpect(
                    jsonPath("$.response.offerDetails.language", is(PersonalizedOffersData.LANGUAGE)))
            .andExpect(
                    jsonPath("$.response.offerDetails.merchant.name", is(PersonalizedOffersData.MERCHANT)))
            .andExpect(
                    jsonPath(
                            "$.response.offerDetails.detailPostpaidCreditOffer.cashBack",
                            is(PersonalizedOffersData.CASH_BACK)))
            .andExpect(
                    jsonPath(
                            "$.response.offerDetails.detailPostpaidCreditOffer.statementCreditType",
                            is(PersonalizedOffersData.STATEMENT_CREDIT_TYPE)))
            .andExpect(
                    jsonPath(
                            "$.response.offerDetails.detailPostpaidCreditOffer.visitThreshold",
                            is(PersonalizedOffersData.ONE)))
            .andExpect(
                    jsonPath(
                            "$.response.offerDetails.detailPostpaidCreditOffer.minTransactionAmountToRedeem",
                            is(PersonalizedOffersData.CASH_AMOUNT)))
            .andExpect(
                    jsonPath(
                            "$.response.offerDetails.redemptionMode",
                            is(PersonalizedOffersData.REDEMPTION_MODE)))
            .andExpect(
                    jsonPath(
                            "$.response.offerDetails.eventStartDate", is(PersonalizedOffersData.START_DATE)))
            .andExpect(
                    jsonPath("$.response.offerDetails.eventEndDate", is(PersonalizedOffersData.END_DATE)))
            .andExpect(
                    jsonPath("$.response.offerDetails.maxUserRedemptions", is(PersonalizedOffersData.ONE)))
            .andExpect(
                    jsonPath(
                            "$.response.offerDetails.offerCountry", is(PersonalizedOffersData.OFFER_COUNTRY)))
            .andExpect(
                    jsonPath(
                            "$.response.offerDetails.activationDate",
                            is(PersonalizedOffersData.ACTIVATION_DATE)));
  }

  @Test
  @DisplayName("GET /offers")
  void offers() throws Exception {
    when(referenceApplicationGateway.getOffers(anyString()))
            .thenReturn(PersonalizedOffersData.getOffers());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/offers")
                            .param("f_id", PersonalizedOffersData.FID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.offset", is(PersonalizedOffersData.FIVE)))
            .andExpect(jsonPath("$.limit", is(PersonalizedOffersData.FIVE)))
            .andExpect(jsonPath("$.total", is(PersonalizedOffersData.FIVE)))
            .andExpect(
                    jsonPath("$.status.code", is(Integer.valueOf(PersonalizedOffersData.STATUS_CODE))))
            .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
            .andExpect(jsonPath("$.count", is(PersonalizedOffersData.FIVE)))
            .andExpect(jsonPath("offers[0].id", is(PersonalizedOffersData.ONE)))
            .andExpect(jsonPath("offers[0].type", is(PersonalizedOffersData.OFFER_TYPE)))
            .andExpect(
                    jsonPath("offers[0].redemptionChannel", is(PersonalizedOffersData.REDEMPTION_CHANNEL)))
            .andExpect(jsonPath("offers[0].source", is(PersonalizedOffersData.OFFER_SOURCE)))
            .andExpect(jsonPath("offers[0].category", is(PersonalizedOffersData.OFFER_CATEGORY)))
            .andExpect(jsonPath("offers[0].currency", is(PersonalizedOffersData.CURRENCY_CODE)))
            .andExpect(jsonPath("offers[0].merchant.name", is(PersonalizedOffersData.MERCHANT)))
            .andExpect(jsonPath("offers[0].eventStartDate", is(PersonalizedOffersData.START_DATE)))
            .andExpect(jsonPath("offers[0].eventEndDate", is(PersonalizedOffersData.END_DATE)))
            .andExpect(jsonPath("offers[0].redemptionMaxPerUser", is(PersonalizedOffersData.FIVE)))
            .andExpect(jsonPath("offers[0].offerCountry", is(PersonalizedOffersData.OFFER_COUNTRY)))
            .andExpect(jsonPath("offers[0].matchStartDate", is(PersonalizedOffersData.START_DATE)))
            .andExpect(jsonPath("offers[0].eventEndDate", is(PersonalizedOffersData.END_DATE)))
            .andExpect(jsonPath("offers[0].activationType", is(PersonalizedOffersData.ACTIVATION_TYPE)))
            .andExpect(jsonPath("offers[0].assignmentOnEnrollment", is(false)))
            .andExpect(jsonPath("offers[0].goal", is(PersonalizedOffersData.GOAL)))
            .andExpect(jsonPath("offers[0].lastModified", is(PersonalizedOffersData.END_DATE)))
            .andExpect(jsonPath("offers[0].matchMaxPerUser", is(PersonalizedOffersData.FIVE)))
            .andExpect(jsonPath("offers[0].presentmentEndDate", is(PersonalizedOffersData.END_DATE)))
            .andExpect(jsonPath("offers[0].status", is(PersonalizedOffersData.STATUS)))
            .andExpect(jsonPath("offers[0].test", is(false)))
            .andExpect(jsonPath("offers[0].redemptionGracePeriodDays", is(PersonalizedOffersData.FIVE)))
            .andExpect(
                    jsonPath(
                            "offers[0].redemptionSpendMinPerTxn",
                            is(PersonalizedOffersData.TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("offers[0].redemptionVisitThreshold", is(PersonalizedOffersData.FIVE)))
            .andExpect(
                    jsonPath(
                            "offers[0].redemptionStatementCreditType",
                            is(PersonalizedOffersData.STATEMENT_CREDIT_TYPE)));
  }

  @Test
  @DisplayName("GET /adjustments")
  void adjustments() throws Exception {
    when(referenceApplicationGateway.getAdjustments(any(), any(), any(),
            any(), any(), any())).thenReturn(PersonalizedOffersData.getAdjustments());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/adjustments")
                            .param("f_id", PersonalizedOffersData.FID)
                            .param("offset", String.valueOf(PersonalizedOffersData.OFFSET))
                            .param("limit", String.valueOf(PersonalizedOffersData.LIMIT))
                            .param("start_date", PersonalizedOffersData.START_DATE)
                            .param("end_date", PersonalizedOffersData.END_DATE)
                            .param("date_filter", PersonalizedOffersData.DATE_FILTER)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.offset", is(PersonalizedOffersData.OFFSET)))
            .andExpect(jsonPath("$.limit", is(PersonalizedOffersData.LIMIT)))
            .andExpect(jsonPath("$.total", is(PersonalizedOffersData.TOTAL)))
            .andExpect(jsonPath("$.count", is(PersonalizedOffersData.COUNT)))
            .andExpect(jsonPath("$.responseLastModified",
                    is(PersonalizedOffersData.LAST_MODIFIED)))
            .andExpect(jsonPath("$.status.code", is(Integer.valueOf(PersonalizedOffersData.STATUS_CODE))))
            .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))

            .andExpect(jsonPath("$.adjustments[0].id", is(PersonalizedOffersData.ID)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentId",
                    is(PersonalizedOffersData.ADJUSTMENT_ID)))
            .andExpect(jsonPath("$.adjustments[0].activationId",
                    is(PersonalizedOffersData.ADJUSTMENT_ACTIVATION_ID)))
            .andExpect(jsonPath("$.adjustments[0].activatedDateTime",
                    is(PersonalizedOffersData.ACTIVATED_DATE_TIME)))
            .andExpect(jsonPath("$.adjustments[0].transactionClassifier",
                    is(PersonalizedOffersData.TRANSACTION_CLASSIFIER)))
            .andExpect(jsonPath("$.adjustments[0].offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentMode",
                    is(PersonalizedOffersData.ADJUSTMENT_MODE)))
            .andExpect(jsonPath("$.adjustments[0].transactionBankAccountNumber",
                    is(PersonalizedOffersData.TRANSACTION_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.adjustments[0].transactionAmount",
                    is(PersonalizedOffersData.TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.adjustments[0].transactionDate",
                    is(PersonalizedOffersData.TRANSACTION_DATE)))
            .andExpect(jsonPath("$.adjustments[0].transactionTime",
                    is(PersonalizedOffersData.TRANSACTION_TIME)))
            .andExpect(jsonPath("$.adjustments[0].transactionDescription",
                    is(PersonalizedOffersData.TRANSACTION_DESCRIPTION)))
            .andExpect(jsonPath("$.adjustments[0].transactionSequenceNumber",
                    is(PersonalizedOffersData.TRANSACTION_SEQUENCE_NUMBER)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentCashValue",
                    is(PersonalizedOffersData.ADJUSTMENT_CASH_VALUE.doubleValue())))
            .andExpect(jsonPath("$.adjustments[0].redemptionDateTime",
                    is(PersonalizedOffersData.REDEMPTION_DATETIME)))
            .andExpect(jsonPath("$.adjustments[0].status",
                    is(PersonalizedOffersData.STATUS)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentPointsValue",
                    is(PersonalizedOffersData.ADJUSTMENT_POINTS_VALUE.doubleValue())))
            .andExpect(jsonPath("$.adjustments[0].transactionMerchantId",
                    is(PersonalizedOffersData.TRANSACTION_MERCHANT_ID)))
            .andExpect(jsonPath("$.adjustments[0].transactionChannel",
                    is(PersonalizedOffersData.TRANSACTION_CHANNEL)))
            .andExpect(jsonPath("$.adjustments[0].offerType",
                    is(PersonalizedOffersData.OFFER_TYPE)))
            .andExpect(jsonPath("$.adjustments[0].transactionAccountISUID",
                    is(PersonalizedOffersData.TRANSACTION_ACCOUNT_ISU_ID)))
            .andExpect(jsonPath("$.adjustments[0].transactionBankProductCode",
                    is(PersonalizedOffersData.TRANSACTION_BANK_PRODUCT_CODE)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentBankAccountNumber",
                    is(PersonalizedOffersData.ADJUSTMENT_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentBankProductCode",
                    is(PersonalizedOffersData.ADJUSTMENT_BANK_PRODUCT_CODE)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentAccountISUID",
                    is(PersonalizedOffersData.ADJUSTMENT_ACCOUNT_ISU_ID)))
            .andExpect(jsonPath("$.adjustments[0].transactionCountry",
                    is(PersonalizedOffersData.TRANSACTION_COUNTRY)))
            .andExpect(jsonPath("$.adjustments[0].transactionCurrency",
                    is(PersonalizedOffersData.TRANSACTION_CURRENCY)))
            .andExpect(jsonPath("$.adjustments[0].transactionBankAccountNumberAlias",
                    is(PersonalizedOffersData.TRANSACTION_BANK_ACCOUNT_NUMBER_ALIAS)))
            .andExpect(jsonPath("$.adjustments[0].transactionInstallmentPayment",
                    is(PersonalizedOffersData.TRANSACTION_INSTALLMENT_PAYMENT)))
            .andExpect(jsonPath("$.adjustments[0].transactionIssuerCountry",
                    is(PersonalizedOffersData.TRANSACTION_ISSUER_COUNTRY)))
            .andExpect(jsonPath("$.adjustments[0].created",
                    is(PersonalizedOffersData.CREATED)))
            .andExpect(jsonPath("$.adjustments[0].lastModified",
                    is(PersonalizedOffersData.LAST_MODIFIED)))
            .andExpect(jsonPath("$.adjustments[0].bankCustomerNumber",
                    is(PersonalizedOffersData.BANK_CUSTOMER_NUMBER)))
            .andExpect(jsonPath("$.adjustments[0].transactionAuthorizationCode",
                    is(PersonalizedOffersData.TRANSACTION_AUTHORIZATION_CODE)))
            .andExpect(jsonPath("$.adjustments[0].transactionDescriptionOriginal",
                    is(PersonalizedOffersData.TRANSACTION_DESCRIPTION_ORIGINAL)))
            .andExpect(jsonPath("$.adjustments[0].errorDescription",
                    is(PersonalizedOffersData.ERROR_DESCRIPTION)))
            .andExpect(jsonPath("$.adjustments[0].transactionCity",
                    is(PersonalizedOffersData.TRANSACTION_CITY)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentReversal",
                    is(PersonalizedOffersData.ADJUSTMENT_REVERSAL)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentValueSign",
                    is(PersonalizedOffersData.ADJUSTMENT_VALUE_SIGN)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentMerchantCategoryCode",
                    is(PersonalizedOffersData.ADJUSTMENT_MERCHANT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.adjustments[0].pointsConversionRate",
                    is(PersonalizedOffersData.POINTS_CONVERSION_RATE.doubleValue())))
            .andExpect(jsonPath("$.adjustments[0].transactionClassifierDisplayName",
                    is(PersonalizedOffersData.TRANSACTION_CLASSIFIER_DISPLAY_NAME)))
            .andExpect(jsonPath("$.adjustments[0].campaignId",
                    is(PersonalizedOffersData.CAMPAIGN_ID)))
            .andExpect(jsonPath("$.adjustments[0].transactionIssuerICA",
                    is(PersonalizedOffersData.TRANSACTION_ISSUER_ICA)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentPointsType",
                    is(PersonalizedOffersData.ADJUSTMENT_POINTS_TYPE)))
            .andExpect(jsonPath("$.adjustments[0].type",
                    is(PersonalizedOffersData.TYPE)));

  }

  @Test
  @DisplayName("POST /user-presentment/access-tokens")
  void accessTokens() throws Exception {
    when(referenceApplicationGateway.getToken(any()))
            .thenReturn(PersonalizedOffersData.getAccessTokenResponse());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.post("/user-presentment/access-tokens")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(PersonalizedOffersData.getAccessTokenRequest())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken", is(PersonalizedOffersData.ACCESS_TOKEN)));
  }

  @Test
  @DisplayName("GET /user-presentment/offers")
  void userPresentmentOffers() throws Exception {
    when(referenceApplicationGateway.getOffers(any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(PersonalizedOffersData.getUserPresentmentOffers());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/user-presentment/offers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .header("x-auth-token", PersonalizedOffersData.ACCESS_TOKEN))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.offset", is(PersonalizedOffersData.OFFSET)))
            .andExpect(jsonPath("$.limit", is(PersonalizedOffersData.LIMIT)))
            .andExpect(jsonPath("$.total", is(PersonalizedOffersData.TOTAL)))
            .andExpect(jsonPath("$.count", is(PersonalizedOffersData.COUNT)))
            .andExpect(jsonPath("$.offers[0].id", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(jsonPath("$.offers[0].type", is(PersonalizedOffersData.OFFER_TYPE)))
            .andExpect(jsonPath("$.offers[0].redemptionChannel", is(PersonalizedOffersData.REDEMPTION_CHANNEL)))
            .andExpect(jsonPath("$.offers[0].source", is(PersonalizedOffersData.OFFER_SOURCE)))
            .andExpect(jsonPath("$.offers[0].category", is(PersonalizedOffersData.OFFER_CATEGORY)))
            .andExpect(jsonPath("$.offers[0].currency", is(PersonalizedOffersData.CURRENCY_CODE)))
            .andExpect(jsonPath("$.offers[0].merchant.name", is(PersonalizedOffersData.MERCHANT)))
            .andExpect(
                    jsonPath("$.offers[0].redemptionSpendThreshold",
                            is(PersonalizedOffersData.SPEND_THRESHOLD.doubleValue())))
            .andExpect(jsonPath("$.offers[0].eventStartDate", is(PersonalizedOffersData.START_DATE)))
            .andExpect(jsonPath("$.offers[0].eventEndDate", is(PersonalizedOffersData.END_DATE)))
            .andExpect(jsonPath("$.offers[0].offerCountry", is(PersonalizedOffersData.OFFER_COUNTRY)))
            .andExpect(jsonPath("$.offers[0].matchStartDate", is(PersonalizedOffersData.START_DATE)))
            .andExpect(jsonPath("$.offers[0].matchEndDate", is(PersonalizedOffersData.END_DATE)))
            .andExpect(jsonPath("$.offers[0].activationType", is(PersonalizedOffersData.ACTIVATION_TYPE)))
            .andExpect(jsonPath("$.offers[0].assignmentOnEnrollment", is(false)))
            .andExpect(jsonPath("$.offers[0].goal", is(PersonalizedOffersData.GOAL)))
            .andExpect(jsonPath("$.offers[0].lastModified", is(PersonalizedOffersData.LAST_MODIFIED)))
            .andExpect(jsonPath("$.offers[0].matchMaxPerUser", is(PersonalizedOffersData.FIVE)))
            .andExpect(jsonPath("$.offers[0].presentmentEndDate", is(PersonalizedOffersData.PRESENTMENT_DATE)))
            .andExpect(jsonPath("$.offers[0].status", is(PersonalizedOffersData.STATUS)))
            .andExpect(jsonPath("$.offers[0].test", is(false)))
            .andExpect(jsonPath("$.offers[0].redemptionGracePeriodDays", is(PersonalizedOffersData.FIVE)))
            .andExpect(
                    jsonPath("$.offers[0].redemptionSpendMinPerTxn",
                            is(PersonalizedOffersData.TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.offers[0].redemptionVisitThreshold", is(PersonalizedOffersData.FIVE)))
            .andExpect(
                    jsonPath("$.offers[0].redemptionStatementCreditType",
                            is(PersonalizedOffersData.STATEMENT_CREDIT_TYPE)))
            .andExpect(jsonPath("$.offers[0].rewards[0].primary", is(true)))
            .andExpect(
                    jsonPath("$.offers[0].rewards[0].cashValueAbsolute",
                            is(PersonalizedOffersData.ADJUSTMENT_CASH_VALUE.doubleValue())))
            .andExpect(jsonPath("$.offers[0].rewards[0].discountType", is(PersonalizedOffersData.DISCOUNT_TYPE)))
            .andExpect(jsonPath("$.offers[0].rewards[0].mode", is(PersonalizedOffersData.REDEMPTION_MODE)))
            .andExpect(jsonPath("$.offers[0].rewards[0].type", is(PersonalizedOffersData.STATEMENT_CREDIT_TYPE)))
            .andExpect(
                    jsonPath("$.offers[0].localizations[0].descriptionLong",
                            is(PersonalizedOffersData.DESCRIPTION_LONG)))
            .andExpect(
                    jsonPath("$.offers[0].localizations[0].descriptionShort",
                            is(PersonalizedOffersData.DESCRIPTION_SHORT)))
            .andExpect(
                    jsonPath("$.offers[0].localizations[0].headline", is(PersonalizedOffersData.HEADLINE)))
            .andExpect(jsonPath("$.offers[0].localizations[0].lang", is(PersonalizedOffersData.LANGUAGE)))
            .andExpect(
                    jsonPath("$.offers[0].localizations[0].merchantDisplayName", is(PersonalizedOffersData.MERCHANT)))
            .andExpect(jsonPath("$.offers[0].localizations[0].storeLocatorURL", is(PersonalizedOffersData.URL)))
            .andExpect(jsonPath("$.offers[0].localizations[0].termsDetailed", is(PersonalizedOffersData.TERMS)))
            .andExpect(jsonPath("$.offers[0].localizations[0].termsKey", is(PersonalizedOffersData.TERMS)))
            .andExpect(jsonPath("$.offers[0].localizations[0].websiteURL", is(PersonalizedOffersData.URL)))
            .andExpect(
                    jsonPath("$.offers[0].localizations[0].marketingSlogan", is(PersonalizedOffersData.SLOGAN)))
            .andExpect(jsonPath("$.offers[0].localizations[0].name", is(PersonalizedOffersData.MERCHANT)))
            .andExpect(
                    jsonPath("$.offers[0].redemptionClassifiers.primaryValue", is(PersonalizedOffersData.VALUE)))
            .andExpect(jsonPath("$.offers[0].redemptionClassifiers.values[0]", is(PersonalizedOffersData.VALUE)));
  }

  @Test
  @DisplayName("GET /user-presentment/offers/{offer_id}")
  void userPresentmentOfferDetails() throws Exception {
    when(referenceApplicationGateway.getOfferDetails(anyString(), anyString(), anyString()))
            .thenReturn(PersonalizedOffersData.getUserOfferDetails());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get(
                            "/user-presentment/offers/{offer_id}", PersonalizedOffersData.OFFER_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .header("x-auth-token", PersonalizedOffersData.ACCESS_TOKEN))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userOfferDetails.id", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(jsonPath("$.userOfferDetails.type", is(PersonalizedOffersData.OFFER_TYPE)))
            .andExpect(
                    jsonPath("$.userOfferDetails.redemptionChannel", is(PersonalizedOffersData.REDEMPTION_CHANNEL)))
            .andExpect(jsonPath("$.userOfferDetails.source", is(PersonalizedOffersData.OFFER_SOURCE)))
            .andExpect(jsonPath("$.userOfferDetails.category", is(PersonalizedOffersData.OFFER_CATEGORY)))
            .andExpect(jsonPath("$.userOfferDetails.currency", is(PersonalizedOffersData.CURRENCY_CODE)))
            .andExpect(jsonPath("$.userOfferDetails.merchant.name", is(PersonalizedOffersData.MERCHANT)))
            .andExpect(
                    jsonPath("$.userOfferDetails.redemptionSpendThreshold",
                            is(PersonalizedOffersData.SPEND_THRESHOLD.doubleValue())))
            .andExpect(jsonPath("$.userOfferDetails.eventStartDate", is(PersonalizedOffersData.START_DATE)))
            .andExpect(jsonPath("$.userOfferDetails.eventEndDate", is(PersonalizedOffersData.END_DATE)))
            .andExpect(jsonPath("$.userOfferDetails.offerCountry", is(PersonalizedOffersData.OFFER_COUNTRY)))
            .andExpect(jsonPath("$.userOfferDetails.matchStartDate", is(PersonalizedOffersData.START_DATE)))
            .andExpect(jsonPath("$.userOfferDetails.matchEndDate", is(PersonalizedOffersData.END_DATE)))
            .andExpect(jsonPath("$.userOfferDetails.activationType", is(PersonalizedOffersData.ACTIVATION_TYPE)))
            .andExpect(jsonPath("$.userOfferDetails.assignmentOnEnrollment", is(false)))
            .andExpect(jsonPath("$.userOfferDetails.goal", is(PersonalizedOffersData.GOAL)))
            .andExpect(jsonPath("$.userOfferDetails.lastModified", is(PersonalizedOffersData.LAST_MODIFIED)))
            .andExpect(jsonPath("$.userOfferDetails.matchMaxPerUser", is(PersonalizedOffersData.FIVE)))
            .andExpect(
                    jsonPath("$.userOfferDetails.presentmentEndDate", is(PersonalizedOffersData.PRESENTMENT_DATE)))
            .andExpect(jsonPath("$.userOfferDetails.status", is(PersonalizedOffersData.STATUS)))
            .andExpect(jsonPath("$.userOfferDetails.test", is(false)))
            .andExpect(
                    jsonPath("$.userOfferDetails.redemptionGracePeriodDays", is(PersonalizedOffersData.FIVE)))
            .andExpect(
                    jsonPath("$.userOfferDetails.redemptionSpendMinPerTxn",
                            is(PersonalizedOffersData.TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(
                    jsonPath("$.userOfferDetails.redemptionVisitThreshold",
                            is(PersonalizedOffersData.FIVE)))
            .andExpect(
                    jsonPath("$.userOfferDetails.redemptionStatementCreditType",
                            is(PersonalizedOffersData.STATEMENT_CREDIT_TYPE)))
            .andExpect(jsonPath("$.userOfferDetails.rewards[0].primary", is(true)))
            .andExpect(
                    jsonPath("$.userOfferDetails.rewards[0].cashValueAbsolute",
                            is(PersonalizedOffersData.ADJUSTMENT_CASH_VALUE.doubleValue())))
            .andExpect(
                    jsonPath("$.userOfferDetails.rewards[0].discountType", is(PersonalizedOffersData.DISCOUNT_TYPE)))
            .andExpect(jsonPath("$.userOfferDetails.rewards[0].mode", is(PersonalizedOffersData.REDEMPTION_MODE)))
            .andExpect(
                    jsonPath("$.userOfferDetails.rewards[0].type", is(PersonalizedOffersData.STATEMENT_CREDIT_TYPE)))
            .andExpect(
                    jsonPath("$.userOfferDetails.localizations[0].descriptionLong",
                            is(PersonalizedOffersData.DESCRIPTION_LONG)))
            .andExpect(
                    jsonPath("$.userOfferDetails.localizations[0].descriptionShort",
                            is(PersonalizedOffersData.DESCRIPTION_SHORT)))
            .andExpect(
                    jsonPath("$.userOfferDetails.localizations[0].headline", is(PersonalizedOffersData.HEADLINE)))
            .andExpect(
                    jsonPath("$.userOfferDetails.localizations[0].lang", is(PersonalizedOffersData.LANGUAGE)))
            .andExpect(
                    jsonPath("$.userOfferDetails.localizations[0].merchantDisplayName",
                            is(PersonalizedOffersData.MERCHANT)))
            .andExpect(jsonPath("$.userOfferDetails.localizations[0].storeLocatorURL",
                    is(PersonalizedOffersData.URL)))
            .andExpect(
                    jsonPath("$.userOfferDetails.localizations[0].termsDetailed", is(PersonalizedOffersData.TERMS)))
            .andExpect(jsonPath("$.userOfferDetails.localizations[0].termsKey", is(PersonalizedOffersData.TERMS)))
            .andExpect(jsonPath("$.userOfferDetails.localizations[0].websiteURL", is(PersonalizedOffersData.URL)))
            .andExpect(
                    jsonPath("$.userOfferDetails.localizations[0].marketingSlogan",
                            is(PersonalizedOffersData.SLOGAN)))
            .andExpect(jsonPath("$.userOfferDetails.localizations[0].name", is(PersonalizedOffersData.MERCHANT)))
            .andExpect(
                    jsonPath("$.userOfferDetails.redemptionClassifiers.primaryValue",
                            is(PersonalizedOffersData.VALUE)))
            .andExpect(
                    jsonPath("$.userOfferDetails.redemptionClassifiers.values[0]", is(PersonalizedOffersData.VALUE)));
  }

  @Test
  @DisplayName("GET /user-presentment/offer-ratings")
  void getOfferRatings() throws Exception {
    when(referenceApplicationGateway.getOfferRatings(any(),any(),any(),any()))
            .thenReturn(PersonalizedOffersData.getOfferRatingsResponse());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/user-presentment/offer-ratings")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON).header("x-auth-token", PersonalizedOffersData.ACCESS_TOKEN))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.offset", is(PersonalizedOffersData.OFFSET)))
            .andExpect(jsonPath("$.limit", is(PersonalizedOffersData.LIMIT)))
            .andExpect(jsonPath("$.total", is(PersonalizedOffersData.TOTAL)))
            .andExpect(jsonPath("$.count", is(PersonalizedOffersData.COUNT)))
            .andExpect(jsonPath("$.offerRatings[0].offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(jsonPath("$.offerRatings[0].like", is(PersonalizedOffersData.OFFER_RATING)));

  }

  @Test
  @DisplayName("GET /user-presentment/offer-ratings/{offer_id}")
  void getOfferRating() throws Exception {
    when(referenceApplicationGateway.getOfferRating(any(),any()))
            .thenReturn(PersonalizedOffersData.getOfferRatingResponse());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/user-presentment/offer-ratings/{offer_id}",PersonalizedOffersData.OFFER_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON).header("x-auth-token", PersonalizedOffersData.ACCESS_TOKEN))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.offerRating.offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(jsonPath("$.offerRating.like", is(PersonalizedOffersData.OFFER_RATING)));

  }

  @Test
  @DisplayName("POST /user-presentment/offer-ratings/{offer_id}/likes")
  void createOfferRating() throws Exception {
    when(referenceApplicationGateway.createOfferRating(any(),any(),any()))
            .thenReturn(PersonalizedOffersData.getOfferRatingResponse());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.post("/user-presentment/offer-ratings/{offer_id}/likes",PersonalizedOffersData.OFFER_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON).header("x-auth-token", PersonalizedOffersData.ACCESS_TOKEN)
                            .content(gson.toJson(PersonalizedOffersData.getOfferRatingRequest())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.offerRating.offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(jsonPath("$.offerRating.like", is(PersonalizedOffersData.OFFER_RATING)));

  }

  @Test
  @DisplayName("POST /user-presentment/activation-requests")
  void processActiavations() throws Exception {
    when(referenceApplicationGateway.processActivations(any(),any()))
            .thenReturn(PersonalizedOffersData.getActivations());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.post("/user-presentment/activation-requests")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON).header("x-auth-token", PersonalizedOffersData.ACCESS_TOKEN)
                            .content(gson.toJson(PersonalizedOffersData.getRequestedActivation())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.activations[0].id", is(PersonalizedOffersData.ACTIVATION_ID)))
            .andExpect(jsonPath("$.activations[0].status", is(PersonalizedOffersData.STATUS)))
            .andExpect(jsonPath("$.activations[0].offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(jsonPath("$.activations[0].assignments[0].id", is(PersonalizedOffersData.ID)))
            .andExpect(jsonPath("$.activations[0].assignments[0].activationTimeStamp", is(PersonalizedOffersData.START_DATE)))
            .andExpect(jsonPath("$.activations[0].assignments[0].redemptionStartDate", is(PersonalizedOffersData.START_DATE)))
            .andExpect(jsonPath("$.activations[0].assignments[0].redemptionEndDate", is(PersonalizedOffersData.END_DATE)))
            .andExpect(jsonPath("$.activations[0].assignments[0].status", is(PersonalizedOffersData.STATUS)));
  }

  @Test
  @DisplayName("GET /user-presentment/savings")
  void userPresentmentSavings() throws Exception {
    when(referenceApplicationGateway.getUserPresentmentSavings(anyString()))
            .thenReturn(PersonalizedOffersData.getUserPresentmentSavings());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/user-presentment/savings")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON).header("x-auth-token", PersonalizedOffersData.ACCESS_TOKEN))
            .andExpect(status().isOk())
            .andExpect(
                    jsonPath(
                            "$.userSavings.statementCreditOffersSavings.numRedeemed",
                            is(PersonalizedOffersData.ONE)))
            .andExpect(
                    jsonPath(
                            "$.userSavings.statementCreditOffersSavings.earnedCashback",
                            is(PersonalizedOffersData.CASH_BACK)))
            .andExpect(
                    jsonPath(
                            "$.userSavings.statementCreditOffersSavings.numAvailable",
                            is(PersonalizedOffersData.ONE)))
            .andExpect(
                    jsonPath(
                            "$.userSavings.statementCreditOffersSavings.potentialSavings",
                            is(PersonalizedOffersData.ONE)))
            .andExpect(
                    jsonPath(
                            "$.userSavings.statementCreditOffersSavings.earnedPoints",
                            is(PersonalizedOffersData.POINTS_EARNED)))
            .andExpect(
                    jsonPath(
                            "$.userSavings.statementCreditOffersSavings.potentialPoints",
                            is(PersonalizedOffersData.POINTS_EARNED)))
            .andExpect(
                    jsonPath(
                            "$.userSavings.totalAmountSaved", is(PersonalizedOffersData.CASH_AMOUNT)))
            .andExpect(
                    jsonPath("$.userSavings.totalOffersUsed", is(PersonalizedOffersData.ONE)))
            .andExpect(
                    jsonPath(
                            "$.userSavings.totalEarnedPoints",
                            is(PersonalizedOffersData.POINTS_EARNED)));
  }

  @Test
  @DisplayName("GET /user-presentment/adjustments")
  void userAdjustments() throws Exception {
    when(referenceApplicationGateway.getUserPresentmentAdjustments(any(), any(), any(),
            any(), any(), any())).thenReturn(PersonalizedOffersData.getUserAdjustments());

    mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/user-presentment/adjustments")
                            .param("offset", String.valueOf(PersonalizedOffersData.OFFSET))
                            .param("limit", String.valueOf(PersonalizedOffersData.LIMIT))
                            .param("start_date", PersonalizedOffersData.START_DATE)
                            .param("end_date", PersonalizedOffersData.END_DATE)
                            .param("date_filter", PersonalizedOffersData.DATE_FILTER)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                            .header("x-auth-token", PersonalizedOffersData.ACCESS_TOKEN))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.offset", is(PersonalizedOffersData.OFFSET)))
            .andExpect(jsonPath("$.limit", is(PersonalizedOffersData.LIMIT)))
            .andExpect(jsonPath("$.total", is(PersonalizedOffersData.TOTAL)))
            .andExpect(jsonPath("$.count", is(PersonalizedOffersData.COUNT)))
            .andExpect(jsonPath("$.responseLastModified",
                    is(PersonalizedOffersData.LAST_MODIFIED)))

            .andExpect(jsonPath("$.adjustments[0].id", is(PersonalizedOffersData.ID)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentId",
                    is(PersonalizedOffersData.ADJUSTMENT_ID)))
            .andExpect(jsonPath("$.adjustments[0].activationId",
                    is(PersonalizedOffersData.ADJUSTMENT_ACTIVATION_ID)))
            .andExpect(jsonPath("$.adjustments[0].activatedDateTime",
                    is(PersonalizedOffersData.ACTIVATED_DATE_TIME)))
            .andExpect(jsonPath("$.adjustments[0].transactionClassifier",
                    is(PersonalizedOffersData.TRANSACTION_CLASSIFIER)))
            .andExpect(jsonPath("$.adjustments[0].offerId", is(PersonalizedOffersData.OFFER_ID)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentMode",
                    is(PersonalizedOffersData.ADJUSTMENT_MODE)))
            .andExpect(jsonPath("$.adjustments[0].transactionBankAccountNumber",
                    is(PersonalizedOffersData.TRANSACTION_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.adjustments[0].transactionAmount",
                    is(PersonalizedOffersData.TRANSACTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.adjustments[0].transactionDate",
                    is(PersonalizedOffersData.TRANSACTION_DATE)))
            .andExpect(jsonPath("$.adjustments[0].transactionTime",
                    is(PersonalizedOffersData.TRANSACTION_TIME)))
            .andExpect(jsonPath("$.adjustments[0].transactionDescription",
                    is(PersonalizedOffersData.TRANSACTION_DESCRIPTION)))
            .andExpect(jsonPath("$.adjustments[0].transactionSequenceNumber",
                    is(PersonalizedOffersData.TRANSACTION_SEQUENCE_NUMBER)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentCashValue",
                    is(PersonalizedOffersData.ADJUSTMENT_CASH_VALUE.doubleValue())))
            .andExpect(jsonPath("$.adjustments[0].redemptionDateTime",
                    is(PersonalizedOffersData.REDEMPTION_DATETIME)))
            .andExpect(jsonPath("$.adjustments[0].status",
                    is(PersonalizedOffersData.STATUS)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentPointsValue",
                    is(PersonalizedOffersData.ADJUSTMENT_POINTS_VALUE.doubleValue())))
            .andExpect(jsonPath("$.adjustments[0].transactionMerchantId",
                    is(PersonalizedOffersData.TRANSACTION_MERCHANT_ID)))
            .andExpect(jsonPath("$.adjustments[0].transactionChannel",
                    is(PersonalizedOffersData.TRANSACTION_CHANNEL)))
            .andExpect(jsonPath("$.adjustments[0].offerType",
                    is(PersonalizedOffersData.OFFER_TYPE)))
            .andExpect(jsonPath("$.adjustments[0].transactionAccountISUID",
                    is(PersonalizedOffersData.TRANSACTION_ACCOUNT_ISU_ID)))
            .andExpect(jsonPath("$.adjustments[0].transactionBankProductCode",
                    is(PersonalizedOffersData.TRANSACTION_BANK_PRODUCT_CODE)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentBankAccountNumber",
                    is(PersonalizedOffersData.ADJUSTMENT_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentBankProductCode",
                    is(PersonalizedOffersData.ADJUSTMENT_BANK_PRODUCT_CODE)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentAccountISUID",
                    is(PersonalizedOffersData.ADJUSTMENT_ACCOUNT_ISU_ID)))
            .andExpect(jsonPath("$.adjustments[0].transactionCountry",
                    is(PersonalizedOffersData.TRANSACTION_COUNTRY)))
            .andExpect(jsonPath("$.adjustments[0].transactionCurrency",
                    is(PersonalizedOffersData.TRANSACTION_CURRENCY)))
            .andExpect(jsonPath("$.adjustments[0].transactionBankAccountNumberAlias",
                    is(PersonalizedOffersData.TRANSACTION_BANK_ACCOUNT_NUMBER_ALIAS)))
            .andExpect(jsonPath("$.adjustments[0].transactionInstallmentPayment",
                    is(PersonalizedOffersData.TRANSACTION_INSTALLMENT_PAYMENT)))
            .andExpect(jsonPath("$.adjustments[0].transactionIssuerCountry",
                    is(PersonalizedOffersData.TRANSACTION_ISSUER_COUNTRY)))
            .andExpect(jsonPath("$.adjustments[0].created",
                    is(PersonalizedOffersData.CREATED)))
            .andExpect(jsonPath("$.adjustments[0].lastModified",
                    is(PersonalizedOffersData.LAST_MODIFIED)))
            .andExpect(jsonPath("$.adjustments[0].bankCustomerNumber",
                    is(PersonalizedOffersData.BANK_CUSTOMER_NUMBER)))
            .andExpect(jsonPath("$.adjustments[0].transactionAuthorizationCode",
                    is(PersonalizedOffersData.TRANSACTION_AUTHORIZATION_CODE)))
            .andExpect(jsonPath("$.adjustments[0].transactionDescriptionOriginal",
                    is(PersonalizedOffersData.TRANSACTION_DESCRIPTION_ORIGINAL)))
            .andExpect(jsonPath("$.adjustments[0].errorDescription",
                    is(PersonalizedOffersData.ERROR_DESCRIPTION)))
            .andExpect(jsonPath("$.adjustments[0].transactionCity",
                    is(PersonalizedOffersData.TRANSACTION_CITY)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentReversal",
                    is(PersonalizedOffersData.ADJUSTMENT_REVERSAL)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentValueSign",
                    is(PersonalizedOffersData.ADJUSTMENT_VALUE_SIGN)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentMerchantCategoryCode",
                    is(PersonalizedOffersData.ADJUSTMENT_MERCHANT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.adjustments[0].pointsConversionRate",
                    is(PersonalizedOffersData.POINTS_CONVERSION_RATE.doubleValue())))
            .andExpect(jsonPath("$.adjustments[0].transactionClassifierDisplayName",
                    is(PersonalizedOffersData.TRANSACTION_CLASSIFIER_DISPLAY_NAME)))
            .andExpect(jsonPath("$.adjustments[0].campaignId",
                    is(PersonalizedOffersData.CAMPAIGN_ID)))
            .andExpect(jsonPath("$.adjustments[0].transactionIssuerICA",
                    is(PersonalizedOffersData.TRANSACTION_ISSUER_ICA)))
            .andExpect(jsonPath("$.adjustments[0].adjustmentPointsType",
                    is(PersonalizedOffersData.ADJUSTMENT_POINTS_TYPE)))
            .andExpect(jsonPath("$.adjustments[0].type",
                    is(PersonalizedOffersData.TYPE)));

  }

}
