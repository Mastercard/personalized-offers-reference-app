package com.mastercard.developer.service;

import com.mastercard.ApiException;
import com.mastercard.api.*;
import com.mastercard.api.model.*;
import com.mastercard.developer.integration.data.PersonalizedOffersData;
import com.mastercard.developer.service.domain.GenericOffersCriterion;
import com.mastercard.developer.service.domain.MatchedOffersCriterion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersonalizedOffersServiceTest {

    @Mock
    private StatementCreditActivationsApi statementCreditActivationsApi;
    @Mock
    private UserFeedbackApi userFeedbackApi;
    @Mock
    private UserSavingsApi userSavingsApi;
    @Mock
    private RedeemedOffersApi redeemedOffersApi;
    @Mock
    private MatchedOffersApi matchedOffersApi;
    @Mock
    private OfferDetailsApi offerDetailsApi;
    @Mock
    private OffersApi offersApi;
    @InjectMocks
    @Spy
    private PersonalizedOffersService personalizedOffersService;

    @Test(expected = Exception.class)
    public void testGetRedeemedOffersForException() throws ApiException {
        personalizedOffersService.getRedeemedOffers(PersonalizedOffersData.FID);
    }

    @Test
    public void testGetRedeemedOffersForNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.doReturn(null).when(personalizedOffersService).getRedeemedOffers(Mockito.any(GenericOffersCriterion.class));
        ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers redeemedOffers = personalizedOffersService.getRedeemedOffers(PersonalizedOffersData.FID);
        Assert.assertNull(redeemedOffers);
    }

    @Test
    public void testGetRedeemedOffersForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.when(redeemedOffersApi.getRedeemedOfferDetails(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(PersonalizedOffersData.redeemedOffers());
        ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers redeemedOffers = personalizedOffersService.getRedeemedOffers(PersonalizedOffersData.FID);
        Assert.assertNotNull(redeemedOffers);
        Assert.assertNotNull(redeemedOffers.getResponse());
        Assert.assertNotNull(redeemedOffers.getResponse().getStatus());
        Assert.assertEquals(PersonalizedOffersData.STATUS_CODE, redeemedOffers.getResponse().getStatus().getCode());
    }

    @Test(expected = Exception.class)
    public void testActivateStatementCreditOfferForException() throws ApiException {
        Mockito.doReturn(new ApiException()).when(statementCreditActivationsApi).activateStatementCreditOffer(Mockito.any());
        personalizedOffersService.activateStatementCreditOffer(new ActivateSCOfferInputStatementCreditOfferActivation());
    }

    @Test
    public void testActivateStatementCreditOfferForNull() throws ApiException {
        Mockito.doReturn(null).when(statementCreditActivationsApi).activateStatementCreditOffer(Mockito.any());
        ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation response = personalizedOffersService.activateStatementCreditOffer(new ActivateSCOfferInputStatementCreditOfferActivation());
        Assert.assertNull(response);
    }

    @Test
    public void testActivateStatementCreditOfferForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.activatedOffer()).when(statementCreditActivationsApi).activateStatementCreditOffer(Mockito.any());
        ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation response = personalizedOffersService.activateStatementCreditOffer(new ActivateSCOfferInputStatementCreditOfferActivation());
        Assert.assertNotNull(response);
        Assert.assertEquals(PersonalizedOffersData.activatedOffer(), response);
    }

    @Test(expected = Exception.class)
    public void testGetStatementCreditActivationDetailForException() throws ApiException {
        personalizedOffersService.getStatementCreditActivationDetail(PersonalizedOffersData.FID, PersonalizedOffersData.ACTIVATION_ID);
    }

    @Test
    public void testGetStatementCreditActivationDetailForNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail redeemedOffers = personalizedOffersService.getStatementCreditActivationDetail(PersonalizedOffersData.FID, PersonalizedOffersData.ACTIVATION_ID);
        Assert.assertNull(redeemedOffers);
    }

    @Test
    public void testGetStatementCreditActivationDetailForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.doReturn(PersonalizedOffersData.activationDetails()).when(personalizedOffersService).getStatementCreditActivationDetail(Mockito.any());
        ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail redeemedOffers = personalizedOffersService.getStatementCreditActivationDetail(PersonalizedOffersData.FID, PersonalizedOffersData.ACTIVATION_ID);
        Assert.assertNotNull(redeemedOffers);
        Assert.assertEquals(PersonalizedOffersData.activationDetails(), redeemedOffers);
    }

    @Test(expected = Exception.class)
    public void testSendUserFeedbackForException() throws ApiException {
        personalizedOffersService.sendUserFeedback(new UserFeedbackInput());
    }

    @Test
    public void testSendUserFeedbackForNull() throws ApiException {
        UserFeedbackInput input = new UserFeedbackInput();
        input.setFid(PersonalizedOffersData.FID);
        input.setUserToken(PersonalizedOffersData.USER_TOKEN);
        input.setOfferId(PersonalizedOffersData.OFFER_ID);
        input.setFeedback(PersonalizedOffersData.FEEDBACK);
        Mockito.doReturn(null).when(userFeedbackApi).sendUserFeedback(Mockito.any());
        ResponseWrapperUserFeedbackOutputWrapper wrapper = personalizedOffersService.sendUserFeedback(input);
        Assert.assertNull(wrapper);
    }

    @Test
    public void testSendUserFeedbackForNotNull() throws ApiException {
        UserFeedbackInput input = new UserFeedbackInput();
        input.setFid(PersonalizedOffersData.FID);
        input.setUserToken(PersonalizedOffersData.USER_TOKEN);
        input.setOfferId(PersonalizedOffersData.OFFER_ID);
        input.setFeedback(PersonalizedOffersData.FEEDBACK);
        Mockito.doReturn(PersonalizedOffersData.sendUserFeedback()).when(userFeedbackApi).sendUserFeedback(Mockito.any());
        ResponseWrapperUserFeedbackOutputWrapper wrapper = personalizedOffersService.sendUserFeedback(input);
        Assert.assertNotNull(wrapper);
        Assert.assertEquals(PersonalizedOffersData.sendUserFeedback(), wrapper);
    }

    @Test(expected = Exception.class)
    public void testGetUserFeedbackForException() throws ApiException {
        personalizedOffersService.getUserFeedback(PersonalizedOffersData.FID);
    }

    @Test
    public void testGetUserFeedbackForNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        ResponseWrapperUserFeedbackOutputList wrapper = personalizedOffersService.getUserFeedback(PersonalizedOffersData.FID);
        Assert.assertNull(wrapper);
    }

    @Test
    public void testGetUserFeedbackForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserFeedback()).when(personalizedOffersService).getUserFeedback(Mockito.anyString());
        ResponseWrapperUserFeedbackOutputList wrapper = personalizedOffersService.getUserFeedback(PersonalizedOffersData.FID);
        Assert.assertNotNull(wrapper);
        Assert.assertEquals(PersonalizedOffersData.getUserFeedback(), wrapper);
    }

    @Test(expected = Exception.class)
    public void testGetMatchedOffersForException() throws ApiException {
        personalizedOffersService.getMatchedOffers(PersonalizedOffersData.FID);
    }

    @Test
    public void testGetMatchedOffersForNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.doReturn(null).when(personalizedOffersService).getMatchedOffers(Mockito.any(MatchedOffersCriterion.class));
        ResponseWrapperMatchedOfferDetailsResponseMatchedOffers matchedOffers = personalizedOffersService.getMatchedOffers(PersonalizedOffersData.FID);
        Assert.assertNull(matchedOffers);
    }

    @Test
    public void testGetMatchedOffersForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.doReturn(PersonalizedOffersData.getMatchedOffers()).when(matchedOffersApi).matchedOffers(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        ResponseWrapperMatchedOfferDetailsResponseMatchedOffers matchedOffers = personalizedOffersService.getMatchedOffers(PersonalizedOffersData.FID);
        Assert.assertNotNull(matchedOffers);
        Assert.assertEquals(PersonalizedOffersData.getMatchedOffers(), matchedOffers);
    }

    @Test(expected = Exception.class)
    public void testGetUserSavingsForException() throws ApiException {
        personalizedOffersService.getUserSavings(PersonalizedOffersData.FID);
    }

    @Test
    public void testGetUserSavingsForNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.doReturn(null).when(personalizedOffersService).getUserSavings(Mockito.any(GenericOffersCriterion.class));
        ResponseWrapperUserSavingsOutputWrapper outputWrapper = personalizedOffersService.getUserSavings(PersonalizedOffersData.FID);
        Assert.assertNull(outputWrapper);
    }

    @Test
    public void testGetUserSavingsForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.doReturn(PersonalizedOffersData.getUserSavings()).when(userSavingsApi).getUserSavings(Mockito.any(), Mockito.any());
        ResponseWrapperUserSavingsOutputWrapper outputWrapper = personalizedOffersService.getUserSavings(PersonalizedOffersData.FID);
        Assert.assertNotNull(outputWrapper);
        Assert.assertEquals(PersonalizedOffersData.getUserSavings(), outputWrapper);
    }

    @Test(expected = Exception.class)
    public void testGetOfferDetailsForException() throws ApiException {
        personalizedOffersService.getOfferDetails(PersonalizedOffersData.FID, PersonalizedOffersData.OFFER_ID);
    }

    @Test
    public void testGetOfferDetailsForNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.doReturn(null).when(personalizedOffersService).getOfferDetails(Mockito.any(GenericOffersCriterion.class));
        ResponseWrapperDetailedOffersResponseDetailedOffers detailedOffers = personalizedOffersService.getOfferDetails(PersonalizedOffersData.FID, PersonalizedOffersData.OFFER_ID);
        Assert.assertNull(detailedOffers);
    }

    @Test
    public void testGetOfferDetailsForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.doReturn(PersonalizedOffersData.getOfferDetails()).when(offerDetailsApi).getOfferDetails(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        ResponseWrapperDetailedOffersResponseDetailedOffers detailedOffers = personalizedOffersService.getOfferDetails(PersonalizedOffersData.FID, PersonalizedOffersData.OFFER_ID);
        Assert.assertNotNull(detailedOffers);
        Assert.assertEquals(PersonalizedOffersData.getOfferDetails(), detailedOffers);
    }

    @Test(expected = Exception.class)
    public void testGetOffersForException() throws ApiException {
        personalizedOffersService.getOffers(PersonalizedOffersData.FID);
    }

    @Test
    public void testGetOffersForNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.doReturn(null).when(personalizedOffersService).getOffers(Mockito.any(GenericOffersCriterion.class));
        BrowseOffers browseOffers = personalizedOffersService.getOffers(PersonalizedOffersData.FID);
        Assert.assertNull(browseOffers);
    }

    @Test
    public void testGetOffersForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserToken()).when(personalizedOffersService).getUserToken(Mockito.anyString());
        Mockito.doReturn(PersonalizedOffersData.getOffers()).when(offersApi).browseOffers(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        BrowseOffers browseOffers = personalizedOffersService.getOffers(PersonalizedOffersData.FID);
        Assert.assertNotNull(browseOffers);
        Assert.assertEquals(PersonalizedOffersData.getOffers(), browseOffers);
    }

    @Test(expected = Exception.class)
    public void testGetAdjustmentsForException() throws ApiException {
        personalizedOffersService.getAdjustments(PersonalizedOffersData.FID, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.START_DATE, PersonalizedOffersData.END_DATE, PersonalizedOffersData.DATE_FILTER);
    }

    @Test
    public void testGetAdjustmentsForNull() throws ApiException {
        Mockito.doReturn(null).when(personalizedOffersService).getAdjustments(Mockito.any());
        UserAdjustment userAdjustment = personalizedOffersService.getAdjustments(PersonalizedOffersData.FID, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.START_DATE, PersonalizedOffersData.END_DATE, PersonalizedOffersData.DATE_FILTER);
        Assert.assertNull(userAdjustment);
    }

    @Test
    public void testGetAdjustmentsForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getAdjustments()).when(personalizedOffersService).getAdjustments(Mockito.any());
        UserAdjustment userAdjustment = personalizedOffersService.getAdjustments(PersonalizedOffersData.FID, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.START_DATE, PersonalizedOffersData.END_DATE, PersonalizedOffersData.DATE_FILTER);
        Assert.assertNotNull(userAdjustment);
        Assert.assertEquals(PersonalizedOffersData.getAdjustments(), userAdjustment);
    }

    @Test(expected = Exception.class)
    public void testGetTokenForException() throws ApiException {
        personalizedOffersService.getToken(PersonalizedOffersData.getAccessTokenRequest());
    }

    @Test
    public void testGetTokenForNull() throws ApiException {
        Mockito.doReturn(null).when(personalizedOffersService).getToken(Mockito.any());
        UserAccessToken userAccessToken = personalizedOffersService.getToken(PersonalizedOffersData.getAccessTokenRequest());
        Assert.assertNull(userAccessToken);
    }

    @Test
    public void testGetTokenFornotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getAccessToken()).when(personalizedOffersService).getToken(Mockito.any());
        UserAccessToken userAccessToken = personalizedOffersService.getToken(PersonalizedOffersData.getAccessTokenRequest());
        Assert.assertNotNull(userAccessToken);
        Assert.assertEquals(PersonalizedOffersData.getAccessToken(), userAccessToken);
    }

    @Test(expected = Exception.class)
    public void testGetUserPresentmentOffersForException() throws ApiException {
        personalizedOffersService.getOffers(PersonalizedOffersData.FID, PersonalizedOffersData.OFFER_TYPE, PersonalizedOffersData.OFFER_CATEGORY, PersonalizedOffersData.OFFER_COUNTRY, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.ACCESS_TOKEN);
    }

    @Test
    public void testGetUserPresentmentOffersForNull() throws ApiException {
        Mockito.doReturn(null).when(personalizedOffersService).getOffers(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
        UserOffers userOffers = personalizedOffersService.getOffers(PersonalizedOffersData.FID, PersonalizedOffersData.OFFER_TYPE, PersonalizedOffersData.OFFER_CATEGORY, PersonalizedOffersData.OFFER_COUNTRY, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNull(userOffers);
    }

    @Test
    public void testGetUserPresentmentOffersForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserPresentmentOffers()).when(personalizedOffersService).getOffers(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
        UserOffers userOffers = personalizedOffersService.getOffers(PersonalizedOffersData.FID, PersonalizedOffersData.OFFER_TYPE, PersonalizedOffersData.OFFER_CATEGORY, PersonalizedOffersData.OFFER_COUNTRY, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNotNull(userOffers);
        Assert.assertEquals(PersonalizedOffersData.getUserPresentmentOffers(), userOffers);
    }

    @Test(expected = Exception.class)
    public void testGetUserOfferDetailsForException() throws ApiException {
        personalizedOffersService.getOfferDetails(PersonalizedOffersData.OFFER_ID, PersonalizedOffersData.ACCESS_TOKEN, PersonalizedOffersData.LANGUAGE);
    }

    @Test
    public void testGetUserOfferDetailsForNull() throws ApiException {
        Mockito.doReturn(null).when(personalizedOffersService).getOfferDetails(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        OfferDetails offerDetails = personalizedOffersService.getOfferDetails(PersonalizedOffersData.OFFER_ID, PersonalizedOffersData.ACCESS_TOKEN, PersonalizedOffersData.LANGUAGE);
        Assert.assertNull(offerDetails);
    }

    @Test
    public void testGetUserOfferDetailsForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserOfferDetails()).when(personalizedOffersService).getOfferDetails(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        OfferDetails offerDetails = personalizedOffersService.getOfferDetails(PersonalizedOffersData.OFFER_ID, PersonalizedOffersData.ACCESS_TOKEN, PersonalizedOffersData.LANGUAGE);
        Assert.assertNotNull(offerDetails);
        Assert.assertEquals(PersonalizedOffersData.getUserOfferDetails(), offerDetails);
    }

    @Test(expected = Exception.class)
    public void testGetOfferRatingsForException() throws ApiException {
        personalizedOffersService.getOfferRatings(PersonalizedOffersData.CURRENCY_CODE, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.ACCESS_TOKEN);
    }

    @Test
    public void testGetOfferRatingsForNull() throws ApiException {
        Mockito.doReturn(null).when(personalizedOffersService).getOfferRatings(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
        UserOfferRatings userOfferRatings = personalizedOffersService.getOfferRatings(PersonalizedOffersData.CURRENCY_CODE, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNull(userOfferRatings);
    }

    @Test
    public void testGetOfferRatingsForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserOfferRatings()).when(personalizedOffersService).getOfferRatings(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
        UserOfferRatings userOfferRatings = personalizedOffersService.getOfferRatings(PersonalizedOffersData.CURRENCY_CODE, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNotNull(userOfferRatings);
        Assert.assertEquals(PersonalizedOffersData.getUserOfferRatings(), userOfferRatings);
    }

    @Test(expected = Exception.class)
    public void testGetOfferRatingForException() throws ApiException {
        personalizedOffersService.getOfferRating(PersonalizedOffersData.OFFER_ID, PersonalizedOffersData.ACCESS_TOKEN);
    }

    @Test
    public void testGetOfferRatingForNull() throws ApiException {
        Mockito.doReturn(null).when(personalizedOffersService).getOfferRating(Mockito.anyString(), Mockito.anyString());
        UserOfferRating userOfferRating = personalizedOffersService.getOfferRating(PersonalizedOffersData.OFFER_ID, PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNull(userOfferRating);
    }

    @Test
    public void testGetOfferRatingForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserOfferRating()).when(personalizedOffersService).getOfferRating(Mockito.anyString(), Mockito.anyString());
        UserOfferRating userOfferRating = personalizedOffersService.getOfferRating(PersonalizedOffersData.OFFER_ID, PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNotNull(userOfferRating);
        Assert.assertEquals(PersonalizedOffersData.getUserOfferRating(), userOfferRating);
    }

    @Test(expected = Exception.class)
    public void testCreateOfferRatingForException() throws ApiException {
        personalizedOffersService.createOfferRating(PersonalizedOffersData.OFFER_ID, PersonalizedOffersData.getOfferRatingRequest(), PersonalizedOffersData.ACCESS_TOKEN);
    }

    @Test
    public void testCreateOfferRatingForNull() throws ApiException {
        Mockito.doReturn(null).when(personalizedOffersService).createOfferRating(Mockito.anyString(), Mockito.any(), Mockito.anyString());
        UserOfferRating userOfferRating = personalizedOffersService.createOfferRating(PersonalizedOffersData.OFFER_ID, PersonalizedOffersData.getOfferRatingRequest(), PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNull(userOfferRating);
    }

    @Test
    public void testCreateOfferRatingForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserOfferRating()).when(personalizedOffersService).createOfferRating(Mockito.anyString(), Mockito.any(), Mockito.anyString());
        UserOfferRating userOfferRating = personalizedOffersService.createOfferRating(PersonalizedOffersData.OFFER_ID, PersonalizedOffersData.getOfferRatingRequest(), PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNotNull(userOfferRating);
        Assert.assertEquals(PersonalizedOffersData.getUserOfferRating(), userOfferRating);
    }

    @Test(expected = Exception.class)
    public void testProcessActivationsForException() throws ApiException {
        personalizedOffersService.processActivations(PersonalizedOffersData.getRequestedActivation(), PersonalizedOffersData.ACCESS_TOKEN);
    }

    @Test
    public void testProcessActivationsForNull() throws ApiException {
        Mockito.doReturn(null).when(personalizedOffersService).processActivations(Mockito.any(), Mockito.anyString());
        Activations activations = personalizedOffersService.processActivations(PersonalizedOffersData.getRequestedActivation(), PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNull(activations);
    }

    @Test
    public void testProcessActivationsForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getActivations()).when(personalizedOffersService).processActivations(Mockito.any(), Mockito.anyString());
        Activations activations = personalizedOffersService.processActivations(PersonalizedOffersData.getRequestedActivation(), PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNotNull(activations);
        Assert.assertEquals(PersonalizedOffersData.getActivations(), activations);
    }

    @Test(expected = Exception.class)
    public void testGetUserPresentmentSavingsForException() throws ApiException {
        personalizedOffersService.getUserPresentmentSavings(null);
    }

    @Test
    public void testGetUserPresentmentSavingsForNull() throws ApiException {
        Mockito.doReturn(null).when(userSavingsApi).getUserPresentmentSavings(Mockito.anyString());
        UserSavings userSavings = personalizedOffersService.getUserPresentmentSavings(PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNull(userSavings);
    }

    @Test
    public void testGetUserPresentmentSavingsForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserPresentmentSavings()).when(personalizedOffersService).getUserPresentmentSavings(Mockito.anyString());
        UserSavings userSavings = personalizedOffersService.getUserPresentmentSavings(PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNotNull(userSavings);
        Assert.assertEquals(PersonalizedOffersData.getUserPresentmentSavings(), userSavings);
    }

    @Test(expected = Exception.class)
    public void testGetUserPresentmentAdjustmentsForException() throws ApiException {
        personalizedOffersService.getUserPresentmentAdjustments(PersonalizedOffersData.START_DATE, PersonalizedOffersData.END_DATE, PersonalizedOffersData.DATE_FILTER, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.ACCESS_TOKEN);
    }

    @Test
    public void testGetUserPresentmentAdjustmentsForNull() throws ApiException {
        Mockito.doReturn(null).when(personalizedOffersService).getUserPresentmentAdjustments(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
        UserAdjustments userAdjustments = personalizedOffersService.getUserPresentmentAdjustments(PersonalizedOffersData.START_DATE, PersonalizedOffersData.END_DATE, PersonalizedOffersData.DATE_FILTER, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNull(userAdjustments);
    }

    @Test
    public void testGetUserPresentmentAdjustmentsForNotNull() throws ApiException {
        Mockito.doReturn(PersonalizedOffersData.getUserAdjustments()).when(personalizedOffersService).getUserPresentmentAdjustments(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
        UserAdjustments userAdjustments = personalizedOffersService.getUserPresentmentAdjustments(PersonalizedOffersData.START_DATE, PersonalizedOffersData.END_DATE, PersonalizedOffersData.DATE_FILTER, PersonalizedOffersData.OFFSET, PersonalizedOffersData.LIMIT, PersonalizedOffersData.ACCESS_TOKEN);
        Assert.assertNotNull(userAdjustments);
        Assert.assertEquals(PersonalizedOffersData.getUserAdjustments(), userAdjustments);
    }
}
