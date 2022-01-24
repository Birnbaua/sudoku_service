package at.birnbaua.sudoku_service.jpa.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.MappedSuperclass

/**
 * Superclass for entities where time logging is wanted.
 * @since 1.0
 * @author Andreas Bachl
 */
@MappedSuperclass
abstract class AbstractEntity(

    @CreatedDate
    @Column(name = "`created_at`")
    open var createdAt: Timestamp? = null,

    @LastModifiedDate
    @Column(name = "`updated_at`")
    open var updatedAt: Timestamp? = null

)