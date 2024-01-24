package com.example.kafkastudy.test2_slack

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class SlackReportProducerService(
    private val slackReportKafkaTemplate: KafkaTemplate<String, SlackReport>,
) {
    private val testLogger = LoggerFactory.getLogger(this::class.java)

    fun sendReportMessageToSlack(slackReport: SlackReport) {
        runCatching { slackReportKafkaTemplate.send(KafkaTopicConstants.TOPIC_SLACK_REPORT, slackReport) }
            .onSuccess { testLogger.info("success publish topic") }
            .onFailure { it.message }
            .onFailure { it.printStackTrace() }
    }
}

@Service
class SlackReportConsumerService(
    private val failSlackReportKafkaTemplate: KafkaTemplate<String, SlackReport>,
) {
    private val testLogger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = [KafkaTopicConstants.TOPIC_SLACK_REPORT], containerFactory = "slackReportMessageListener")
    fun sendReportMessageToSlack(slackReport: SlackReport) {
        runCatching {
            throw RuntimeException("throw Runtime Exception")
        }
            .onSuccess { testLogger.info("success send to slack") }
            .onFailure { it.printStackTrace() }
            .onFailure {
                failSlackReportKafkaTemplate.send(KafkaTopicConstants.TOPIC_FAIL_SLACK_REPORT, slackReport)
                println("kafka fail message publish")
            }
    }

    @KafkaListener(
        topics = [KafkaTopicConstants.TOPIC_FAIL_SLACK_REPORT],
        containerFactory = "failSlackReportMessageListener"
    )
    fun processFailReport(slackReport: SlackReport) {
        testLogger.error("Catch Fail Slack Report")
        // save SlackReport Object
    }
}
