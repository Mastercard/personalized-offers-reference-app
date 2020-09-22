package com.mastercard.developer.configuration;

import com.mastercard.ApiClient;
import com.mastercard.api.AdjustmentsApi;
import com.mastercard.api.BulkActivationsApi;
import com.mastercard.api.MatchedOffersApi;
import com.mastercard.api.OfferDetailsApi;
import com.mastercard.api.OffersApi;
import com.mastercard.api.RedeemedOffersApi;
import com.mastercard.api.StatementCreditActivationsApi;
import com.mastercard.api.UserFeedbackApi;
import com.mastercard.api.UserSavingsApi;
import com.mastercard.api.UserTokenApi;
import com.mastercard.developer.interceptors.OkHttpOAuth1Interceptor;
import com.mastercard.developer.utils.AuthenticationUtils;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static java.util.Objects.requireNonNull;

@Configuration
public class ApiClientConfiguration {

  @Bean
  public ApiClient apiClient(
      final OkHttpClient okHttpClient,
      @Value("${mastercard.api.basePath}" + "/plo") final String basePath) {
    return new ApiClient().setHttpClient(okHttpClient).setBasePath(basePath);
  }

  @Bean
  public UserTokenApi userTokenApi(final ApiClient apiClient) {
    return new UserTokenApi(apiClient);
  }

  @Bean
  public MatchedOffersApi matchedOffersApi(final ApiClient apiClient) {
    return new MatchedOffersApi(apiClient);
  }

  @Bean
  public OfferDetailsApi offerDetailsApi(final ApiClient apiClient) {
    return new OfferDetailsApi(apiClient);
  }

  @Bean
  public RedeemedOffersApi redeemedOffersApi(final ApiClient apiClient) {
    return new RedeemedOffersApi(apiClient);
  }

  @Bean
  public StatementCreditActivationsApi statementCreditActivationsApi(final ApiClient apiClient) {
    return new StatementCreditActivationsApi(apiClient);
  }

  @Bean
  public UserFeedbackApi userFeedbackApi(final ApiClient apiClient) {
    return new UserFeedbackApi(apiClient);
  }

  @Bean
  public UserSavingsApi userSavingsApi(final ApiClient apiClient) {
    return new UserSavingsApi(apiClient);
  }

  @Bean
  public AdjustmentsApi adjustmentApi(final ApiClient apiClient) {
    return new AdjustmentsApi(apiClient);
  }

  @Bean
  public OffersApi offersApi(final ApiClient apiClient) {
    return new OffersApi(apiClient);
  }

  @Bean
  public BulkActivationsApi bulkActivationsApi(final ApiClient apiClient) {
    return new BulkActivationsApi(apiClient);
  }

  @Bean
  public OkHttpClient getHttpClient(
      @Value("${mastercard.api.consumer.key}") final String consumerKey,
      @Value("${mastercard.api.key.alias}") final String signingKeyAlias,
      @Value("${mastercard.api.keystore.password}") final String signingKeyPassword,
      @Value("${mastercard.api.p12.path}") final String signingKeyPkcs12Path) {
    return new OkHttpClient.Builder()
        .addInterceptor(
            new OkHttpOAuth1Interceptor(
                consumerKey,
                getSigningKey(signingKeyPkcs12Path, signingKeyAlias, signingKeyPassword)))
        .build();
  }

  private PrivateKey getSigningKey(
      final String signingKeyPkcs12FileName,
      final String signingKeyAlias,
      final String signingKeyPassword) {
    final ClassLoader classLoader = getClass().getClassLoader();
    final File signingKeyPkcs12File =
        new File(requireNonNull(classLoader.getResource(signingKeyPkcs12FileName)).getFile());
    try {
      return AuthenticationUtils.loadSigningKey(
          signingKeyPkcs12File.getPath(), signingKeyAlias, signingKeyPassword);
    } catch (final IOException
        | KeyStoreException
        | CertificateException
        | NoSuchAlgorithmException
        | UnrecoverableKeyException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
