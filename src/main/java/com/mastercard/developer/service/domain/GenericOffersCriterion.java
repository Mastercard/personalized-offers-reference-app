package com.mastercard.developer.service.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GenericOffersCriterion {

  String fid;
  String userToken;
  String offerId;
  String feedback;
  String activationId;
  Integer itemsPerPage;
  Integer pageNumber;
  String lang;
  Integer issuerIca;
  String bankProductCode;
  String offerType;
  String category;
  String offerCountry;
  List<String> languages;
  Integer offset;
  Integer limit;
  String sort;
  String startDate;
  String endDate;
  String dateFilter;
}
