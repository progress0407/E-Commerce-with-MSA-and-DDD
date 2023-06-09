package msa.with.ddd.order.rest

import msa.with.ddd.order.dto.web.ItemResponses
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * TODO 테스트 하기 용이하게 변경하기
 * https://www.baeldung.com/spring-boot-running-port
 */
@FeignClient(value = "itemCall", url = "localhost:8080")
interface ItemFeignClient {

    @GetMapping("/items")
    fun requestItems(@RequestParam("ids") ids: List<Long>): ItemResponses
}