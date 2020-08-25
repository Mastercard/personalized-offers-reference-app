package com.mastercard.developer.service;

import static java.util.Objects.requireNonNull;

import com.mastercard.ApiException;
import com.mastercard.api.MatchedOffersApi;
import com.mastercard.api.OfferDetailsApi;
import com.mastercard.api.RedeemedOffersApi;
import com.mastercard.api.StatementCreditActivationsApi;
import com.mastercard.api.UserFeedbackApi;
import com.mastercard.api.UserSavingsApi;
import com.mastercard.api.UserTokenApi;
import com.mastercard.api.model.ActivateSCOfferInputStatementCreditOfferActivation;
import com.mastercard.api.model.DetailedOffersResponseDetailedOffers;
import com.mastercard.api.model.DetailedRedeemedOfferListResponseRedeemedOffers;
import com.mastercard.api.model.MatchedOfferDetailsResponseMatchedOffers;
import com.mastercard.api.model.ResponseWrapperDetailedOffersResponseDetailedOffers;
import com.mastercard.api.model.ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers;
import com.mastercard.api.model.ResponseWrapperMatchedOfferDetailsResponseMatchedOffers;
import com.mastercard.api.model.ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation;
import com.mastercard.api.model.ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail;
import com.mastercard.api.model.ResponseWrapperUserFeedbackOutputListResponse;
import com.mastercard.api.model.ResponseWrapperUserFeedbackOutputResponse;
import com.mastercard.api.model.ResponseWrapperUserSavingsResponse;
import com.mastercard.api.model.ResponseWrapperUserTokenOutputResponse;
import com.mastercard.api.model.StatementCreditOfferDetailsResponseStatementCreditOfferActivation;
import com.mastercard.api.model.StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail;
import com.mastercard.api.model.UserFeedbackInput;
import com.mastercard.api.model.UserFeedbackOutputListResponse;
import com.mastercard.api.model.UserFeedbackOutputResponse;
import com.mastercard.api.model.UserSavingsResponse;
import com.mastercard.api.model.UserTokenOutputResponse;
import com.mastercard.developer.service.domain.GenericOffersCriterion;
import com.mastercard.developer.service.domain.MatchedOffersCriterion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PersonalizedOffersService {

  private static final String ACTIVATION_ID_IS_REQUIRED = "Activation ID is required.";
  private static final String FEEDBACK_IS_REQUIRED = "Feedback is required.";
  private static final String FID_IS_REQUIRED = "F_ID is required.";
  private static final String OFFER_ID_IS_REQUIRED = "Offer ID is required.";
  private static final String USER_TOKEN_IS_REQUIRED = "User Token is required.";

  private final MatchedOffersApi matchedOffersApi;
  private final OfferDetailsApi offerDetailsApi;
  private final RedeemedOffersApi redeemedOffersApi;
  private final StatementCreditActivationsApi statementCreditActivationsApi;
  private final String authInfo;
  private final UserFeedbackApi userFeedbackApi;
  private final UserSavingsApi userSavingsApi;
  private final UserTokenApi userTokenApi;

  public PersonalizedOffersService(
      @Value("${mastercard.api.auth.token}") final String authInfo,
      final UserTokenApi userTokenApi,
      final MatchedOffersApi matchedOffersApi,
      final OfferDetailsApi offerDetailsApi,
      final RedeemedOffersApi redeemedOffersApi,
      final StatementCreditActivationsApi statementCreditActivationsApi,
      final UserFeedbackApi userFeedbackApi,
      final UserSavingsApi userSavingsApi) {
    this.authInfo = authInfo;
    this.userTokenApi = userTokenApi;
    this.matchedOffersApi = matchedOffersApi;
    this.offerDetailsApi = offerDetailsApi;
    this.statementCreditActivationsApi = statementCreditActivationsApi;
    this.redeemedOffersApi = redeemedOffersApi;
    this.userFeedbackApi = userFeedbackApi;
    this.userSavingsApi = userSavingsApi;
  }

  public DetailedRedeemedOfferListResponseRedeemedOffers getRedeemedOffers(final String fid)
      throws ApiException {
    UserTokenOutputResponse userToken = getUserToken(fid);

    return getRedeemedOffers(
        GenericOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public StatementCreditOfferDetailsResponseStatementCreditOfferActivation
  activateStatementCreditOffer(
      final ActivateSCOfferInputStatementCreditOfferActivation statementCreditOfferActivation)
      throws ApiException {
    final ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation
        response =
        statementCreditActivationsApi.activateStatementCreditOffer(
            statementCreditOfferActivation);

    return response.getResponse();
  }

  public StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
  getStatementCreditActivationDetail(final String fid, final String activationId)
      throws ApiException {
    UserTokenOutputResponse userToken = getUserToken(fid);

    return getStatementCreditActivationDetail(
        GenericOffersCriterion.builder()
            .fid(fid)
            .activationId(activationId)
            .userToken(getToken(userToken))
            .build());
  }

  public UserFeedbackOutputResponse sendUserFeedback(UserFeedbackInput userFeedback)
      throws ApiException {
    requireNonNull(userFeedback.getFid(), FID_IS_REQUIRED);
    requireNonNull(userFeedback.getUserToken(), USER_TOKEN_IS_REQUIRED);
    requireNonNull(userFeedback.getOfferId(), OFFER_ID_IS_REQUIRED);
    requireNonNull(userFeedback.getFeedback(), FEEDBACK_IS_REQUIRED);

    final ResponseWrapperUserFeedbackOutputResponse response =
        userFeedbackApi.sendUserFeedback(userFeedback);
    return response.getResponse();
  }

  public UserFeedbackOutputListResponse getUserFeedback(String fid) throws ApiException {
    UserTokenOutputResponse userToken = getUserToken(fid);

    return getUserFeedback(
        GenericOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public MatchedOfferDetailsResponseMatchedOffers getMatchedOffers(final String fid)
      throws ApiException {
    UserTokenOutputResponse userToken = getUserToken(fid);

    return getMatchedOffers(
        MatchedOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public UserSavingsResponse getUserSavings(String fid) throws ApiException {
    UserTokenOutputResponse userToken = getUserToken(fid);

    return getUserSavings(
        GenericOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public UserTokenOutputResponse getUserToken(String fid) throws ApiException {
    requireNonNull(fid, FID_IS_REQUIRED);

    final ResponseWrapperUserTokenOutputResponse response =
        userTokenApi.getUserToken(fid, authInfo, null);

    return response.getResponse();
  }

  public DetailedOffersResponseDetailedOffers getOfferDetails(String fid, String offerId)
      throws ApiException {
    UserTokenOutputResponse userToken = getUserToken(fid);

    return getOfferDetails(
        GenericOffersCriterion.builder()
            .fid(fid)
            .offerId(offerId)
            .userToken(getToken(userToken))
            .build());
  }

  private DetailedRedeemedOfferListResponseRedeemedOffers getRedeemedOffers(
      final GenericOffersCriterion redeemOffersCriterion) throws ApiException {
    requireNonNull(redeemOffersCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(redeemOffersCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    final ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers response =
        redeemedOffersApi.getRedeemedOfferDetails(
            redeemOffersCriterion.getFid(),
            redeemOffersCriterion.getUserToken(),
            redeemOffersCriterion.getItemsPerPage(),
            redeemOffersCriterion.getPageNumber(),
            redeemOffersCriterion.getLang());
    return response.getResponse();
  }

  private StatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
  getStatementCreditActivationDetail(GenericOffersCriterion statementCreditActivationCriterion)
      throws ApiException {
    requireNonNull(statementCreditActivationCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(statementCreditActivationCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);
    requireNonNull(statementCreditActivationCriterion.getActivationId(), ACTIVATION_ID_IS_REQUIRED);

    final ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
        response =
        statementCreditActivationsApi.getStatementCreditActivationDetail(
            statementCreditActivationCriterion.getFid(),
            statementCreditActivationCriterion.getActivationId(),
            statementCreditActivationCriterion.getUserToken(),
            statementCreditActivationCriterion.getLang());
    return response.getResponse();
  }

  private UserFeedbackOutputListResponse getUserFeedback(
      GenericOffersCriterion userFeedbackCriterion) throws ApiException {
    requireNonNull(userFeedbackCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(userFeedbackCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    final ResponseWrapperUserFeedbackOutputListResponse response =
        userFeedbackApi.getUserFeedback(
            userFeedbackCriterion.getFid(),
            userFeedbackCriterion.getUserToken(),
            userFeedbackCriterion.getOfferId(),
            userFeedbackCriterion.getFeedback());
    return response.getResponse();
  }

  private MatchedOfferDetailsResponseMatchedOffers getMatchedOffers(
      final MatchedOffersCriterion matchedOffersCriterion) throws ApiException {
    requireNonNull(matchedOffersCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(matchedOffersCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    final ResponseWrapperMatchedOfferDetailsResponseMatchedOffers response =
        matchedOffersApi.matchedOffers(
            matchedOffersCriterion.getFid(),
            matchedOffersCriterion.getUserToken(),
            matchedOffersCriterion.getMerchantName(),
            matchedOffersCriterion.getCategory(),
            matchedOffersCriterion.getOfferType(),
            matchedOffersCriterion.getLatitude(),
            matchedOffersCriterion.getLongitude(),
            matchedOffersCriterion.getRadius(),
            matchedOffersCriterion.getPageNumber(),
            matchedOffersCriterion.getItemsPerPage(),
            matchedOffersCriterion.getLang(),
            matchedOffersCriterion.getUserFeedback(),
            matchedOffersCriterion.getPresentmentDate());
    return response.getResponse();
  }

  private UserSavingsResponse getUserSavings(GenericOffersCriterion userSavingsCriterion)
      throws ApiException {
    requireNonNull(userSavingsCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(userSavingsCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    final ResponseWrapperUserSavingsResponse response =
        userSavingsApi.getUserSavings(
            userSavingsCriterion.getFid(), userSavingsCriterion.getUserToken());
    return response.getResponse();
  }

  private String getToken(UserTokenOutputResponse userToken) {
    return requireNonNull(userToken.getUserToken()).getToken();
  }

  private DetailedOffersResponseDetailedOffers getOfferDetails(
      GenericOffersCriterion offerDetailsCriterion) throws ApiException {
    requireNonNull(offerDetailsCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(offerDetailsCriterion.getOfferId(), OFFER_ID_IS_REQUIRED);
    requireNonNull(offerDetailsCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    final ResponseWrapperDetailedOffersResponseDetailedOffers response =
        offerDetailsApi.getOfferDetails(
            offerDetailsCriterion.getFid(),
            offerDetailsCriterion.getOfferId(),
            offerDetailsCriterion.getUserToken(),
            offerDetailsCriterion.getLang());
    return response.getResponse();
  }
}
