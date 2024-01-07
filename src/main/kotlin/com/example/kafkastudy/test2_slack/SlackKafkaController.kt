package com.example.kafkastudy.test2_slack

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class SlackKafkaController(
    private val slackReportProducerService: SlackReportProducerService,
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/kafka")
    fun sendKafka(@RequestBody slackReport: SlackReport) {
        slackReportProducerService.sendReportMessageToSlack(slackReport)
    }
}
