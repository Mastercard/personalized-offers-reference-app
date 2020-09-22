package com.mastercard.developer.service.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MatchedOffersCriterion {

  String fid;
  String userToken;
  String merchantName;
  String category;
  String offerType;
  Double latitude;
  Double longitude;
  Integer radius;
  Integer pageNumber;
  Integer itemsPerPage;
  String lang;
  String userFeedback;
  String presentmentDate;
}
