//package com.example.kafkastudy.test1
//
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//class KafkaController(
//    private val kafkaProducerService: KafkaProducerService,
//) {
//
//    @GetMapping("/send/{message}")
//    fun sendMessage(@PathVariable("message") message: String) {
//        kafkaProducerService.send(message)
//    }
//}
