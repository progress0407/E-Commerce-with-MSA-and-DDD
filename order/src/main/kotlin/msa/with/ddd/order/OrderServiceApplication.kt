package msa.with.ddd.order

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class OrderServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(OrderServiceApplication::class.java, *args)
}