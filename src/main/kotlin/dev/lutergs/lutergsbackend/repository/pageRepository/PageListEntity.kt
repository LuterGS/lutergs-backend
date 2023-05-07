package dev.lutergs.lutergsbackend.repository.pageRepository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Document(collection = "pagelist")
class PageListEntity {
    @Id
    var id: String? = null
    var name: String? = null
    var endpoint: String? = null

    companion object {
        fun fromPageEntity(pageEntity: PageEntity): PageListEntity {
            return PageListEntity().apply {
                this.id = pageEntity.id
                this.name = pageEntity.name
                this.endpoint = pageEntity.endpoint
            }
        }
    }
}

@Repository
interface PageListReactiveMongoRepository: ReactiveMongoRepository<PageListEntity, String>