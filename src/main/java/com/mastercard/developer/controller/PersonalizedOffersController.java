package com.mastercard.developer.controller;

import com.mastercard.ApiException;
import com.mastercard.api.model.*;
import com.mastercard.developer.service.PersonalizedOffersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping("/user-presentment/access-tokens")
  public AccessTokenResponse createAccessToken(@RequestBody AccessTokenRequest accessToken)
      throws ApiException {
    return referenceApplicationGateway.getToken(accessToken);
  }

  @GetMapping("/user-presentment/offers")
  public UserOffersResponse getOffers (
      @RequestHeader(name = "Accept-Language", defaultValue = "", required = false) final String language,
      @RequestParam(value = "offer_type", required = false) final String offerType,
      @RequestParam(value = "category", required = false) final String category,
      @RequestParam(value = "offer_country", required = false) final String offerCountry,
      @RequestParam(value = "offset", defaultValue = "0", required = false) final Integer offset,
      @RequestParam(value = "limit", defaultValue = "10", required = false) final Integer limit,
      @RequestParam(value = "sort", required = false) final String sort,
      @RequestHeader(name = "x-auth-token") final String xAuthToken
  ) throws ApiException {
    return referenceApplicationGateway.getOffers(language, offerType, category, offerCountry, offset, limit, sort, xAuthToken);
  }

  @GetMapping("/user-presentment/offers/{offer_id}")
  public UserOfferDetailsResponse getOfferDetails (
      @PathVariable(value = "offer_id") final String offerId,
      @RequestHeader(name = "Accept-Language", defaultValue = "", required = false) final String language,
      @RequestHeader(name = "x-auth-token") final String xAuthToken) throws ApiException {
    return referenceApplicationGateway.getOfferDetails(offerId, language, xAuthToken);
  }

  @GetMapping("/user-presentment/offer-ratings")
  public OfferRatingsResponse getOfferRatings (
          @RequestParam(value = "current", defaultValue = "true", required = false) final String current,
          @RequestParam(value = "offset", defaultValue = "0", required = false) final Integer offset,
          @RequestParam(value = "limit", defaultValue = "10", required = false) final Integer limit,
          @RequestHeader(name = "x-auth-token") final String xAuthToken
  ) throws ApiException {
    return referenceApplicationGateway.getOfferRatings( current , offset, limit,xAuthToken);
  }

  @GetMapping("/user-presentment/offer-ratings/{offer_id}")
  public OfferRatingResponse getOfferRating (
          @PathVariable(value = "offer_id") final String offerId,
          @RequestHeader(name = "x-auth-token") final String xAuthToken) throws ApiException {
    return referenceApplicationGateway.getOfferRating(offerId,xAuthToken);
  }

  @PostMapping("/user-presentment/offer-ratings/{offer_id}/likes")
  public OfferRatingResponse createOfferRating (
          @PathVariable(value = "offer_id") final String offerId,
          @RequestBody OfferRatingRequest offerRatingRequest,
          @RequestHeader(name = "x-auth-token") final String xAuthToken) throws ApiException {
    return referenceApplicationGateway.createOfferRating(offerId,offerRatingRequest,xAuthToken);
  }

  @PostMapping("/user-presentment/activation-requests")
  public Activations processActivations (@RequestBody RequestedActivation requestedActivation,
          @RequestHeader(name = "x-auth-token") final String xAuthToken) throws ApiException {
    return referenceApplicationGateway.processActivations(requestedActivation,xAuthToken);
  }

  @GetMapping("/user-presentment/savings")
  public UserSavingsResponse getUserPresentmentSavings (
          @RequestHeader(name = "x-auth-token") final String xAuthToken) throws ApiException {
    return referenceApplicationGateway.getUserPresentmentSavings(xAuthToken);
  }

  @GetMapping("/user-presentment/adjustments")
  public UserAdjustments getUserPresentmentAdjustments (  @RequestParam(value = "start_date", required = false)
  final String  startDate,
          @RequestParam(value = "end_date", required = false)
          final String endDate,
          @RequestParam(value = "date_filter", required = false) final String dateFilter,
          @RequestParam(value = "offset", defaultValue = "0", required = false) final Integer offset,
          @RequestParam(value = "limit", defaultValue = "10", required = false) final Integer limit,
          @RequestHeader(name = "x-auth-token") final String xAuthToken) throws ApiException {
    return referenceApplicationGateway.getUserPresentmentAdjustments(startDate,endDate,dateFilter,offset,limit,xAuthToken);
  }
}
