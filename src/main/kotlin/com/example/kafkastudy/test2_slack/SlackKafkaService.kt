package com.example.kafkastudy.test2_slack

import net.gpedro.integrations.slack.SlackApi
import net.gpedro.integrations.slack.SlackAttachment
import net.gpedro.integrations.slack.SlackMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class SlackReportProducerService(
    private val kafkaTemplate: KafkaTemplate<String, SlackReport>,
) {
    companion object {
        const val TOPIC_NAME = "kafka-message"
    }

    private val testLogger = LoggerFactory.getLogger(this::class.java)

    fun sendReportMessageToSlack(slackReport: SlackReport) {
        runCatching { kafkaTemplate.send(TOPIC_NAME, slackReport) }
            .onSuccess { testLogger.info("success publish topic") }
            .onFailure { it.message }
            .onFailure { it.printStackTrace() }
    }
}

@Service
class SlackReportConsumerService(
    @Value("\${webhook.url}")
    private val webhookUrl: String,
    private val kafkaTemplate: KafkaTemplate<String, SlackReport>,
) {

    private val testLogger = LoggerFactory.getLogger(this::class.java)

    companion object {
        private const val REPORT_MESSAGE = "버그 제보 발생"
        private const val REPORT_REASON = "이유"
        private const val REPORT_CATEGORY = "카테고리"
        private const val REPORT_USER_NAME = "제보자"
        private const val MESSAGE_COLOR = "#e62e2e"
        private const val FALLBACK = "새로운 버그제보가 도착했습니다."
        private const val FAIL_REPORT_TOPIC = "failReport"
    }

    @KafkaListener(
        topics = [SlackReportProducerService.TOPIC_NAME],
        groupId = "testGroup",
        containerFactory = "kafkaListener"
    )
    fun sendReportMessageToSlack(slackReport: SlackReport) {
        val slackAttachment = SlackAttachment().apply { createSlackAttachment(getReportReason(slackReport)) }
        val slackMessage = SlackMessage("").apply {
            addAttachments(slackAttachment)
            createSlackImage(slackReport.imageUrls)
        }
        runCatching { SlackApi(webhookUrl).call(slackMessage) }
            .onSuccess { testLogger.info("success send to slack") }
            .onFailure { it.printStackTrace() }
            .onFailure { kafkaTemplate.send(FAIL_REPORT_TOPIC, slackReport) }
    }

    private fun getReportReason(slackReport: SlackReport) =
        "$REPORT_USER_NAME : ${slackReport.userName}\n$REPORT_REASON : ${slackReport.reason}\n$REPORT_CATEGORY : ${slackReport.category}"

    private fun SlackMessage.createSlackImage(imageUrls: List<String>) {
        imageUrls.map { imageUrl -> this.addAttachments(SlackAttachment().createSlackImageAttachment(imageUrl)) }
    }

    private fun SlackAttachment.createSlackAttachment(errorReason: String) = this.apply {
        setTitle(REPORT_MESSAGE)
        setText(errorReason)
        setFallback(FALLBACK)
        setColor(MESSAGE_COLOR)
    }

    private fun SlackAttachment.createSlackImageAttachment(imageUrl: String) = this.apply {
        setImageUrl(imageUrl)
        setFallback(FALLBACK)
        setColor(MESSAGE_COLOR)
    }

    @KafkaListener(
        topics = [FAIL_REPORT_TOPIC],
        groupId = "testGroup",
        containerFactory = "kafkaListener"
    )
    fun processFailReport(slackReport: SlackReport) {
        testLogger.debug(slackReport.category + "Slack 전송 실패")
    }
}
