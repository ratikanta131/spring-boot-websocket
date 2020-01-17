package in.co.sdrc.newsapp.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface RabbitMQConfig {
    
    public static final String SDRC_APP_EMAIL_CHANNEL_OUT = "sdrcappemailchannel-out";

    public static final String SDRC_APP_EMAIL_CHANNEL_IN = "sdrcappemailchannel-in";

    public static final String SDRC_APP_SUBMIT_EMAIL_CHANNEL_OUT = "sdrcappsubmitemailchannel-out";

    public static final String SDRC_APP_SUBMIT_EMAIL_CHANNEL_IN = "sdrcappsubmitemailchannel-in";


    @Output(RabbitMQConfig.SDRC_APP_EMAIL_CHANNEL_OUT)
    MessageChannel assignmentCreationChannel();

    @Input(RabbitMQConfig.SDRC_APP_EMAIL_CHANNEL_IN)
    SubscribableChannel receiveDataForSendingEmail();

    @Output(RabbitMQConfig.SDRC_APP_SUBMIT_EMAIL_CHANNEL_OUT)
    MessageChannel submitOutChannel();

    @Input(RabbitMQConfig.SDRC_APP_SUBMIT_EMAIL_CHANNEL_IN)
    SubscribableChannel submitInChannel();
}