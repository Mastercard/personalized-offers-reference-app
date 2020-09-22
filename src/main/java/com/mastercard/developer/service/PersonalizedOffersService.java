package com.mastercard.developer.service;

import com.mastercard.ApiException;
import com.mastercard.api.BulkActivationsApi;
import com.mastercard.api.AdjustmentsApi;
import com.mastercard.api.MatchedOffersApi;
import com.mastercard.api.OfferDetailsApi;
import com.mastercard.api.OffersApi;
import com.mastercard.api.RedeemedOffersApi;
import com.mastercard.api.StatementCreditActivationsApi;
import com.mastercard.api.UserFeedbackApi;
import com.mastercard.api.UserSavingsApi;
import com.mastercard.api.UserTokenApi;
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
import com.mastercard.developer.service.domain.GenericOffersCriterion;
import com.mastercard.developer.service.domain.MatchedOffersCriterion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

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
  private final OffersApi offersApi;
  private final BulkActivationsApi bulkActivationsApi;
  private final AdjustmentsApi adjustmentsApi;

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
      final BulkActivationsApi bulkActivationsApi,
      final AdjustmentsApi adjustmentsApi) {
    this.authInfo = authInfo;
    this.userTokenApi = userTokenApi;
    this.matchedOffersApi = matchedOffersApi;
    this.offerDetailsApi = offerDetailsApi;
    this.statementCreditActivationsApi = statementCreditActivationsApi;
    this.redeemedOffersApi = redeemedOffersApi;
    this.userFeedbackApi = userFeedbackApi;
    this.userSavingsApi = userSavingsApi;
    this.offersApi = offersApi;
    this.bulkActivationsApi = bulkActivationsApi;
    this.adjustmentsApi = adjustmentsApi;
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

  public BulkActivationRequestResponse sendBulkActivations(BulkActivationApiRequest bulkActivation)
      throws ApiException {
    return bulkActivationsApi.processBulkActivationRequest(bulkActivation);
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
}
