package com.mastercard.developer.usecases;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mastercard.ApiException;
import com.mastercard.api.model.BrowseOffers;
import com.mastercard.api.model.UserAdjustment;
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

  /** Use case 1. Retrieve All Offers */
  @Test
  @DisplayName("Retrieve all offers")
  void getOffers() {

    GenericOffersCriterion genericOffersCriterion =
        GenericOffersCriterion.builder()
            .fid(fId)
            .issuerIca(ica)
            .bankProductCode(bankProductCode)
            .offerType("POSTPAIDCREDIT")
            .category("DEPARTMENTSTORE")
            .offerCountry("USA")
            .lang("en_US")
            .offset(0)
            .limit(10)
            .build();

    try {
      BrowseOffers offers = personalizedOffersService.getOffers(genericOffersCriterion);

      assertNotNull(offers);

      LOGGER.info("Retrieve all the Financial Institute offers : {}", offers);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }

  /** Use case 2. Retrieve All Adjustments */
  @Test
  @DisplayName("Retrieve all adjustments")
  void getAdjustments() {

    GenericOffersCriterion genericOffersCriterion =
        GenericOffersCriterion.builder()
            .fid(fId)
            .offset(0)
            .limit(10)
            .startDate("2019-10-05")
            .endDate("2020-10-05")
            .dateFilter("CREATED")
            .build();

    try {
      UserAdjustment adjustment = personalizedOffersService.getAdjustments(genericOffersCriterion);

      assertNotNull(adjustment);

      LOGGER.info("Retrieve all the Financial Institute adjustments : {}", adjustment);
    } catch (ApiException e) {
      LOGGER.info(API_CALL_FAILED_WITH_ERROR_MSG, e.getMessage());
      Assertions.fail(e.getMessage());
    }
  }
}
