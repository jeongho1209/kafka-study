//package com.example.kafkastudy.common
//
//import com.example.kafkastudy.test2_slack.SlackReport
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.clients.producer.ProducerConfig
//import org.apache.kafka.common.serialization.StringDeserializer
//import org.apache.kafka.common.serialization.StringSerializer
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.kafka.annotation.EnableKafka
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory
//import org.springframework.kafka.core.DefaultKafkaProducerFactory
//import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.kafka.support.serializer.JsonDeserializer
//import org.springframework.kafka.support.serializer.JsonSerializer
//
//@EnableKafka
//@Configuration
//class KafkaProducerConfig {
//
//    @Bean
//    fun kafkaJsonTemplate(): KafkaTemplate<String, SlackReport> {
//        return KafkaTemplate(kafkaProducer())
//    }
//
//    @Bean
//    fun kafkaProducer(): DefaultKafkaProducerFactory<String, SlackReport> {
//        return DefaultKafkaProducerFactory(producerConfig())
//    }
//
//    private fun producerConfig(): Map<String, Any> = mapOf(
//        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
//        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
//        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
//    )
//}
//
//@EnableKafka
//@Configuration
//class KafkaConsumerConfig {
//
//    @Bean
//    fun kafkaListener() = ConcurrentKafkaListenerContainerFactory<String, SlackReport>().apply {
//        consumerFactory = DefaultKafkaConsumerFactory(consumerConfig())
//    }
//
//    private fun consumerConfig(): Map<String, Any> = mapOf(
//        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
//        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
//        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer(SlackReport::class.java)::class.java,
//        ConsumerConfig.GROUP_ID_CONFIG to "test-kafka"
//    )
//}
