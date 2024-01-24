package com.example.kafkastudy.common

import com.example.kafkastudy.test2_slack.SlackReport
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.io.Serializable

@EnableKafka
@Configuration
class KafkaConsumerConfig {

    private fun getDefaultConfig(): Map<String, Serializable> {
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "groupId",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to ErrorHandlingDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ErrorHandlingDeserializer::class.java,
            ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS to StringDeserializer::class.java,
            ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS to JsonDeserializer::class.java,
            JsonDeserializer.TRUSTED_PACKAGES to "*"
        )
    }

    @Bean
    fun slackReportMessageConsumer(): ConsumerFactory<String, SlackReport> {
        return DefaultKafkaConsumerFactory(
            getDefaultConfig(),
            StringDeserializer(),
            JsonDeserializer(SlackReport::class.java, false)
        )
    }

    @Bean
    fun slackReportMessageListener() = ConcurrentKafkaListenerContainerFactory<String, SlackReport>()
        .apply { consumerFactory = slackReportMessageConsumer() }

    @Bean
    fun failSlackReportMessageConsumer(): ConsumerFactory<String, SlackReport> {
        return DefaultKafkaConsumerFactory(
            getDefaultConfig(),
            StringDeserializer(),
            JsonDeserializer(SlackReport::class.java, false)
        )
    }

    @Bean
    fun failSlackReportMessageListener() = ConcurrentKafkaListenerContainerFactory<String, SlackReport>()
        .apply { consumerFactory = failSlackReportMessageConsumer() }
}
