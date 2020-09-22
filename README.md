# Personalized Offers Reference Implementation
[![](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](./LICENSE)

## Table of Contents
- [Overview](#overview)
- [Usage](#usage)
  * [Frameworks / Libraries used](#prerequisites)
  * [Requirements](#requirements)
  * [Configuration](#configuration)
- [Service Documentation](#service-documentation)
- [Integrating with OpenAPI Generator](#integrating-with-openapi-generator)
  * [OpenAPI Generator Plugin Configuration](#openapi-generator-plugin-configuration)
- [Use Cases](#use-cases)
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
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or above.
- [Lombok](https://projectlombok.org/)
  - [Plugin for IntelliJ](https://plugins.jetbrains.com/plugin/6317-lombok/)
  - [Setup for Eclipse](https://projectlombok.org/setup/eclipse)
  - [Setup for Netbeans](https://projectlombok.org/setup/netbeans)

### Configuration <a name="configuration"></a>
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
9.  Run `./mvnw clean install` from the root of the project directory.
10. Run `./mvnw spring-boot:run` to start the project.
 
## Service Documentation <a name="service-documentation"></a>

Personalized Offers service documentation can be found 
[here](https://developer.mastercard.com/documentation/personalized-offers/)

## Integrating with OpenAPI Generator <a name="integrating-with-openapi-generator"></a>
[OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator) generates API client libraries from 
[OpenAPI Specs](https://github.com/OAI/OpenAPI-Specification). 
It provides generators and library templates for supporting multiple languages and frameworks.

### OpenAPI Generator Plugin Configuration <a name="openapi-generator-plugin-configuration"></a>

```xml
      <plugin>
        <groupId>org.openapitools</groupId>
        <artifactId>openapi-generator-maven-plugin</artifactId>
        <version>4.3.1</version>
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
                <dateLibrary>java8</dateLibrary>              </configOptions>
            </configuration>
          </execution>
        </executions>
      </plugin>
```
For more information on how this client generator works please consult the official 
[Github repository](https://github.com/OpenAPITools/openapi-generator)

## Use Cases <a name="use-cases"></a>

> Case 1: [Retrieve Redeemed Offers]()
  - As part of the consumer experience, Issuers or Partners can also provide a list of offers the cardholder has 
  redeemed. This allows a cardholder to see their activity and understand which offers they may have used in the past, 
  and the benefits they attained from the program.

> Case 2: [Activate an Offer]()
  - For attribution reasons, merchants want to know if a cardholder has seen an offer and plan to use it. An Issuer or 
  Partner can activate an offer (based on a user action such as a click, or a page view) and enable that offer for use.
  Not all programs require an offer to be activated. This is determined during program implementation and Mastercard 
  will consult with the Issuer/Partner on whether offers require an activation for use.
  - Issuer or Partner can retrieve extended information about the specified activated Postpaid Credit offer.

> Case 3: [User Feedback]()
  - Issuer or Partner is allowed to provide a thumbs-up or a thumbs-down rating of the specified offer. Offer matches 
  that are disliked will be suppressed from the results of future calls to Matched Offers. Issuer or Partner can 
  retrieve feedback with offer ids based on the search criteria.

> Case 4: [Retrieve Personalized Offers]()
  - Issuer or Partner can get a list of offers that a cardholder was assigned based on their spend history. The offers 
  are returned along with all the associated marketing content, terms and conditions, and logos/images. The Issuer or 
  Partner can then take the offer content and integrate it into their app or site to create a seamless, integrated 
  consumer experience for their cardholders.
  
> Case 5: [Show the Total User Savings]()
  - Similar to retrieving redeemed offers, an Issuer or Partner can also retrieve the total savings a cardholder has 
  earned from the program and the total cash back or points they have accumulated from redeeming offers. Again, this 
  is used to highlight the benefits a cardholder received from the program. Redeemed Offers is a line-by-line listing 
  of all individual offers that were redeemed whereas User Savings only provide the aggregate sum of the cash back or 
  points earned.
  
> Case 6: [Retrieve User Token]()
  - Issuer or Partner can create a user session token for a cardholder. It must be called prior to any other API calls 
  for the specified cardholder. The token value does not expire.
  
> Case 7: [Retrieve Offer Details]()
  - Issuer or Partner can retrieve extended information for the requested offer, typically returns a detailed 
  information of an offer.
  
> Case 8: [Retrieve All Offers]()
  - Issuer or Partner can get a list of all offers regardless of cardholder. Issuer or Partner can retrieve 
  extended information for the requested offers.
  
> Case 9: [Bulk Activations]()
  - Issuer or Partner can create a request to activate *Always On* offers and enable those offers for use.
  
> Case 10: [Adjustments]()  
  - The Adjustments/Redemption API provides detailed information on the redemptions that has satisfied specific offer 
  criteria in order to be eligible to receive cash back or points offered by merchant in collaboration with the issuer
   and Mastercard.
    
## API Reference <a name="api-reference"></a>

The API Reference can be found [here](https://developer.mastercard.com/documentation/personalized-offers#api-reference)

## Support <a name="support"></a>

Please send an email to [pclo_product@mastercard.com]() with any questions or feedback you may have.

## License <a name="license"></a>
Copyright 2020 Mastercard
 
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with 
the License. You may obtain a copy of the License at:
 
       http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on 
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the 
specific language governing permissions and limitations under the License.