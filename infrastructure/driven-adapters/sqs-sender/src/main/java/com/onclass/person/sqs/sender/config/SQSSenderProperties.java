package com.onclass.person.sqs.sender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapter.sqs")
public record SQSSenderProperties(
        String region,
        String personEnrollmentQueueUrl){}
