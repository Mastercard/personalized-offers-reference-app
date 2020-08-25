package com.mastercard.developer.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(
    classes = {PersonalizedOffersController.class, PersonalizedOffersService.class})
@WebMvcTest
class PersonalizedOffersControllerTest {

  private final Gson gson;
  private final MockMvc mockMvc;

  @MockBean
  PersonalizedOffersService referenceApplicationGateway;

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
        .andExpect(jsonPath("$.status.code", is(PersonalizedOffersData.STATUS_CODE)))
        .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
        .andExpect(jsonPath("$.numberOfPages", is("1")))
        .andExpect(jsonPath("$.itemsPerPage", is("25")))
        .andExpect(jsonPath("$.totalCount", is("1")));
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
        .andExpect(jsonPath("$.status.code", is(PersonalizedOffersData.STATUS_CODE)))
        .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
        .andExpect(
            jsonPath("$.scActivation.activationId", is(PersonalizedOffersData.ACTIVATION_ID)))
        .andExpect(jsonPath("$.scActivation.status", is(PersonalizedOffersData.STATUS)))
        .andExpect(jsonPath("$.scActivation.offerId", is(PersonalizedOffersData.OFFER_ID)))
        .andExpect(
            jsonPath(
                "$.scActivation.activationDate",
                is(PersonalizedOffersData.ACTIVATION_DATE.toString())))
        .andExpect(
            jsonPath(
                "$.scActivation.redemptionDate",
                is(PersonalizedOffersData.REDEMPTION_DATE.toString())))
        .andExpect(
            jsonPath(
                "$.scActivation.redemptionEndDate", is(PersonalizedOffersData.END_DATE.toString())))
        .andExpect(
            jsonPath(
                "$.scActivation.redemptionStartDate",
                is(PersonalizedOffersData.START_DATE.toString())))
        .andExpect(
            jsonPath("$.scActivation.redemptionMode", is(PersonalizedOffersData.REDEMPTION_MODE)))
        .andExpect(
            jsonPath("$.scActivation.pointsEarned", is(PersonalizedOffersData.POINTS_EARNED)))
        .andExpect(jsonPath("$.scActivation.merchant", is(PersonalizedOffersData.MERCHANT)))
        .andExpect(jsonPath("$.scActivation.cashBack", is(PersonalizedOffersData.CASH_BACK)));
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
        .andExpect(jsonPath("$.status.code", is(PersonalizedOffersData.STATUS_CODE)))
        .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
        .andExpect(
            jsonPath("$.scActivation.activationId", is(PersonalizedOffersData.ACTIVATION_ID)))
        .andExpect(jsonPath("$.scActivation.status", is(PersonalizedOffersData.STATUS)))
        .andExpect(jsonPath("$.scActivation.offerId", is(PersonalizedOffersData.OFFER_ID)))
        .andExpect(
            jsonPath(
                "$.scActivation.activationDate",
                is(PersonalizedOffersData.ACTIVATION_DATE.toString())))
        .andExpect(
            jsonPath(
                "$.scActivation.redemptionDate",
                is(PersonalizedOffersData.REDEMPTION_DATE.toString())))
        .andExpect(
            jsonPath(
                "$.scActivation.redemptionStartDate",
                is(PersonalizedOffersData.START_DATE.toString())))
        .andExpect(
            jsonPath("$.scActivation.redemptionMode", is(PersonalizedOffersData.REDEMPTION_MODE)))
        .andExpect(
            jsonPath("$.scActivation.pointsEarned", is(PersonalizedOffersData.POINTS_EARNED)))
        .andExpect(jsonPath("$.scActivation.merchant", is(PersonalizedOffersData.MERCHANT)))
        .andExpect(jsonPath("$.scActivation.cashBack", is(PersonalizedOffersData.CASH_BACK)));
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
        .andExpect(jsonPath("$.status.code", is(PersonalizedOffersData.STATUS_CODE)))
        .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
        .andExpect(jsonPath("$.userFeedback.offerId", is(PersonalizedOffersData.OFFER_ID)))
        .andExpect(
            jsonPath(
                "$.userFeedback.feedback", is(String.valueOf(PersonalizedOffersData.FEEDBACK))));
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
        .andExpect(jsonPath("$.status.code", is(PersonalizedOffersData.STATUS_CODE)))
        .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
        .andExpect(jsonPath("$.items.userFeedback[0].offerId", is(PersonalizedOffersData.OFFER_ID)))
        .andExpect(
            jsonPath(
                "$.items.userFeedback[0].feedback",
                is(String.valueOf(PersonalizedOffersData.FEEDBACK))))
        .andExpect(
            jsonPath("$.items.userFeedback[1].offerId", is(PersonalizedOffersData.OFFER_ID2)))
        .andExpect(
            jsonPath(
                "$.items.userFeedback[1].feedback",
                is(String.valueOf(PersonalizedOffersData.FEEDBACK))))
        .andExpect(jsonPath("$.currentPage", is(PersonalizedOffersData.FIRST)))
        .andExpect(jsonPath("$.itemsPerPage", is(PersonalizedOffersData.ITEMS_PER_PAGE)))
        .andExpect(jsonPath("$.totalCount", is(PersonalizedOffersData.TOTAL_COUNT)));
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
        .andExpect(jsonPath("$.totalCount", is(PersonalizedOffersData.ONE)))
        .andExpect(jsonPath("$.currentPage", is(PersonalizedOffersData.FIRST)))
        .andExpect(jsonPath("$.numberOfPages", is(PersonalizedOffersData.ONE)))
        .andExpect(jsonPath("$.itemsPerPage", is(PersonalizedOffersData.ITEMS_PER_PAGE)))
        .andExpect(jsonPath("$.status.code", is(PersonalizedOffersData.STATUS_CODE)))
        .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
        .andExpect(jsonPath("$.items.matchedOffer[0].offerId", is(PersonalizedOffersData.OFFER_ID)))
        .andExpect(
            jsonPath("$.items.matchedOffer[0].offerType", is(PersonalizedOffersData.OFFER_TYPE)))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].offerSource", is(PersonalizedOffersData.OFFER_SOURCE)))
        .andExpect(
            jsonPath("$.items.matchedOffer[0].language", is(PersonalizedOffersData.LANGUAGE)))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].currencyCode", is(PersonalizedOffersData.CURRENCY_CODE)))
        .andExpect(
            jsonPath("$.items.matchedOffer[0].merchant.name", is(PersonalizedOffersData.MERCHANT)))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].redemptionMode",
                is(PersonalizedOffersData.REDEMPTION_MODE)))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].eventStartDate",
                is(String.valueOf(PersonalizedOffersData.START_DATE))))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].eventEndDate",
                is(String.valueOf(PersonalizedOffersData.END_DATE))))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].offerCountry", is(PersonalizedOffersData.OFFER_COUNTRY)))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].activationDate",
                is(String.valueOf(PersonalizedOffersData.ACTIVATION_DATE))))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].scActivation.activationId",
                is(PersonalizedOffersData.ACTIVATION_ID)))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].scActivation.redemptionStartDate",
                is(String.valueOf(PersonalizedOffersData.START_DATE))))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].scActivation.redemptionEndDate",
                is(String.valueOf(PersonalizedOffersData.END_DATE))))
        .andExpect(jsonPath("$.items.matchedOffer[0].price", is(PersonalizedOffersData.PRICE)))
        .andExpect(
            jsonPath("$.items.matchedOffer[0].discount", is(PersonalizedOffersData.DISCOUNT)))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].discountType", is(PersonalizedOffersData.DISCOUNT_TYPE)))
        .andExpect(
            jsonPath("$.items.matchedOffer[0].activeSc", is(PersonalizedOffersData.ACTIVE_SC)))
        .andExpect(
            jsonPath(
                "$.items.matchedOffer[0].userFeedback.feedback",
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
        .andExpect(jsonPath("$.status.code", is(PersonalizedOffersData.STATUS_CODE)))
        .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
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
            jsonPath("$.userSavings.totalAmountSaved", is(PersonalizedOffersData.CASH_AMOUNT)))
        .andExpect(jsonPath("$.userSavings.totalOffersUsed", is(PersonalizedOffersData.ONE)))
        .andExpect(
            jsonPath("$.userSavings.totalEarnedPoints", is(PersonalizedOffersData.POINTS_EARNED)));
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
        .andExpect(jsonPath("$.status.code", is(PersonalizedOffersData.STATUS_CODE)))
        .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
        .andExpect(jsonPath("$.userToken.token", is(PersonalizedOffersData.USER_TOKEN)));
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
        .andExpect(jsonPath("$.status.code", is(PersonalizedOffersData.STATUS_CODE)))
        .andExpect(jsonPath("$.status.message", is(PersonalizedOffersData.STATUS_MESSAGE)))
        .andExpect(jsonPath("$.offerDetails.offerId", is(PersonalizedOffersData.OFFER_ID)))
        .andExpect(jsonPath("$.offerDetails.offerType", is(PersonalizedOffersData.OFFER_TYPE)))
        .andExpect(
            jsonPath("$.offerDetails.redemptionType", is(PersonalizedOffersData.REDEMPTION_TYPE)))
        .andExpect(jsonPath("$.offerDetails.offerSource", is(PersonalizedOffersData.OFFER_SOURCE)))
        .andExpect(jsonPath("$.offerDetails.language", is(PersonalizedOffersData.LANGUAGE)))
        .andExpect(jsonPath("$.offerDetails.merchant.name", is(PersonalizedOffersData.MERCHANT)))
        .andExpect(
            jsonPath(
                "$.offerDetails.detailPostpaidCreditOffer.cashBack",
                is(PersonalizedOffersData.CASH_BACK)))
        .andExpect(
            jsonPath(
                "$.offerDetails.detailPostpaidCreditOffer.statementCreditType",
                is(PersonalizedOffersData.STATEMENT_CREDIT_TYPE)))
        .andExpect(
            jsonPath(
                "$.offerDetails.detailPostpaidCreditOffer.visitThreshold",
                is(PersonalizedOffersData.ONE)))
        .andExpect(
            jsonPath(
                "$.offerDetails.detailPostpaidCreditOffer.minTransactionAmountToRedeem",
                is(PersonalizedOffersData.CASH_AMOUNT)))
        .andExpect(
            jsonPath("$.offerDetails.redemptionMode", is(PersonalizedOffersData.REDEMPTION_MODE)))
        .andExpect(
            jsonPath(
                "$.offerDetails.eventStartDate",
                is(String.valueOf(PersonalizedOffersData.START_DATE))))
        .andExpect(
            jsonPath(
                "$.offerDetails.eventEndDate", is(String.valueOf(PersonalizedOffersData.END_DATE))))
        .andExpect(jsonPath("$.offerDetails.maxUserRedemptions", is(PersonalizedOffersData.ONE)))
        .andExpect(
            jsonPath("$.offerDetails.offerCountry", is(PersonalizedOffersData.OFFER_COUNTRY)))
        .andExpect(
            jsonPath(
                "$.offerDetails.activationDate",
                is(String.valueOf(PersonalizedOffersData.ACTIVATION_DATE))));
  }
}
