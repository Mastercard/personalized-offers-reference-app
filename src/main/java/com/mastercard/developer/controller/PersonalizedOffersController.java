package com.mastercard.developer.controller;

import com.mastercard.ApiException;
import com.mastercard.api.model.ActivateSCOfferInputStatementCreditOfferActivation;
import com.mastercard.api.model.AdjustmentResponse;
import com.mastercard.api.model.BrowseOffersResponse;
import com.mastercard.api.model.BulkActivationApiRequest;
import com.mastercard.api.model.BulkActivationRequestResponse;
import com.mastercard.api.model.ResponseWrapperDetailedOffersResponseDetailedOffers;
import com.mastercard.api.model.ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers;
import com.mastercard.api.model.ResponseWrapperMatchedOfferDetailsResponseMatchedOffers;
import com.mastercard.api.model.ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation;
import com.mastercard.api.model.ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail;
import com.mastercard.api.model.ResponseWrapperUserFeedbackOutputListResponse;
import com.mastercard.api.model.ResponseWrapperUserFeedbackOutputResponse;
import com.mastercard.api.model.ResponseWrapperUserSavingsResponse;
import com.mastercard.api.model.ResponseWrapperUserTokenOutputResponse;
import com.mastercard.api.model.UserFeedbackInput;
import com.mastercard.developer.service.PersonalizedOffersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonalizedOffersController {

  private final PersonalizedOffersService referenceApplicationGateway;

  public PersonalizedOffersController(final PersonalizedOffersService referenceApplicationGateway) {
    this.referenceApplicationGateway = referenceApplicationGateway;
  }

  @GetMapping("/redeem-offers")
  public ResponseEntity<ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers>
      redeemOffers(@RequestParam("f_id") final String fId) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getRedeemedOffers(fId));
  }

  @PostMapping("/activate-offers")
  public ResponseEntity<
          ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation>
      activateOffer(
          @RequestBody
              final ActivateSCOfferInputStatementCreditOfferActivation
                  statementCreditOfferActivation)
          throws ApiException {
    return ResponseEntity.ok(
        referenceApplicationGateway.activateStatementCreditOffer(statementCreditOfferActivation));
  }

  @GetMapping("/activation-details")
  public ResponseEntity<
          ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail>
      activationDetails(
          @RequestParam("f_id") final String fId,
          @RequestParam("activation_id") final String activationId)
          throws ApiException {
    return ResponseEntity.ok(
        referenceApplicationGateway.getStatementCreditActivationDetail(fId, activationId));
  }

  @PostMapping("/user-feedbacks")
  public ResponseEntity<ResponseWrapperUserFeedbackOutputResponse> sendUserFeedback(
      @RequestBody final UserFeedbackInput userFeedback) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.sendUserFeedback(userFeedback));
  }

  @GetMapping("/user-feedbacks")
  public ResponseEntity<ResponseWrapperUserFeedbackOutputListResponse> userFeedbacks(
      @RequestParam("f_id") final String fId) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getUserFeedback(fId));
  }

  @GetMapping("/matched-offers")
  public ResponseEntity<ResponseWrapperMatchedOfferDetailsResponseMatchedOffers> matchedOffers(
      @RequestParam("f_id") final String fId) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getMatchedOffers(fId));
  }

  @GetMapping("/user-savings")
  public ResponseEntity<ResponseWrapperUserSavingsResponse> userSavings(
      @RequestParam("f_id") final String fId) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getUserSavings(fId));
  }

  @GetMapping("/user-tokens")
  public ResponseEntity<ResponseWrapperUserTokenOutputResponse> userTokens(
      @RequestParam("f_id") final String fId) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getUserToken(fId));
  }

  @GetMapping("/offer-details")
  public ResponseEntity<ResponseWrapperDetailedOffersResponseDetailedOffers> offerDetails(
      @RequestParam("f_id") final String fId, @RequestParam("offer_id") final String offerId)
      throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getOfferDetails(fId, offerId));
  }

  @GetMapping("/offers")
  public ResponseEntity<BrowseOffersResponse> offers(@RequestParam("f_id") final String fId)
      throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getOffers(fId));
  }

  @PostMapping("/bulk-activations")
  public ResponseEntity<BulkActivationRequestResponse> sendBulkActivations(
      @RequestBody final BulkActivationApiRequest bulkActivation) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.sendBulkActivations(bulkActivation));
  }

  @GetMapping("/adjustments")
  public ResponseEntity<AdjustmentResponse> adjustmentDetails(
      @RequestParam(name = "f_id") final String fId,
      @RequestParam(required = false, name = "offset") final Integer offset,
      @RequestParam(required = false, name = "limit") final Integer limit,
      @RequestParam(required = false, name = "start_date") final String startDate,
      @RequestParam(required = false, name = "end_date") final String endDate,
      @RequestParam(required = false, name = "date_filter") final String dateFilter)
      throws ApiException {
    return ResponseEntity.ok(
        referenceApplicationGateway.getAdjustments(
            fId, offset, limit, startDate, endDate, dateFilter));
  }
}
