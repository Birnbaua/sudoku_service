package at.birnbaua.sudoku_service.jpa.jpaservice

import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Suppress("unused")
abstract class JpaService<T: Any,ID: Any> (private val repository: JpaRepository<T, ID>) {

    open fun save(entity: T): T {
        return repository.save(entity)
    }

    open fun saveAll(entities: MutableIterable<T>): MutableList<T> {
        return repository.saveAll(entities)
    }

    fun findById(id: ID): Optional<T> {
        return repository.findById(id)
    }

    fun existsById(id: ID): Boolean {
        return repository.existsById(id)
    }

    fun findAll(): MutableList<T> {
        return repository.findAll()
    }

    fun findAll(sort: Sort): MutableList<T> {
        return repository.findAll(sort)
    }

    fun findAll(example: Example<T>): MutableList<T> {
        return repository.findAll(example)
    }

    fun findAll(example: Example<T>, sort: Sort): MutableList<T> {
        return repository.findAll(example,sort)
    }

    fun findAll(pageable: Pageable): Page<T> {
        return repository.findAll(pageable)
    }

    fun findAll(example: Example<T>, pageable: Pageable): Page<T> {
        return repository.findAll(example,pageable)
    }

    fun findAllById(ids: MutableIterable<ID>): MutableList<T> {
        return repository.findAllById(ids)
    }

    fun count(): Long {
        return repository.count()
    }

    fun count(example: Example<T>): Long {
        return repository.count(example)
    }

    fun deleteById(id: ID) {
        repository.deleteById(id)
    }

    fun delete(entity: T) {
        repository.delete(entity)
    }

    fun deleteAllById(ids: MutableIterable<ID>) {
        repository.deleteAllById(ids)
    }

    fun deleteAll(entities: MutableIterable<T>) {
        repository.deleteAll(entities)
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun findOne(example: Example<T>): Optional<T> {
        return repository.findOne(example)
    }

    fun exists(example: Example<T>): Boolean {
        return repository.exists(example)
    }

    fun flush() {
        repository.flush()
    }

    fun saveAndFlush(entity: T): T {
        return repository.saveAndFlush(entity)
    }

    open fun saveAllAndFlush(entities: MutableIterable<T>): MutableList<T> {
        return repository.saveAllAndFlush(entities)
    }

    fun deleteAllInBatch(entities: MutableIterable<T>) {
        repository.deleteAllInBatch(entities)
    }

    fun deleteAllInBatch() {
        repository.deleteAllInBatch()
    }

    fun deleteAllByIdInBatch(ids: MutableIterable<ID>) {
        repository.flush()
        repository.deleteAllByIdInBatch(ids)
    }

    fun getOne(id: ID): T {
        return repository.getById(id)
    }

    fun getById(id: ID): T {
        return repository.getById(id)
    }
}