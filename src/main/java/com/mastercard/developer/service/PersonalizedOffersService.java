package com.mastercard.developer.service;

import static java.util.Objects.requireNonNull;

import com.mastercard.ApiException;
import com.mastercard.api.ActivationsApi;
import com.mastercard.api.AdjustmentsApi;
import com.mastercard.api.FilterUserOffersApi;
import com.mastercard.api.MatchedOffersApi;
import com.mastercard.api.OfferDetailsApi;
import com.mastercard.api.OfferRatingsApi;
import com.mastercard.api.OffersApi;
import com.mastercard.api.RedeemedOffersApi;
import com.mastercard.api.StatementCreditActivationsApi;
import com.mastercard.api.TokensApi;
import com.mastercard.api.UserAdjustmentsApi;
import com.mastercard.api.UserFeedbackApi;
import com.mastercard.api.UserOffersApi;
import com.mastercard.api.UserSavingsApi;
import com.mastercard.api.UserTokenApi;
import com.mastercard.api.model.ActivateSCOfferInputStatementCreditOfferActivation;
import com.mastercard.api.model.Activations;
import com.mastercard.api.model.BrowseOffers;
import com.mastercard.api.model.OfferDetails;
import com.mastercard.api.model.OfferFilter;
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
import com.mastercard.api.model.UserAccessToken;
import com.mastercard.api.model.UserAdjustment;
import com.mastercard.api.model.UserAdjustments;
import com.mastercard.api.model.UserFeedbackInput;
import com.mastercard.api.model.UserOfferRating;
import com.mastercard.api.model.UserOfferRatings;
import com.mastercard.api.model.UserOffers;
import com.mastercard.api.model.UserSavings;
import com.mastercard.developer.service.domain.GenericOffersCriterion;
import com.mastercard.developer.service.domain.MatchedOffersCriterion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PersonalizedOffersService {

  private static final String ACTIVATION_ID_IS_REQUIRED = "Activation ID is required.";
  private static final String AUTH_TOKEN_IS_REQUIRED = "Auth Token is required.";
  private static final String FEEDBACK_IS_REQUIRED = "Feedback is required.";
  private static final String FID_IS_REQUIRED = "F_ID is required.";
  private static final String OFFER_ID_IS_REQUIRED = "Offer ID is required.";
  private static final String USER_TOKEN_IS_REQUIRED = "User Token is required.";
  private static final String USER_ID_IS_REQUIRED = "User Id is required.";
  private static final String UTC_OFFSET_IS_REQUIRED = "UtcOffset is required.";
  private static final String ACTIVATION_REQUEST_IS_REQUIRED = "ActivationRequest is required.";

  private final MatchedOffersApi matchedOffersApi;
  private final OfferDetailsApi offerDetailsApi;
  private final RedeemedOffersApi redeemedOffersApi;
  private final StatementCreditActivationsApi statementCreditActivationsApi;
  private final String authInfo;
  private final UserFeedbackApi userFeedbackApi;
  private final UserSavingsApi userSavingsApi;
  private final UserTokenApi userTokenApi;
  private final OffersApi offersApi;
  private final FilterUserOffersApi filterUserOffersApi;
  private final AdjustmentsApi adjustmentsApi;
  private final TokensApi tokensApi;
  private final UserOffersApi userOffersApi;
  private final OfferRatingsApi userRatingsApi;
  private final UserAdjustmentsApi userAdjustmentsApi;
  private final ActivationsApi activationsApi;

  public PersonalizedOffersService(
      @Value("${mastercard.api.auth.token}") final String authInfo,
      final UserTokenApi userTokenApi,
      final MatchedOffersApi matchedOffersApi,
      final OfferDetailsApi offerDetailsApi,
      final RedeemedOffersApi redeemedOffersApi,
      final StatementCreditActivationsApi statementCreditActivationsApi,
      final UserFeedbackApi userFeedbackApi,
      final UserSavingsApi userSavingsApi,
      final OffersApi offersApi,
      final FilterUserOffersApi filterUserOffersApi,
      final AdjustmentsApi adjustmentsApi,
      final TokensApi tokensApi,
      final UserOffersApi userOffersApi,
      final OfferRatingsApi userRatingsApi,
      final UserAdjustmentsApi userAdjustmentsApi,
      final ActivationsApi activationsApi) {
    this.authInfo = authInfo;
    this.userTokenApi = userTokenApi;
    this.matchedOffersApi = matchedOffersApi;
    this.offerDetailsApi = offerDetailsApi;
    this.statementCreditActivationsApi = statementCreditActivationsApi;
    this.redeemedOffersApi = redeemedOffersApi;
    this.userFeedbackApi = userFeedbackApi;
    this.userSavingsApi = userSavingsApi;
    this.offersApi = offersApi;
    this.filterUserOffersApi = filterUserOffersApi;
    this.adjustmentsApi = adjustmentsApi;
    this.tokensApi = tokensApi;
    this.userOffersApi = userOffersApi;
    this.userRatingsApi = userRatingsApi;
    this.userAdjustmentsApi = userAdjustmentsApi;
    this.activationsApi = activationsApi;
  }

  public ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers getRedeemedOffers(
      final String fid) throws ApiException {
    ResponseWrapperUserTokenOutputWrapper userToken = getUserToken(fid);

    return getRedeemedOffers(
        GenericOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation
      activateStatementCreditOffer(
          final ActivateSCOfferInputStatementCreditOfferActivation statementCreditOfferActivation)
          throws ApiException {
    return statementCreditActivationsApi.activateStatementCreditOffer(
        statementCreditOfferActivation);
  }

  public ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
      getStatementCreditActivationDetail(final String fid, final String activationId)
          throws ApiException {
    ResponseWrapperUserTokenOutputWrapper userToken = getUserToken(fid);

    return getStatementCreditActivationDetail(
        GenericOffersCriterion.builder()
            .fid(fid)
            .activationId(activationId)
            .userToken(getToken(userToken))
            .build());
  }

  public ResponseWrapperUserFeedbackOutputWrapper sendUserFeedback(UserFeedbackInput userFeedback)
      throws ApiException {
    requireNonNull(userFeedback.getFid(), FID_IS_REQUIRED);
    requireNonNull(userFeedback.getUserToken(), USER_TOKEN_IS_REQUIRED);
    requireNonNull(userFeedback.getOfferId(), OFFER_ID_IS_REQUIRED);
    requireNonNull(userFeedback.getFeedback(), FEEDBACK_IS_REQUIRED);

    return userFeedbackApi.sendUserFeedback(userFeedback);
  }

  public ResponseWrapperUserFeedbackOutputList getUserFeedback(String fid) throws ApiException {
    ResponseWrapperUserTokenOutputWrapper userToken = getUserToken(fid);

    return getUserFeedback(
        GenericOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public ResponseWrapperMatchedOfferDetailsResponseMatchedOffers getMatchedOffers(final String fid)
      throws ApiException {
    ResponseWrapperUserTokenOutputWrapper userToken = getUserToken(fid);

    return getMatchedOffers(
        MatchedOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public ResponseWrapperUserSavingsOutputWrapper getUserSavings(String fid) throws ApiException {
    ResponseWrapperUserTokenOutputWrapper userToken = getUserToken(fid);

    return getUserSavings(
        GenericOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public ResponseWrapperUserTokenOutputWrapper getUserToken(String fid) throws ApiException {
    requireNonNull(fid, FID_IS_REQUIRED);

    return userTokenApi.getUserToken(fid, authInfo, null);
  }

  public ResponseWrapperDetailedOffersResponseDetailedOffers getOfferDetails(
      String fid, String offerId) throws ApiException {
    ResponseWrapperUserTokenOutputWrapper userToken = getUserToken(fid);

    return getOfferDetails(
        GenericOffersCriterion.builder()
            .fid(fid)
            .offerId(offerId)
            .userToken(getToken(userToken))
            .build());
  }

  public BrowseOffers getOffers(String fid) throws ApiException {
    ResponseWrapperUserTokenOutputWrapper userToken = getUserToken(fid);

    return getOffers(
        GenericOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build(), null);
  }

  public UserAdjustment getAdjustments(
      String clientId,
      String fid,
      Integer offset,
      Integer limit,
      String startDate,
      String endDate,
      String dateFilter)
      throws ApiException {

    return getAdjustments(
        GenericOffersCriterion.builder()
            .fid(fid)
            .offset(offset)
            .limit(limit)
            .startDate(startDate)
            .endDate(endDate)
            .dateFilter(dateFilter)
            .build(), clientId);
  }

  public UserAdjustment getAdjustments(GenericOffersCriterion adjustmentsCriterion, String clientId)
      throws ApiException {
    requireNonNull(adjustmentsCriterion.getFid(), FID_IS_REQUIRED);

    return adjustmentsApi.getAdjustments(
        adjustmentsCriterion.getFid(),
        clientId,
        adjustmentsCriterion.getOffset(),
        adjustmentsCriterion.getLimit(),
        adjustmentsCriterion.getStartDate(),
        adjustmentsCriterion.getEndDate(),
        adjustmentsCriterion.getDateFilter());
  }

  public ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers getRedeemedOffers(
      final GenericOffersCriterion redeemOffersCriterion) throws ApiException {
    requireNonNull(redeemOffersCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(redeemOffersCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    return redeemedOffersApi.getRedeemedOfferDetails(
        redeemOffersCriterion.getFid(),
        redeemOffersCriterion.getUserToken(),
        redeemOffersCriterion.getItemsPerPage(),
        redeemOffersCriterion.getPageNumber(),
        redeemOffersCriterion.getLang());
  }

  public ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
      getStatementCreditActivationDetail(GenericOffersCriterion statementCreditActivationCriterion)
          throws ApiException {
    requireNonNull(statementCreditActivationCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(statementCreditActivationCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);
    requireNonNull(statementCreditActivationCriterion.getActivationId(), ACTIVATION_ID_IS_REQUIRED);

    return statementCreditActivationsApi.getStatementCreditActivationDetail(
        statementCreditActivationCriterion.getFid(),
        statementCreditActivationCriterion.getActivationId(),
        statementCreditActivationCriterion.getUserToken(),
        statementCreditActivationCriterion.getLang());
  }

  public ResponseWrapperUserFeedbackOutputList getUserFeedback(
      GenericOffersCriterion userFeedbackCriterion) throws ApiException {
    requireNonNull(userFeedbackCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(userFeedbackCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    return userFeedbackApi.getUserFeedback(
        userFeedbackCriterion.getFid(),
        userFeedbackCriterion.getUserToken(),
        userFeedbackCriterion.getOfferId(),
        userFeedbackCriterion.getFeedback());
  }

  public ResponseWrapperMatchedOfferDetailsResponseMatchedOffers getMatchedOffers(
      final MatchedOffersCriterion matchedOffersCriterion) throws ApiException {
    requireNonNull(matchedOffersCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(matchedOffersCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    return matchedOffersApi.matchedOffers(
        matchedOffersCriterion.getFid(),
        matchedOffersCriterion.getUserToken(),
        matchedOffersCriterion.getMerchantName(),
        matchedOffersCriterion.getCategory(),
        matchedOffersCriterion.getOfferType(),
        matchedOffersCriterion.getPageNumber(),
        matchedOffersCriterion.getItemsPerPage(),
        matchedOffersCriterion.getLang(),
        matchedOffersCriterion.getUserFeedback(),
        matchedOffersCriterion.getPresentmentDate());
  }

  public ResponseWrapperUserSavingsOutputWrapper getUserSavings(
      GenericOffersCriterion userSavingsCriterion) throws ApiException {
    requireNonNull(userSavingsCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(userSavingsCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    return userSavingsApi.getUserSavings(
        userSavingsCriterion.getFid(), userSavingsCriterion.getUserToken());
  }

  private String getToken(ResponseWrapperUserTokenOutputWrapper userToken) {
    return requireNonNull(requireNonNull(userToken.getResponse()).getUserToken()).getToken();
  }

  public ResponseWrapperDetailedOffersResponseDetailedOffers getOfferDetails(
      GenericOffersCriterion offerDetailsCriterion) throws ApiException {
    requireNonNull(offerDetailsCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(offerDetailsCriterion.getOfferId(), OFFER_ID_IS_REQUIRED);
    requireNonNull(offerDetailsCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    return offerDetailsApi.getOfferDetails(
        offerDetailsCriterion.getFid(),
        offerDetailsCriterion.getOfferId(),
        offerDetailsCriterion.getUserToken(),
        offerDetailsCriterion.getLang());
  }

  public BrowseOffers getOffers(GenericOffersCriterion offersCriterion, String clientId) throws ApiException {
    requireNonNull(offersCriterion.getFid(), FID_IS_REQUIRED);

    return offersApi.browseOffers(
        offersCriterion.getFid(),
        clientId,
        offersCriterion.getIssuerIca(),
        offersCriterion.getBankProductCode(),
        offersCriterion.getOfferType(),
        offersCriterion.getCategory(),
        offersCriterion.getOfferCountry(),
        offersCriterion.getLanguages(),
        offersCriterion.getOffset(),
        offersCriterion.getLimit());
  }

  public UserAccessToken getToken(RequestedAccessToken accessToken) throws ApiException {
    requireNonNull(accessToken.getFiId(), FID_IS_REQUIRED);
    requireNonNull(accessToken.getUserId(), USER_ID_IS_REQUIRED);
    requireNonNull(accessToken.getUtcOffset(), UTC_OFFSET_IS_REQUIRED);

    return tokensApi.createAccessToken(accessToken);
  }

  public UserOffers getOffers(
      String acceptLanguage,
      String offerType,
      String category,
      String offerCountry,
      Boolean active,
      Integer offset,
      Integer limit,
      String xAuthToken)
      throws ApiException {
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);

    return userOffersApi.getOffers(
        xAuthToken, acceptLanguage, offerType, category, offerCountry, active, offset, limit);
  }

  public UserOffers filterOffers(String xAuthToken, OfferFilter offerFilter, String language, String clientId)
      throws ApiException {
    return filterUserOffersApi.searchOffers(xAuthToken, offerFilter, language, clientId);
  }

  public OfferDetails getOfferDetails(String offerId, String xAuthToken, String acceptLanguage)
      throws ApiException {
    requireNonNull(offerId, OFFER_ID_IS_REQUIRED);
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);

    return userOffersApi.getOffer(offerId, xAuthToken, acceptLanguage);
  }

  public UserOfferRatings getOfferRatings(
      String current, Integer offset, Integer limit, String xAuthToken) throws ApiException {
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);
    return userRatingsApi.getOfferRatings(xAuthToken, current, offset, limit);
  }

  public UserOfferRating getOfferRating(String offerId, String xAuthToken) throws ApiException {
    requireNonNull(offerId, OFFER_ID_IS_REQUIRED);
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);

    return userRatingsApi.getOfferRating(offerId, xAuthToken);
  }

  public UserOfferRating createOfferRating(
      String offerId, RequestedOfferRating offerRatingRequest, String xAuthToken)
      throws ApiException {
    requireNonNull(offerId, OFFER_ID_IS_REQUIRED);
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);
    return userRatingsApi.createOfferRating(offerId, xAuthToken, offerRatingRequest);
  }

  public Activations processActivations(RequestedActivation requestedActivation, String xAuthToken)
      throws ApiException {
    requireNonNull(requestedActivation, ACTIVATION_REQUEST_IS_REQUIRED);
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);
    return activationsApi.processActivations(xAuthToken, requestedActivation);
  }

  public UserSavings getUserPresentmentSavings(String xAuthToken) throws ApiException {
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);
    return userSavingsApi.getUserPresentmentSavings(xAuthToken);
  }

  public UserAdjustments getUserPresentmentAdjustments(
      String startDate,
      String endDate,
      String dateFilter,
      Integer offset,
      Integer limit,
      String xAuthToken)
      throws ApiException {
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);
    return userAdjustmentsApi.getUserPresentmentAdjustments(
        xAuthToken, startDate, endDate, dateFilter, offset, limit);
  }
}
