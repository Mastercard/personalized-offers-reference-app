package com.mastercard.developer.usecases;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mastercard.ApiException;
import com.mastercard.api.model.BrowseOffers;
import com.mastercard.api.model.UserAdjustment;
import com.mastercard.developer.constant.Constant;
import com.mastercard.developer.integration.data.PersonalizedOffersData;
import com.mastercard.developer.service.PersonalizedOffersService;
import com.mastercard.developer.service.domain.GenericOffersCriterion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlatformAdminServiceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(PlatformAdminServiceTest.class);
  private static final String API_CALL_FAILED_WITH_ERROR_MSG =
      "The API call failed with error msg : {}";

  @Autowired private PersonalizedOffersService personalizedOffersService;

  @Value("${test.data.f-id}")
  private String fId;

  @Value("${test.data.ica}")
  private Integer ica;

  @Value("${test.data.bank-product-code}")
  private String bankProductCode;

  /** Use cases 1 and 2. Retrieve/filter All Offers */
  @Test
  @DisplayName("Retrieve/Filter all offers")
  void getOffers() {

    GenericOffersCriterion genericOffersCriterion =
        GenericOffersCriterion.builder()
            .fid(fId)
            .issuerIca(ica)
            .bankProductCode(bankProductCode)
            .offerType(Constant.Offers.OFFER_TYPE_POSTPAIDCREDIT)
            .category(Constant.Offers.CATEGORY_DEPARTMENTSTORE)
            .offerCountry(Constant.Offers.COUNTRY_USA)
            .lang(Constant.Offers.EN_US)
            .offset(Constant.OFFSET)
            .limit(Constant.LIMIT_TEN)
            .build();

    try {
      BrowseOffers offers =
          personalizedOffersService.getOffers(
              genericOffersCriterion, PersonalizedOffersData.CLIENT_ID);

      assertNotNull(offers);

      LOGGER.info("Retrieve all the Financial Institute offers : {}", offers);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 3. Retrieve All Adjustments */
  @Test
  @DisplayName("Retrieve all adjustments")
  void getAdjustments() {

    GenericOffersCriterion genericOffersCriterion =
        GenericOffersCriterion.builder()
            .fid(fId)
            .offset(Constant.OFFSET)
            .limit(Constant.LIMIT_TEN)
            .startDate(Constant.UserAdjustments.START_DATE)
            .endDate(Constant.UserAdjustments.END_DATE)
            .dateFilter(Constant.UserAdjustments.DATE_FILTER)
            .build();

    try {
      UserAdjustment adjustment =
          personalizedOffersService.getAdjustments(
              genericOffersCriterion, PersonalizedOffersData.CLIENT_ID);

      assertNotNull(adjustment);

      LOGGER.info("Retrieve all the Financial Institute adjustments : {}", adjustment);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }
}
