//package com.example.kafkastudy.test1
//
//import com.example.kafkastudy.test2_slack.SlackReport
//import net.gpedro.integrations.slack.SlackApi
//import net.gpedro.integrations.slack.SlackAttachment
//import net.gpedro.integrations.slack.SlackMessage
//import org.slf4j.LoggerFactory
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.kafka.annotation.KafkaListener
//import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.stereotype.Service
//
//@Service
//class KafkaProducerService(
//    private val kafkaTemplate: KafkaTemplate<String, SlackReport>,
//) {
//    companion object {
//        const val TOPIC_NAME = "testKafka"
//    }
//
//    // topic을 발행한다.
//    fun send(data: String) {
//        runCatching { kafkaTemplate.send(TOPIC_NAME, SlackReport("ad", "ad", "ad", listOf())) }
//            .onSuccess { println("asdklfjasl;dfjk") }
//            .onFailure { it.printStackTrace() }
//    }
//}
//
//@Service
//class KafkaConsumerService {
//
//    @KafkaListener(topics = [KafkaProducerService.TOPIC_NAME], autoStartup = "true")
//    fun consumer(message: String) {
//        println("receive message : $message")
//    }
//}
