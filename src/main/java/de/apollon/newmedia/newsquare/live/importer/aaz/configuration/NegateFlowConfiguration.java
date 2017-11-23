package de.apollon.newmedia.newsquare.live.importer.aaz.configuration;

import de.apollon.newmedia.newsquare.live.importer.aaz.service.NegateFlowService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class NegateFlowConfiguration {

    public static final String INPUT_CHANNEL = "inputChannel";
    public static final String OUTPUT_CHANNEL = "outputChannel";
    public static final String INPUT_SUBFLOW_CHANNEL = "inputSubflowChannel";
    public static final String OUTPUT_SUBFLOW_CHANNEL = "outputSubflowChannel";

    @Bean
    public IntegrationFlow negateFlow(
            NegateFlowService negateFlowService,
            NegateSubflowGateway subflowGateway,
            RetryTemplate retryTemplate) {
        return IntegrationFlows
                .from(INPUT_CHANNEL)

                .<Integer, Boolean>route(
                        negateFlowService::isNegative,
                        router -> router
                                .resolutionRequired(false)
                                .defaultOutputToParentFlow()

                                .subFlowMapping(true, subflow -> subflow
                                        .transform(subflowGateway::negateSubflow)))

                .transform(
                        negateFlowService::str,
                        endpoint -> endpoint.advice(new RequestHandlerRetryAdvice() {{
                            setRetryTemplate(retryTemplate);
                        }}))

                .channel(OUTPUT_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow negateSubflow(
            NegateFlowService negateFlowService) {
        return IntegrationFlows
                .from(INPUT_SUBFLOW_CHANNEL)

                .transform(negateFlowService::negate)

                .channel(OUTPUT_SUBFLOW_CHANNEL)
                .get();
    }

    @Bean
    public RetryTemplate retryTemplate(
            ApplicationProperties properties) {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(properties.getRetryCount()));
        return retryTemplate;
    }

    @MessagingGateway
    public interface NegateGateway {

        @Gateway(requestChannel = INPUT_CHANNEL, replyChannel = OUTPUT_CHANNEL)
        String negate(int i);
    }

    @MessagingGateway
    public interface NegateSubflowGateway {

        @Gateway(requestChannel = INPUT_SUBFLOW_CHANNEL, replyChannel = OUTPUT_SUBFLOW_CHANNEL)
        int negateSubflow(int i);
    }
}
