package com.mastercard.developer.service;

import com.mastercard.ApiException;
import com.mastercard.api.*;
import com.mastercard.api.model.*;
import com.mastercard.developer.service.domain.GenericOffersCriterion;
import com.mastercard.developer.service.domain.MatchedOffersCriterion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

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
  private final AdjustmentsApi adjustmentsApi;
  private final TokensApi tokensApi;
  private final OfferApi offerApi;
  private final UserRatingsApi userRatingsApi;
  private final UserRatingApi userRatingApi;
  private final CreateUserRatingApi createUserRatingApi;
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
      final AdjustmentsApi adjustmentsApi,
      final TokensApi tokensApi,
      final OfferApi offerApi,
      final UserRatingsApi userRatingsApi,
      final UserRatingApi userRatingApi,
      final CreateUserRatingApi createUserRatingApi,
     final ActivationsApi activationsApi     ) {
    this.authInfo = authInfo;
    this.userTokenApi = userTokenApi;
    this.matchedOffersApi = matchedOffersApi;
    this.offerDetailsApi = offerDetailsApi;
    this.statementCreditActivationsApi = statementCreditActivationsApi;
    this.redeemedOffersApi = redeemedOffersApi;
    this.userFeedbackApi = userFeedbackApi;
    this.userSavingsApi = userSavingsApi;
    this.offersApi = offersApi;
    this.adjustmentsApi = adjustmentsApi;
    this.tokensApi = tokensApi;
    this.offerApi = offerApi;
    this.userRatingsApi = userRatingsApi;
    this.userRatingApi = userRatingApi;
    this.createUserRatingApi = createUserRatingApi;
    this.activationsApi = activationsApi;
  }

  public ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers getRedeemedOffers(
      final String fid) throws ApiException {
    ResponseWrapperUserTokenOutputResponse userToken = getUserToken(fid);

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
    ResponseWrapperUserTokenOutputResponse userToken = getUserToken(fid);

    return getStatementCreditActivationDetail(
        GenericOffersCriterion.builder()
            .fid(fid)
            .activationId(activationId)
            .userToken(getToken(userToken))
            .build());
  }

  public ResponseWrapperUserFeedbackOutputResponse sendUserFeedback(UserFeedbackInput userFeedback)
      throws ApiException {
    requireNonNull(userFeedback.getFid(), FID_IS_REQUIRED);
    requireNonNull(userFeedback.getUserToken(), USER_TOKEN_IS_REQUIRED);
    requireNonNull(userFeedback.getOfferId(), OFFER_ID_IS_REQUIRED);
    requireNonNull(userFeedback.getFeedback(), FEEDBACK_IS_REQUIRED);

    return userFeedbackApi.sendUserFeedback(userFeedback);
  }

  public ResponseWrapperUserFeedbackOutputListResponse getUserFeedback(String fid)
      throws ApiException {
    ResponseWrapperUserTokenOutputResponse userToken = getUserToken(fid);

    return getUserFeedback(
        GenericOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public ResponseWrapperMatchedOfferDetailsResponseMatchedOffers getMatchedOffers(final String fid)
      throws ApiException {
    ResponseWrapperUserTokenOutputResponse userToken = getUserToken(fid);

    return getMatchedOffers(
        MatchedOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public ResponseWrapperUserSavingsResponse getUserSavings(String fid) throws ApiException {
    ResponseWrapperUserTokenOutputResponse userToken = getUserToken(fid);

    return getUserSavings(
        GenericOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public ResponseWrapperUserTokenOutputResponse getUserToken(String fid) throws ApiException {
    requireNonNull(fid, FID_IS_REQUIRED);

    return userTokenApi.getUserToken(fid, authInfo, null);
  }

  public ResponseWrapperDetailedOffersResponseDetailedOffers getOfferDetails(
      String fid, String offerId) throws ApiException {
    ResponseWrapperUserTokenOutputResponse userToken = getUserToken(fid);

    return getOfferDetails(
        GenericOffersCriterion.builder()
            .fid(fid)
            .offerId(offerId)
            .userToken(getToken(userToken))
            .build());
  }

  public BrowseOffersResponse getOffers(String fid) throws ApiException {
    ResponseWrapperUserTokenOutputResponse userToken = getUserToken(fid);

    return getOffers(
        GenericOffersCriterion.builder().fid(fid).userToken(getToken(userToken)).build());
  }

  public AdjustmentResponse getAdjustments(String fid, Integer offset, Integer limit,
                                           String startDate, String endDate, String dateFilter) throws ApiException {
    final ResponseWrapperUserTokenOutputResponse userToken = getUserToken(fid);

    return getAdjustments(
        GenericOffersCriterion.builder().fid(fid).offset(offset).limit(limit).
            startDate(startDate).endDate(endDate).dateFilter(dateFilter).userToken(getToken(userToken)).build());
  }

  private AdjustmentResponse getAdjustments(GenericOffersCriterion adjustmentsCriterion)
      throws ApiException {
    requireNonNull(adjustmentsCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(adjustmentsCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    return adjustmentsApi.getAdjustments(
        adjustmentsCriterion.getFid(),
        adjustmentsCriterion.getOffset(),
        adjustmentsCriterion.getLimit(),
        adjustmentsCriterion.getStartDate(),
        adjustmentsCriterion.getEndDate(),
        adjustmentsCriterion.getDateFilter());
  }

  private ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers getRedeemedOffers(
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

  private ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail
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

  private ResponseWrapperUserFeedbackOutputListResponse getUserFeedback(
      GenericOffersCriterion userFeedbackCriterion) throws ApiException {
    requireNonNull(userFeedbackCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(userFeedbackCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    return userFeedbackApi.getUserFeedback(
        userFeedbackCriterion.getFid(),
        userFeedbackCriterion.getUserToken(),
        userFeedbackCriterion.getOfferId(),
        userFeedbackCriterion.getFeedback());
  }

  private ResponseWrapperMatchedOfferDetailsResponseMatchedOffers getMatchedOffers(
      final MatchedOffersCriterion matchedOffersCriterion) throws ApiException {
    requireNonNull(matchedOffersCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(matchedOffersCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    return matchedOffersApi.matchedOffers(
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
  }

  private ResponseWrapperUserSavingsResponse getUserSavings(
      GenericOffersCriterion userSavingsCriterion) throws ApiException {
    requireNonNull(userSavingsCriterion.getFid(), FID_IS_REQUIRED);
    requireNonNull(userSavingsCriterion.getUserToken(), USER_TOKEN_IS_REQUIRED);

    return userSavingsApi.getUserSavings(
        userSavingsCriterion.getFid(), userSavingsCriterion.getUserToken());
  }

  private String getToken(ResponseWrapperUserTokenOutputResponse userToken) {
    return requireNonNull(requireNonNull(userToken.getResponse()).getUserToken()).getToken();
  }

  private ResponseWrapperDetailedOffersResponseDetailedOffers getOfferDetails(
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

  private BrowseOffersResponse getOffers(GenericOffersCriterion offersCriterion)
      throws ApiException {
    requireNonNull(offersCriterion.getFid(), FID_IS_REQUIRED);

    return offersApi.browseOffers(
        offersCriterion.getFid(),
        offersCriterion.getIssuerIca(),
        offersCriterion.getBankProductCode(),
        offersCriterion.getOfferType(),
        offersCriterion.getCategory(),
        offersCriterion.getOfferCountry(),
        offersCriterion.getLanguages(),
        offersCriterion.getOffset(),
        offersCriterion.getLimit(),
        offersCriterion.getSort());
  }

  public AccessTokenResponse getToken(AccessTokenRequest accessToken) throws ApiException {
    requireNonNull(accessToken.getFiId(), FID_IS_REQUIRED);
    requireNonNull(accessToken.getUserId(), USER_ID_IS_REQUIRED);
    requireNonNull(accessToken.getUtcOffset(), UTC_OFFSET_IS_REQUIRED);

    return tokensApi.createAccessToken(accessToken);
  }

  public UserOffersResponse getOffers(String acceptLanguage, String offerType, String category, String offerCountry, Integer offset, Integer limit, String sort, String xAuthToken) throws ApiException {
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);

    return offersApi.getOffers(acceptLanguage, offerType, category, offerCountry, offset, limit, sort, xAuthToken);
  }

  public UserOfferDetailsResponse getOfferDetails(String offerId, String acceptLanguage, String xAuthToken) throws ApiException {
    requireNonNull(offerId, OFFER_ID_IS_REQUIRED);
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);

    return offerApi.getOffer(offerId, acceptLanguage, xAuthToken);
  }

  public OfferRatingsResponse getOfferRatings(String current, Integer offset, Integer limit, String xAuthToken) throws ApiException {
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);
    return userRatingsApi.getOfferRatings(current, offset, limit, xAuthToken);
  }

  public OfferRatingResponse getOfferRating(String offerId, String xAuthToken) throws ApiException {
    requireNonNull(offerId, OFFER_ID_IS_REQUIRED);
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);

    return userRatingApi.getOfferRating(offerId,xAuthToken);
  }

  public OfferRatingResponse createOfferRating(String offerId, OfferRatingRequest offerRatingRequest,  String xAuthToken) throws ApiException {
    requireNonNull(offerId, OFFER_ID_IS_REQUIRED);
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);
    return createUserRatingApi.createOfferRating(offerId,offerRatingRequest,xAuthToken);
  }

  public Activations processActivations(RequestedActivation requestedActivation,  String xAuthToken) throws ApiException {
    requireNonNull(requestedActivation, ACTIVATION_REQUEST_IS_REQUIRED);
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);
    return activationsApi.processActivations(requestedActivation);
  }

  public UserSavingsResponse getUserPresentmentSavings(String xAuthToken) throws ApiException {
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);
    return userSavingsApi.getUserPresentmentSavings(xAuthToken);
  }

  public UserAdjustments getUserPresentmentAdjustments(String startDate, String endDate, String dateFilter, Integer offset, Integer limit,String xAuthToken) throws ApiException {
    requireNonNull(xAuthToken, AUTH_TOKEN_IS_REQUIRED);
    return adjustmentsApi.getUserPresentmentAdjustments(startDate,endDate,dateFilter,offset,limit,xAuthToken);
  }
}

