package msa.with.ddd.item.config

import lombok.extern.slf4j.Slf4j
import msa.with.ddd.item.domain.Item
import msa.with.ddd.item.repository.ItemRepository
import mu.KotlinLogging
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.SQLException
import java.sql.SQLTimeoutException
import javax.sql.DataSource

@Component
@Slf4j
class StartupApplicationListener(
    private val dataSource: DataSource,
    private val itemRepository: ItemRepository
) : ApplicationListener<ContextRefreshedEvent?> {

    private val log = KotlinLogging.logger { }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {

        checkConnection()

        initItems()
    }

    private fun checkConnection() {

        val connection: Connection? = getConnection()
        if (connection === null) {
            log.error { "[DB Connection Fail] ${this.javaClass.simpleName}" }
        }
        log.info("[DB Connection Success] ${this.javaClass.simpleName}")
    }

    private fun getConnection(): Connection? {
        return try {
            dataSource.connection
        } catch (e: SQLException) {
            log.error { "[DB Connection Fail] ${this.javaClass.simpleName}" }
            throw RuntimeException(e)
        } catch (e: SQLTimeoutException) {
            log.error { "[DB Connection Fail] ${this.javaClass.simpleName}" }
            throw RuntimeException(e)
        }
    }

    private fun initItems() {
        try {
            itemRepository.save(Item("포카리 스웨트", "200", 1_600, 40))
            itemRepository.save(Item("이영자 함박 돈까스 도시락", 4_900, 3))
            itemRepository.save(Item("포카리 스웨트", 1_600, 100))
        } catch (e: Exception) {
            // 데이터 초기화 에러
            log.error { "[Data Initialization error]" }
            e.printStackTrace()
            val all = itemRepository.findAll()
            log.info { "item list = $all" }
        }
    }
}
