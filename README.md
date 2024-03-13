# Personalized Offers Reference Implementation
[![](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](./LICENSE)

## Table of Contents
- [Overview](#overview)
- [Usage](#usage)
  * [Frameworks / Libraries used](#prerequisites)
  * [Requirements](#requirements)
- [Integrating with OpenAPI Generator](#integrating-with-openapi-generator)
  * [OpenAPI Generator Plugin Configuration](#openapi-generator-plugin-configuration)
- [Configuration](#configuration)  
- [Use Cases](#use-cases)
- [Execute the Use-Cases](#execute-the-use-cases)
- [Service Documentation](#service-documentation)
- [API Reference](#api-reference)
- [Support](#support)
- [License](#license)

## Overview <a name="overview"></a>

Personalized Offers analyzes payment card transaction history to present cardholders with customized offers.
This code showcases reference implementation of Personalized Offers APIs. Please see here for more details on the API: 
[Mastercard Developer](https://developer.mastercard.com/product/personalized-offers)

## Usage <a name="usage"></a>
### Frameworks / Libraries used <a name="prerequisites"></a>

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Okhttp](https://square.github.io/okhttp/)
- [Lombok](https://projectlombok.org/)
- [Mockito](https://site.mockito.org/)
- [OpenAPI Generator](https://openapi.tools/)
- [Apache Maven](https://maven.apache.org/)

### Requirements <a name="requirements"></a>
- [Java 11](https://www.oracle.com/java/technologies/downloads/) or above.
- [Maven](https://maven.apache.org/download.cgi)
- [Lombok](https://projectlombok.org/)
  - [Plugin for IntelliJ](https://plugins.jetbrains.com/plugin/6317-lombok/)
  - [Setup for Eclipse](https://projectlombok.org/setup/eclipse)
  - [Setup for Netbeans](https://projectlombok.org/setup/netbeans)
 
 
## Integrating with OpenAPI Generator <a name="integrating-with-openapi-generator"></a>
[OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator) generates API client libraries from 
[OpenAPI Specs](https://github.com/OAI/OpenAPI-Specification). 
It provides generators and library templates for supporting multiple languages and frameworks.

### OpenAPI Generator Plugin Configuration <a name="openapi-generator-plugin-configuration"></a>

```xml
      <plugin>
        <groupId>org.openapitools</groupId>
        <artifactId>openapi-generator-maven-plugin</artifactId>
        <version>5.3.0</version>
        <executions>
          <execution>
            <goals>
              <goal>generate</goal>
            </goals>
            <configuration>
              <inputSpec>${project.basedir}/src/main/resources/reference-service.yaml</inputSpec>
              <generatorName>java</generatorName>
              <apiPackage>com.mastercard.api</apiPackage>
              <modelPackage>com.mastercard.api.model</modelPackage>              
              <generateApiTests>false</generateApiTests>
              <generateModelTests>false</generateModelTests>
              <generateApiTests>false</generateApiTests>
              <generateModelDocumentation>false</generateModelDocumentation>
              <generateApiDocumentation>false</generateApiDocumentation>              
              <skipValidateSpec>true</skipValidateSpec>
              <configOptions>
                <sourceFolder>src/gen/java/main</sourceFolder>
                <supportingFiles>false</supportingFiles>
                <dateLibrary>java8</dateLibrary>              
              </configOptions>
            </configuration>
          </execution>
        </executions>
      </plugin>
```
For more information on how this client generator works please consult the official 
[GitHub repository](https://github.com/OpenAPITools/openapi-generator)

## Configuration <a name="configuration"></a>

1.  Open Mastercard Developers website and create an account at [Mastercard Developer](https://developer.mastercard.com).
2.  Create a new project [here](https://developer.mastercard.com/dashboard).
3.  Add the `Personalized Offers` API to your project and click continue.
4.  Configure project and click continue.
5.  Download Sandbox Signing Key.
6.  A `.p12` file is downloaded automatically. **Note**: On Safari, the file name will be `Unknown`. Rename it to a 
    .p12 extension.
7.  Copy the downloaded `.p12` file to `src/main/resources` folder in the code.
8.  Open `src/main/resources/application.properties` and configure:
      - `mastercard.api.p12.path` - Path to keystore (.p12) file. Since the .p12 is under same resources folder just 
      pass the name of the .p12 here.
      - `mastercard.api.consumer.key` - Consumer key. Copy this from "Sandbox/Production Keys" on your project page
      - `mastercard.api.key.alias` - Key alias. Default key alias for sandbox is `keyalias`.
      - `mastercard.api.keystore.password` - Keystore password. Default keystore password for sandbox project is 
      `keystorepassword`.

## Use Cases <a name="use-cases"></a>

**Legacy User Presentment Use Cases**

The following API use cases are the deprecated version of the current User Presentment APIs that are listed under [User Presentment Use Cases](#user-presentment-use-cases)

1. **Retrieve Redeemed Offers**  
As part of the consumer experience, Issuers or Partners can also provide a list of offers the cardholder has 
redeemed. This allows a cardholder to see their activity and understand which offers they may have used in the past, 
and the benefits they attained from the program.

2. **Activate an Offer**  
 For attribution reasons, merchants want to know if a cardholder has seen an offer and plan to use it. An Issuer or 
Partner can activate an offer (based on a user action such as a click, or a page view) and enable that offer for use.
Not all programs require an offer to be activated. This is determined during program implementation and Mastercard 
will consult with the Issuer/Partner on whether offers require an activation for use.
Issuer or Partner can retrieve extended information about the specified activated Postpaid Credit offer.

3. **User Feedback**  
Issuer or Partner is allowed to provide a thumbs-up or a thumbs-down rating of the specified offer. Offer matches 
that are disliked will be suppressed from the results of future calls to Matched Offers. Issuer or Partner can 
retrieve feedback with offer ids based on the search criteria.

4. **Retrieve Personalized Offers**  
Issuer or Partner can get a list of offers that a cardholder was assigned based on their spend history. The offers 
are returned along with all the associated marketing content, terms and conditions, and logos/images. The Issuer or 
Partner can then take the offer content and integrate it into their app or site to create a seamless, integrated 
consumer experience for their cardholders.
  
5. **Show the Total User Savings**  
Similar to retrieving redeemed offers, an Issuer or Partner can also retrieve the total savings a cardholder has 
earned from the program and the total cash back or points they have accumulated from redeeming offers. Again, this 
is used to highlight the benefits a cardholder received from the program. Redeemed Offers is a line-by-line listing 
of all individual offers that were redeemed whereas User Savings only provide the aggregate sum of the cash back or 
points earned.
  
6. **Retrieve User Token**  
Issuer or Partner can create a user session token for a cardholder. It must be called prior to any other API calls 
for the specified cardholder. The token value does not expire.
  
7. **Retrieve Offer Details**  
Issuer or Partner can retrieve extended information for the requested offer, typically returns a detailed 
information of an offer.
  
More details on the Legacy User Presentment Use Cases can be found [here](https://developer.mastercard.com/pclo-presentment/documentation/legacy-user-presentment/use-cases/).  
  
**Platform Admin Use Cases**  

1. **Retrieve All Offers**  
Issuer or Partner can get a list of all offers regardless of cardholder. Issuer or Partner can retrieve 
extended information for the requested offers.
  
2. **Retrieve All Adjustments**  
The Adjustments/Redemption API provides detailed information on the redemptions that has satisfied specific offer 
criteria in order to be eligible to receive cash back or points offered by merchant in collaboration with the issuer
and Mastercard.
   
More details on the Platform Admin Use Cases can be found [here](https://developer.mastercard.com/pclo-presentment/documentation/use-cases/platform-admin/).    
   
##### User Presentment Use Cases <a name="user-presentment-use-cases"></a>
   
1. **Retrieve Access Token**  
The Access Tokens API responsible for generating access tokens. It must be called prior to any other user centered 
API calls.
  
2. **Retrieve User Offers**  
Retrieves a list of offers that were assigned to the cardholder based on his or her spend history. 
The offers are returned along with all the associated marketing content, terms and conditions, and logos/images.

3. **Activate Offer**  
Activates the given offer and makes it available for redemption to the cardholder.
  
4. **Offer Rating**  
Cardholder can submit a rating for an offer based on his or her preference (for example: like or dislike).
Cardholder can also Retrieve the rating for the offer.
  
5. **Retrieve Offer Ratings**  
Cardholder can retrieve all ratings he or she submitted with the corresponding offer ids.

6. **Retrieve Offer**  
Cardholder can retrieve extended information for the requested offer, typically returns detailed information of an offer.

7. **Retrieve User Adjustments**  
Cardholder can retrieve all adjustments made that conform to the search criteria (if specified).

8. **Retrieve User Savings**  
Provides an overview of the accumulated and potential savings for a cardholder based on the redemptions made and the additional offers available for the cardholder.                

9. **Filter Personalized Offers**
Cardholder can filter the list of offers they were assigned based on a criteria provided. The offers are returned along with all the associated marketing content, terms and conditions, and logos/images.
  
More details on the User Presentment Use Cases can be found [here](https://developer.mastercard.com/pclo-presentment/documentation/use-cases/user-presentment/).      

## Execute the Use-Cases   <a name="execute-the-use-cases"></a>
1. Run `./mvn clean install` from the root of the project directory.
2. There are two ways to execute the use-cases:
    1. Execute the use-cases(test cases):  
        - Go to `src/test/java/com/mastercard/developer/usecases` folder.  
        - Execute each test cases.
        - In `LegacyUserPresentmentServiceTest.java` and `PlatformAdminServiceTest.java`, note that a Financial Institution Identifier (f-id) provided by Mastercard during implementation is required while executing all test cases.
        - In `LegacyUserPresentmentServiceTest.java`, note that a user-token is created using Financial Institution Identifier and AuthInfo (encrypted concatenation of "User ID as specified in enrollment:Financial Institute ID:current Unix time) provided by Mastercard during formal implementation project, the user-token is used while executing all the other test-cases.
        - In `UserPresentmentServiceTest.java`, note that an access-token is created using Financial Institution Identifier, user Id and UTC time offset. The access-token is used while executing all the other test-cases.
        - If you want to change any request parameters, please make change in `Constant.java`from `src/main/java/com/mastercard/developer/constant` folder.
    
    2. Use REST API based Client( such as [Insomnia](https://insomnia.rest/download) or [Postman](https://www.postman.com/downloads/))  
        - Run `./mvn spring-boot:run` command to run the application.  
        - Use any REST API based Client to test the functionality. Below are the endpoints exposed by this reference application, use `locahost:8080` as the Host:  
          - GET <Host>/redeem-offers      
          - POST <Host>/activate-offers  
          - GET <Host>/activation-details  
          - POST <Host>/user-feedbacks      
          - GET <Host>/user-feedbacks  
          - GET <Host>/matched-offers  
          - GET <Host>/user-savings             
          - GET <Host>/user-tokens  
          - GET <Host>/offer-details  
          - GET <Host>/offers  
          - GET <Host>/adjustments  
          - POST <Host>/user-presentment/access-tokens  
          - GET <Host>/user-presentment/offers  
          - GET <Host>/user-presentment/offers/{offer_id}  
          - POST <Host>/user-presentment/offers/filters  
          - GET <Host>/user-presentment/offer-ratings  
          - GET <Host>/user-presentment/offer-ratings/{offer_id}  
          - POST <Host>/user-presentment/offer-ratings/{offer_id}/likes  
          - POST <Host>/user-presentment/activations  
          - GET <Host>/user-presentment/savings  
          - GET <Host>/user-presentment/adjustments
          - GET <Host>/platform-admin/offers
          - POST <Host>/platform-admin/offers/filters
          - GET <Host>/platform-admin/adjustments
        - For more information about above endpoints, please refer `PersonalizedOffersController.java` from `src/main/java/com/mastercard/developer/controller` folder.
    
## Service Documentation <a name="service-documentation"></a>

Personalized Offers service documentation can be found 
[here](https://developer.mastercard.com/product/personalized-offers)    
    
## API Reference <a name="api-reference"></a>

The API Reference can be found [here](https://developer.mastercard.com/pclo-presentment/documentation/api-reference/user-presentment/)

## Support <a name="support"></a>

Please email [pclo_product@mastercard.com]() with any questions or feedback you may have.

## License <a name="license"></a>
Copyright 2024 Mastercard
 
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with 
the License. You may obtain a copy of the License at:
 
       http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on 
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the 
specific language governing permissions and limitations under the License.