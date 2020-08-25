package com.mastercard.developer.controller;

import com.mastercard.ApiException;
import com.mastercard.api.model.ActivateSCOfferInputStatementCreditOfferActivation;
import com.mastercard.api.model.DetailedOffersResponseDetailedOffers;
import com.mastercard.api.model.DetailedRedeemedOfferListResponseRedeemedOffers;
import com.mastercard.api.model.MatchedOfferDetailsResponseMatchedOffers;
import com.mastercard.api.model.StatementCreditOfferDetailsResponseStatementCreditOfferActivation;
import com.mastercard.api.model.StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail;
import com.mastercard.api.model.UserFeedbackInput;
import com.mastercard.api.model.UserFeedbackOutputListResponse;
import com.mastercard.api.model.UserFeedbackOutputResponse;
import com.mastercard.api.model.UserSavingsResponse;
import com.mastercard.api.model.UserTokenOutputResponse;
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
  public ResponseEntity<DetailedRedeemedOfferListResponseRedeemedOffers> redeemOffers(
      @RequestParam("f_id") final String fId) throws ApiException {
    final DetailedRedeemedOfferListResponseRedeemedOffers redeemedOffers =
        referenceApplicationGateway.getRedeemedOffers(fId);

    return ResponseEntity.ok(redeemedOffers);
  }

  @PostMapping("/activate-offers")
  public ResponseEntity<StatementCreditOfferDetailsResponseStatementCreditOfferActivation>
  activateOffer(
      @RequestBody final ActivateSCOfferInputStatementCreditOfferActivation
          statementCreditOfferActivation)
      throws ApiException {
    final StatementCreditOfferDetailsResponseStatementCreditOfferActivation activatedOffer =
        referenceApplicationGateway.activateStatementCreditOffer(statementCreditOfferActivation);
    return ResponseEntity.ok(activatedOffer);
  }

  @GetMapping("/activation-details")
  public ResponseEntity<StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail>
  activationDetails(
      @RequestParam("f_id") final String fId,
      @RequestParam("activation_id") final String activationId)
      throws ApiException {
    final StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
        activationDetails =
        referenceApplicationGateway.getStatementCreditActivationDetail(fId, activationId);
    return ResponseEntity.ok(activationDetails);
  }

  @PostMapping("/user-feedbacks")
  public ResponseEntity<UserFeedbackOutputResponse> sendUserFeedback(
      @RequestBody final UserFeedbackInput userFeedback) throws ApiException {
    final UserFeedbackOutputResponse userFeedbackResponse =
        referenceApplicationGateway.sendUserFeedback(userFeedback);
    return ResponseEntity.ok(userFeedbackResponse);
  }

  @GetMapping("/user-feedbacks")
  public ResponseEntity<UserFeedbackOutputListResponse> userFeedbacks(
      @RequestParam("f_id") final String fId) throws ApiException {
    final UserFeedbackOutputListResponse userFeedbackResponse =
        referenceApplicationGateway.getUserFeedback(fId);
    return ResponseEntity.ok(userFeedbackResponse);
  }

  @GetMapping("/matched-offers")
  public ResponseEntity<MatchedOfferDetailsResponseMatchedOffers> matchedOffers(
      @RequestParam("f_id") final String fId) throws ApiException {
    final MatchedOfferDetailsResponseMatchedOffers personalizedOffers =
        referenceApplicationGateway.getMatchedOffers(fId);
    return ResponseEntity.ok(personalizedOffers);
  }

  @GetMapping("/user-savings")
  public ResponseEntity<UserSavingsResponse> userSavings(@RequestParam("f_id") final String fId)
      throws ApiException {
    final UserSavingsResponse totalUserSavings = referenceApplicationGateway.getUserSavings(fId);

    return ResponseEntity.ok(totalUserSavings);
  }

  @GetMapping("/user-tokens")
  public ResponseEntity<UserTokenOutputResponse> userTokens(@RequestParam("f_id") final String fId)
      throws ApiException {
    final UserTokenOutputResponse userToken = referenceApplicationGateway.getUserToken(fId);

    return ResponseEntity.ok(userToken);
  }

  @GetMapping("/offer-details")
  public ResponseEntity<DetailedOffersResponseDetailedOffers> offerDetails(
      @RequestParam("f_id") final String fId, @RequestParam("offer_id") final String offerId)
      throws ApiException {
    final DetailedOffersResponseDetailedOffers offerDetails =
        referenceApplicationGateway.getOfferDetails(fId, offerId);

    return ResponseEntity.ok(offerDetails);
  }
}
