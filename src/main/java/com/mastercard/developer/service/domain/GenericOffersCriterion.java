package com.mastercard.developer.service.domain;

import lombok.Builder;
import lombok.Data;

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
}
